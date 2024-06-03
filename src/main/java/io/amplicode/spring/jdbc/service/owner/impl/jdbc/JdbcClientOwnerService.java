package io.amplicode.spring.jdbc.service.owner.impl.jdbc;

import io.amplicode.spring.jdbc.model.owner.OwnerBase;
import io.amplicode.spring.jdbc.model.owner.OwnerMinimal;
import io.amplicode.spring.jdbc.model.owner.OwnerWithPets;
import io.amplicode.spring.jdbc.model.pet.PetBase;
import io.amplicode.spring.jdbc.service.owner.OwnerFilter;
import io.amplicode.spring.jdbc.service.owner.OwnerService;
import io.amplicode.spring.jdbc.service.pet.PetService;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
public class JdbcClientOwnerService implements OwnerService {

    private final JdbcClient jdbcClient;
    private final PetService petService;
    private final SimpleJdbcInsert insertOwner;

    public JdbcClientOwnerService(JdbcClient jdbcClient,
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
        var sql = "select * from " + TABLE_NAME;

        List<String> filterConditions = convertToConditions(filter);
        if (!filterConditions.isEmpty()) {
            String conditionsExpr = filterConditions.stream()
                    .map(s -> " (" + s + ")")
                    .collect(Collectors.joining(" and"));
            sql += " where" + conditionsExpr;
        }

        Sort sort = pageable.getSort();
        List<String> sortExpressions = convertToExpressions(sort);
        if (!sortExpressions.isEmpty()) {
            sql += " order by " + String.join(", ", sortExpressions);
        }

        if (!pageable.isUnpaged()) {
            sql += " limit " + pageable.getPageSize() + " offset " + pageable.getOffset();
        }

        return jdbcClient.sql(sql)
                .query(OwnerService::mapOwnerBase)
                .list();
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

    private List<String> convertToExpressions(Sort sort) {
        return sort.stream().map(order -> {
            String property = JdbcUtils.convertPropertyNameToUnderscoreName(order.getProperty());
            Sort.Direction direction = order.getDirection();
            return property + " " + direction;
        }).toList();
    }

    private List<String> convertToConditions(OwnerFilter filter) {
        List<String> result = new ArrayList<>();
        String name = filter.name();
        if (StringUtils.hasText(name)) {
            result.add("(" + FIRST_NAME + " ilike '%" + name + "%') or (" + LAST_NAME + " ilike '%" + name + "%')");
        }
        String city = filter.city();
        if (StringUtils.hasText(city)) {
            result.add(CITY + " ilike '%" + city + "%'");
        }
        String telephone = filter.telephone();
        if (StringUtils.hasText(telephone)) {
            result.add(TELEPHONE + " = '" + telephone + "'");
        }
        return result;
    }

    @Override
    public Optional<OwnerBase> findById(Integer id) {
        return jdbcClient.sql("select * from owners where id = :id")
                .param("id", id)
                .query(OwnerService::mapOwnerBase)
                .optional();
    }

    @Override
    public List<OwnerMinimal> findAllById(Collection<Integer> ids) {
        return jdbcClient.sql("select id, first_name, last_name from owners where id in (:ids)")
                .param("ids", ids)
                .query(OwnerService::mapOwnerMinimal)
                .list();
    }

    @Transactional
    @NonNull
    @Override
    public OwnerBase save(OwnerBase owner) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(owner);
//        MapSqlParameterSource parameterSource = new MapSqlParameterSource(Map.of(
//                FIRST_NAME, owner.firstName(),
//                LAST_NAME, owner.lastName(),
//                ADDRESS, owner.address(),
//                CITY, owner.city(),
//                TELEPHONE, owner.telephone()
//        ));

        boolean isNew = owner.getId() == null;
        if (isNew) {
            Number newKey = this.insertOwner.executeAndReturnKey(parameterSource);
            owner.setId(newKey.intValue());
        } else {
            this.jdbcClient.sql("""
                            UPDATE owners
                            SET first_name=:firstName, last_name=:lastName, address=:address, city=:city, telephone=:telephone
                            WHERE id=:id
                            """)
                    .paramSource(parameterSource)
                    .update();
        }
        return owner;
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        jdbcClient.sql("delete from owners where id=:id")
                .param("id", id)
                .update();
    }

    @Override
    public void deleteAllById(Collection<Integer> ids) {
        jdbcClient.sql("delete from owners where id in (:ids)")
                .param("ids", ids)
                .update();
    }
}
