package io.amplicode.spring.jdbc.service.pet.impl;

import io.amplicode.spring.jdbc.model.pet.PetBase;
import io.amplicode.spring.jdbc.service.pet.PetService;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class JdbcClientPetService implements PetService {

    private final JdbcClient jdbcClient;

    public JdbcClientPetService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<PetBase> findAllByOwnerIds(Collection<Integer> ownerIds) {
        var sql = "select * from pets where owner_id in (:owner_ids) order by name";

        return jdbcClient.sql(sql)
                .param("owner_ids", ownerIds)
                .query(PetService::mapPetBase)
                .list();
    }
}
