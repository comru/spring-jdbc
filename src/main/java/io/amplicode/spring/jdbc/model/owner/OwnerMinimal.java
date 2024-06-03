package io.amplicode.spring.jdbc.model.owner;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class OwnerMinimal {
    Integer id;
    String firstName;
    String lastName;
}
