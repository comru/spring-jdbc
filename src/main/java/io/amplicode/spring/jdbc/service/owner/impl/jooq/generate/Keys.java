/*
 * This file is generated by jOOQ.
 */
package io.amplicode.spring.jdbc.service.owner.impl.jooq.generate;


import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Owners;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Pets;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Types;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.records.OwnersRecord;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.records.PetsRecord;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.records.TypesRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<OwnersRecord> OWNERS_PKEY = Internal.createUniqueKey(Owners.OWNERS, DSL.name("owners_pkey"), new TableField[] { Owners.OWNERS.ID }, true);
    public static final UniqueKey<PetsRecord> PETS_PKEY = Internal.createUniqueKey(Pets.PETS, DSL.name("pets_pkey"), new TableField[] { Pets.PETS.ID }, true);
    public static final UniqueKey<TypesRecord> TYPES_PKEY = Internal.createUniqueKey(Types.TYPES, DSL.name("types_pkey"), new TableField[] { Types.TYPES.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<PetsRecord, OwnersRecord> PETS__PETS_OWNER_ID_FKEY = Internal.createForeignKey(Pets.PETS, DSL.name("pets_owner_id_fkey"), new TableField[] { Pets.PETS.OWNER_ID }, Keys.OWNERS_PKEY, new TableField[] { Owners.OWNERS.ID }, true);
    public static final ForeignKey<PetsRecord, TypesRecord> PETS__PETS_TYPE_ID_FKEY = Internal.createForeignKey(Pets.PETS, DSL.name("pets_type_id_fkey"), new TableField[] { Pets.PETS.TYPE_ID }, Keys.TYPES_PKEY, new TableField[] { Types.TYPES.ID }, true);
}
