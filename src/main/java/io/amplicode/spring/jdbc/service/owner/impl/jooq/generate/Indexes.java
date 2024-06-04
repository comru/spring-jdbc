/*
 * This file is generated by jOOQ.
 */
package io.amplicode.spring.jdbc.service.owner.impl.jooq.generate;


import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Owners;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Pets;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Types;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index OWNERS_LAST_NAME_IDX = Internal.createIndex(DSL.name("owners_last_name_idx"), Owners.OWNERS, new OrderField[] { Owners.OWNERS.LAST_NAME }, false);
    public static final Index PETS_NAME_IDX = Internal.createIndex(DSL.name("pets_name_idx"), Pets.PETS, new OrderField[] { Pets.PETS.NAME }, false);
    public static final Index PETS_OWNER_ID_IDX = Internal.createIndex(DSL.name("pets_owner_id_idx"), Pets.PETS, new OrderField[] { Pets.PETS.OWNER_ID }, false);
    public static final Index TYPES_NAME_IDX = Internal.createIndex(DSL.name("types_name_idx"), Types.TYPES, new OrderField[] { Types.TYPES.NAME }, false);
}