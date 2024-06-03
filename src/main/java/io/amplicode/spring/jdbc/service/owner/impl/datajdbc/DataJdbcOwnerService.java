package io.amplicode.spring.jdbc.service.owner.impl.datajdbc;

import io.amplicode.spring.jdbc.model.owner.OwnerBase;
import io.amplicode.spring.jdbc.model.owner.OwnerMinimal;
import io.amplicode.spring.jdbc.model.owner.OwnerWithPets;
import io.amplicode.spring.jdbc.model.pet.PetBase;
import io.amplicode.spring.jdbc.service.owner.OwnerFilter;
import io.amplicode.spring.jdbc.service.owner.OwnerService;
import io.amplicode.spring.jdbc.service.pet.PetService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.relational.core.query.Criteria.where;

@Service
public class DataJdbcOwnerService implements OwnerService {

    private final JdbcAggregateOperations entityOperations;

    private final PetService petService;

    private final JdbcOwnerRepository ownerRepository;

    public DataJdbcOwnerService(JdbcAggregateOperations entityOperations,
                                PetService petService,
                                JdbcOwnerRepository ownerRepository) {
        this.entityOperations = entityOperations;
        this.petService = petService;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<OwnerBase> findAll(OwnerFilter filter, Pageable pageable) {
        var query = createQuery(filter);
        return entityOperations.findAll(query, OwnerBase.class, pageable).getContent();
    }

    private Query createQuery(OwnerFilter filter) {
        Criteria criteria = Criteria.empty();
        String name = filter.name();
        if (StringUtils.hasText(name)) {
            //TODO property name!!! 4 hours!!!!
            String nameExpr = "%" + name + "%";
            criteria = criteria.and(
                    where("firstName").like(nameExpr).ignoreCase(true)
                            .or("lastName").like(nameExpr).ignoreCase(true)
            );
        }
        String city = filter.city();
        if (StringUtils.hasText(city)) {
            criteria = criteria.and(CITY).like("%" + city + "%").ignoreCase(true);
        }
        String telephone = filter.telephone();
        if (StringUtils.hasText(telephone)) {
            criteria = criteria.and(TELEPHONE).is(telephone);
        }
        return Query.query(criteria);
    }

    //TODO load by aggregation root?
    @Override
    public List<OwnerWithPets> findAllWithPets(OwnerFilter filter, Pageable pageable) {
//        List<OwnerWithPets> all = ownerRepository.findAllWithPetsBy(pageable);
//        return all;

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
        return ownerRepository.findById(id);
    }

    @Override
    public List<OwnerMinimal> findAllById(Collection<Integer> ids) {
        return ownerRepository.findAllByIdIn(ids);
    }

    @Override
    public OwnerBase save(OwnerBase owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void deleteById(Integer id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public void deleteAllById(Collection<Integer> ids) {
        ownerRepository.deleteAllById(ids);
    }
}
