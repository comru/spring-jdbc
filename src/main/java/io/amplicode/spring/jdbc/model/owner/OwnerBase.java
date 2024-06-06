package io.amplicode.spring.jdbc.model.owner;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
@Table("owners")
@FieldNameConstants
public class OwnerBase extends OwnerMinimal {

    String address;
    String city;
    String telephone;

    public static class Fields extends OwnerMinimal.Fields {}
}