package io.amplicode.spring.jdbc.service.owner;

import io.amplicode.spring.jdbc.model.owner.OwnerBase;
import io.amplicode.spring.jdbc.model.owner.OwnerMinimal;
import io.amplicode.spring.jdbc.model.owner.OwnerWithPets;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OwnerService {

    String TABLE_NAME = "owners";
    String ID = "id";
    String FIRST_NAME = "first_Name";
    String LAST_NAME = "last_Name";
    String ADDRESS = "address";
    String CITY = "city";
    String TELEPHONE = "telephone";

    List<OwnerBase> findAll(OwnerFilter filter, Pageable pageable);

    List<OwnerWithPets> findAllWithPets(OwnerFilter filter, Pageable pageable);

    Optional<OwnerBase> findById(Integer id);

    List<OwnerMinimal> findAllById(Collection<Integer> ids);

    @NonNull
    OwnerBase save(OwnerBase owner);

    void deleteById(Integer id);

    void deleteAllById(Collection<Integer> ids);

    static OwnerMinimal mapOwnerMinimal(ResultSet rs, int rowNum) throws SQLException {
        return mapOwnerMinimal(new OwnerMinimal(), rs);
    }

    static OwnerMinimal mapOwnerMinimal(OwnerMinimal owner, ResultSet rs) throws SQLException {
        owner.setId(rs.getInt(ID));
        owner.setFirstName(rs.getString(FIRST_NAME));
        owner.setLastName(rs.getString(LAST_NAME));
        return owner;
    }

    @SuppressWarnings("unused")
    static OwnerBase mapOwnerBase(ResultSet rs, int rowNum) throws SQLException {
        OwnerBase owner = (OwnerBase) mapOwnerMinimal(new OwnerBase(), rs);
        owner.setAddress(rs.getString(ADDRESS));
        owner.setCity(rs.getString(CITY));
        owner.setTelephone(rs.getString(TELEPHONE));
        return owner;
    }
}
