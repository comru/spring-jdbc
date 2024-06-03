package io.amplicode.spring.jdbc.model.owner;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class OwnerBase extends OwnerMinimal {

    String address;
    String city;
    String telephone;
}