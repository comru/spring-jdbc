package io.amplicode.spring.jdbc.service.owner.impl.datajdbc;

import io.amplicode.spring.jdbc.model.owner.OwnerBase;
import io.amplicode.spring.jdbc.model.owner.OwnerMinimal;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface JdbcOwnerRepository extends CrudRepository<OwnerBase, Integer> {

    List<OwnerMinimal> findAllByIdIn(Collection<Integer> ids);

//    List<OwnerWithPets> findAllWithPetsBy(Pageable pageable);
}
