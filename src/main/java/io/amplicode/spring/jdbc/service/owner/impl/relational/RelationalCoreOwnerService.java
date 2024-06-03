package io.amplicode.spring.jdbc.service.owner.impl.relational;

import io.amplicode.spring.jdbc.model.owner.OwnerBase;
import io.amplicode.spring.jdbc.model.owner.OwnerMinimal;
import io.amplicode.spring.jdbc.model.owner.OwnerWithPets;
import io.amplicode.spring.jdbc.model.pet.PetBase;
import io.amplicode.spring.jdbc.service.owner.OwnerFilter;
import io.amplicode.spring.jdbc.service.owner.OwnerService;
import io.amplicode.spring.jdbc.service.pet.PetService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.sql.*;
import org.springframework.data.relational.core.sql.render.SqlRenderer;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.relational.core.sql.Conditions.*;

@Repository
public class RelationalCoreOwnerService implements OwnerService {

    private final JdbcClient jdbcClient;
    private final PetService petService;
    private final SimpleJdbcInsert insertOwner;

    public RelationalCoreOwnerService(JdbcClient jdbcClient,
                                      PetService petService,
                                      DataSource dataSource) {
        this.jdbcClient = jdbcClient;
        this.petService = petService;
        insertOwner = new SimpleJdbcInsert(dataSource)
                .withTableName("owners")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<OwnerBase> findAll(OwnerFilter filter, Pageable pageable) {
        var table = ownerTable();

        final var from = Select.builder()
                .select(table.column("*"))
                .from(table);

        List<Condition> conditions = convertToConditions(table, filter);
        if (!conditions.isEmpty()) {
            from.where(conditions.stream().reduce(Condition::and).get());
        }

        from.orderBy(convertToOrderByFields(table, pageable.getSort()));

        if (!pageable.isUnpaged()) {
            from.limitOffset(pageable.getPageSize(), pageable.getOffset());
        }

        String sql = SqlRenderer.toString(from.build());
        return jdbcClient.sql(sql)
                .query(OwnerService::mapOwnerBase)
                .list();
    }

    private Collection<OrderByField> convertToOrderByFields(Table table, Sort sort) {
        return sort.stream().map(order -> {
            String property = JdbcUtils.convertPropertyNameToUnderscoreName(order.getProperty());
            Sort.Direction direction = order.getDirection();
            return OrderByField.from(table.column(property), direction);
        }).toList();
    }

    private List<Condition> convertToConditions(Table table, OwnerFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        String name = filter.name();
        if (StringUtils.hasText(name)) {
            conditions.add(nest(ignoreCaseContains(table, FIRST_NAME, name)
                    .or(ignoreCaseContains(table, LAST_NAME, name))));
        }
        String city = filter.city();
        if (StringUtils.hasText(city)) {
            conditions.add(ignoreCaseContains(table, CITY, city));
        }
        String telephone = filter.telephone();
        if (StringUtils.hasText(telephone)) {
            conditions.add(isEqual(table.column(TELEPHONE), SQL.literalOf(telephone)));
        }
        return conditions;
    }

    private static Like ignoreCaseContains(Table table, String columnName, String literal) {
        Expression leftExpression = lowerFunction(table.column(columnName));
        Expression rightExpression = lowerFunction(SQL.literalOf("%" + literal + "%"));
        return like(leftExpression, rightExpression);
    }

    private static Expression lowerFunction(Expression expression) {
        return SimpleFunction.create("lower", List.of(expression));
    }

    @Override
    public List<OwnerWithPets> findAllWithPets(OwnerFilter filter, Pageable pageable) {
        List<OwnerWithPets> owners = findAll(filter, pageable).stream()
                .map(OwnerWithPets::create)
                .toList();

        if (owners.isEmpty()) {
            return List.of();
        }

        Set<Integer> ownerIds = owners.stream()
                .map(OwnerMinimal::getId)
                .collect(Collectors.toSet());

        List<PetBase> pets = petService.findAllByOwnerIds(ownerIds);

        for (OwnerWithPets ownerWithPets : owners) {
            List<PetBase> ownerPerts = pets.stream()
                    .filter(petBase -> Objects.equals(petBase.getOwnerId(), ownerWithPets.getId()))
                    .toList();
            ownerWithPets.setPets(ownerPerts);
        }

        return owners;
    }

    @Override
    public Optional<OwnerBase> findById(Integer id) {
        var table = ownerTable();

        var select = Select.builder()
                .select(table.column("*"))
                .from(table)
                .where(isEqual(table.column(ID), SQL.literalOf(id)))
                .build();

        return jdbcClient.sql(SqlRenderer.toString(select))
                .query(OwnerService::mapOwnerBase)
                .optional();
    }

    @Override
    public List<OwnerMinimal> findAllById(Collection<Integer> ids) {
        var table = ownerTable();

        Column idColumn = table.column(ID);
        List<NumericLiteral> idExpressions = ids.stream()
                .map(SQL::literalOf)
                .toList();

        var select = Select.builder()
                .select(idColumn, table.column(FIRST_NAME), table.column(LAST_NAME))
                .from(table)
                .where(in(idColumn, idExpressions))
                .build();

        return jdbcClient.sql(SqlRenderer.toString(select))
                .query(OwnerService::mapOwnerMinimal)
                .list();
    }

    @NonNull
    @Override
    public OwnerBase save(OwnerBase owner) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(owner);

        boolean isNew = owner.getId() == null;
        if (isNew) {
            Number newKey = this.insertOwner.executeAndReturnKey(parameterSource);
            owner.setId(newKey.intValue());
        } else {
            var table = ownerTable();

            Update update = Update.builder().table(table).set(List.of(
                    assignment(table.column(FIRST_NAME), owner.getFirstName()),
                    assignment(table.column(LAST_NAME), owner.getLastName()),
                    assignment(table.column(ADDRESS), owner.getAddress()),
                    assignment(table.column(CITY), owner.getCity()),
                    assignment(table.column(TELEPHONE), owner.getTelephone())
            )).where(isEqual(table.column(ID), SQL.literalOf(owner.getId()))).build();

            jdbcClient.sql(SqlRenderer.toString(update))
                    .update();
        }
        return owner;
    }

    private Assignment assignment(Column column, String value) {
        return AssignValue.create(column, SQL.literalOf(value));
    }

    @Override
    public void deleteById(Integer id) {
        Table table = ownerTable();

        Delete delete = Delete.builder()
                .from(table)
                .where(isEqual(table.column(ID), SQL.literalOf(id)))
                .build();

        jdbcClient.sql(SqlRenderer.toString(delete))
                .update();
    }

    @Override
    public void deleteAllById(Collection<Integer> ids) {
        Table table = ownerTable();

        List<NumericLiteral> idExpressions = ids.stream()
                .map(SQL::literalOf)
                .toList();

        Delete delete = Delete.builder()
                .from(table)
                .where(in(table.column(ID), idExpressions))
                .build();

        jdbcClient.sql(SqlRenderer.toString(delete)).update();
    }

    private static Table ownerTable() {
        return Table.create(TABLE_NAME);
    }
}
