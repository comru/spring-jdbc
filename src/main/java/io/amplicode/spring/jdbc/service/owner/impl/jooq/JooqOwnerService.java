package io.amplicode.spring.jdbc.service.owner.impl.jooq;

import io.amplicode.spring.jdbc.model.owner.OwnerBase;
import io.amplicode.spring.jdbc.model.owner.OwnerMinimal;
import io.amplicode.spring.jdbc.model.owner.OwnerWithPets;
import io.amplicode.spring.jdbc.model.pet.PetBase;
import io.amplicode.spring.jdbc.service.owner.OwnerFilter;
import io.amplicode.spring.jdbc.service.owner.OwnerService;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Owners;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.records.OwnersRecord;
import io.amplicode.spring.jdbc.service.pet.PetService;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Condition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Owners.OWNERS;

@Service
@RequiredArgsConstructor
public class JooqOwnerService implements OwnerService {

    private static final Map<String, TableField<OwnersRecord, ?>> domainToJooqField = Map.of(
            "id", OWNERS.ID,
            "firstName", OWNERS.FIRST_NAME,
            "lastName", OWNERS.LAST_NAME,
            "address", OWNERS.ADDRESS,
            "city", OWNERS.CITY,
            "telephone", OWNERS.TELEPHONE
    );

    private final DSLContext dsl;
    private final PetService petService;

    @Override
    public List<OwnerBase> findAll(OwnerFilter filter, Pageable pageable) {
        var select = dsl.selectFrom(OWNERS)
                .where(convertToConditions(filter))
                .orderBy(convertToOrders(pageable.getSort()));

        if (!pageable.isUnpaged()) {
            select.limit(pageable.getPageSize())
                    .offset(pageable.getOffset());
        }

        return select.fetch().map(this::mapToOwnerBase);
    }

    private List<SortField<?>> convertToOrders(Sort sort) {
        return sort.stream()
                .map(order -> {
                    TableField<OwnersRecord, ?> tableField = domainToJooqField.get(order.getProperty());
                    if (tableField == null) {
                        return null;
                    }
                    return order.getDirection() == Sort.Direction.DESC ? tableField.desc() : tableField.asc();
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private List<Condition> convertToConditions(OwnerFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        Owners owners = OWNERS;
        String name = filter.name();
        if (StringUtils.hasText(name)) {
            String nameExpr = "%" + name + "%";
            conditions.add(owners.FIRST_NAME.likeIgnoreCase(nameExpr)
                    .or(owners.LAST_NAME.likeIgnoreCase(nameExpr)));
        }
        String city = filter.city();
        if (StringUtils.hasText(city)) {
            conditions.add(owners.CITY.likeIgnoreCase("%" + city + "%"));
        }
        String telephone = filter.telephone();
        if (StringUtils.hasText(telephone)) {
            conditions.add(owners.TELEPHONE.eq(telephone));
        }
        return conditions;
    }

    private OwnerBase mapToOwnerBase(OwnersRecord record) {
        OwnerBase ownerBase = new OwnerBase();
        ownerBase.setId(record.getId());
        ownerBase.setFirstName(record.getFirstName());
        ownerBase.setLastName(record.getLastName());
        ownerBase.setAddress(record.getAddress());
        ownerBase.setCity(record.getCity());
        ownerBase.setTelephone(record.getTelephone());
        return ownerBase;
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
        return dsl.selectFrom(OWNERS)
                .where(OWNERS.ID.eq(id))
                .fetchOptional()
                .map(this::mapToOwnerBase);
    }

    @Override
    public List<OwnerMinimal> findAllById(Collection<Integer> ids) {
        return dsl.selectFrom(OWNERS)
                .where(OWNERS.ID.in(ids))
                .fetch()
                .map(this::mapToOwnerBase);
    }

    @Override
    public OwnerBase save(OwnerBase owner) {
        Integer ownerId = owner.getId();
        OwnersRecord record = new OwnersRecord(
                ownerId,
                owner.getFirstName(),
                owner.getLastName(),
                owner.getCity(),
                owner.getAddress(),
                owner.getTelephone()
        );

        if (ownerId == null) {
            Record1<Integer> newId = dsl.insertInto(OWNERS)
                    .set(record)
                    .returningResult(OWNERS.ID)
                    .fetchOne();
            owner.setId(newId.component1());
        } else {
            dsl.update(OWNERS)
                    .set(record)
                    .where(OWNERS.ID.eq(ownerId))
                    .execute();
        }
        return owner;
    }

    @Override
    public void deleteById(Integer id) {
        dsl.delete(OWNERS)
                .where(OWNERS.ID.eq(id))
                .execute();
    }

    @Override
    public void deleteAllById(Collection<Integer> ids) {
        dsl.delete(OWNERS)
                .where(OWNERS.ID.in(ids))
                .execute();
    }
}
