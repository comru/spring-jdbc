package io.amplicode.spring.jdbc.model.pet;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PetBase extends PetMinimal {
    LocalDate birthDate;
    Integer typeId;
    Integer ownerId;
}
