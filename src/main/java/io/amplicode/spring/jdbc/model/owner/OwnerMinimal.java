package io.amplicode.spring.jdbc.model.owner;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@FieldNameConstants
public class OwnerMinimal implements Persistable<Integer>  {

    @Id
    Integer id;
    String firstName;
    String lastName;

    @Override
    public boolean isNew() {
        return id == null;
    }

    public static class Fields {}
}
