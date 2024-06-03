package io.amplicode.spring.jdbc.model.owner;

import io.amplicode.spring.jdbc.model.pet.PetBase;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class OwnerWithPets extends OwnerBase {

    @MappedCollection(keyColumn = "owner_id", idColumn = "id")
    private List<PetBase> pets = new ArrayList<>();

    public static OwnerWithPets create(OwnerBase ownerBase) {
        OwnerWithPets result = new OwnerWithPets();
        result.setId(ownerBase.getId());
        result.setFirstName(ownerBase.getFirstName());
        result.setLastName(ownerBase.getLastName());
        result.setAddress(ownerBase.getAddress());
        result.setCity(ownerBase.getCity());
        result.setTelephone(ownerBase.getTelephone());
        return result;
    }
}
