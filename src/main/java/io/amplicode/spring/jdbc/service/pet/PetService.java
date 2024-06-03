package io.amplicode.spring.jdbc.service.pet;

import io.amplicode.spring.jdbc.model.pet.PetBase;
import io.amplicode.spring.jdbc.model.pet.PetMinimal;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface PetService {

    String TABLE_NAME = "pets";
    String ID = "id";
    String NAME = "name";
    String BIRTH_DATE = "birth_date";
    String TYPE_ID = "type_id";
    String OWNER_Id = "owner_id";

    List<PetBase> findAllByOwnerIds(Collection<Integer> ownerIds);

    static PetMinimal mapPetMinimal(PetMinimal petMinimal, ResultSet rs) throws SQLException {
        petMinimal.setId(rs.getInt(ID));
        petMinimal.setName(rs.getString(NAME));
        return petMinimal;
    }

    static PetBase mapPetBase(ResultSet rs, int rowNum) throws SQLException {
        Date birthDate = rs.getDate(BIRTH_DATE);
        PetBase petBase = (PetBase) mapPetMinimal(new PetBase(), rs);
        petBase.setBirthDate(birthDate == null ? null : birthDate.toLocalDate());
        petBase.setTypeId(rs.getInt(TYPE_ID));
        petBase.setOwnerId(rs.getInt(OWNER_Id));
        return petBase;
    }
}
