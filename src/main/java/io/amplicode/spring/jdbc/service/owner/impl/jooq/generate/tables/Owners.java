/*
 * This file is generated by jOOQ.
 */
package io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables;


import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.Indexes;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.Keys;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.Public;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Pets.PetsPath;
import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.records.OwnersRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Owners extends TableImpl<OwnersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.owners</code>
     */
    public static final Owners OWNERS = new Owners();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OwnersRecord> getRecordType() {
        return OwnersRecord.class;
    }

    /**
     * The column <code>public.owners.id</code>.
     */
    public final TableField<OwnersRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.owners.first_name</code>.
     */
    public final TableField<OwnersRecord, String> FIRST_NAME = createField(DSL.name("first_name"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.owners.last_name</code>.
     */
    public final TableField<OwnersRecord, String> LAST_NAME = createField(DSL.name("last_name"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.owners.address</code>.
     */
    public final TableField<OwnersRecord, String> ADDRESS = createField(DSL.name("address"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.owners.city</code>.
     */
    public final TableField<OwnersRecord, String> CITY = createField(DSL.name("city"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.owners.telephone</code>.
     */
    public final TableField<OwnersRecord, String> TELEPHONE = createField(DSL.name("telephone"), SQLDataType.CLOB, this, "");

    private Owners(Name alias, Table<OwnersRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Owners(Name alias, Table<OwnersRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.owners</code> table reference
     */
    public Owners(String alias) {
        this(DSL.name(alias), OWNERS);
    }

    /**
     * Create an aliased <code>public.owners</code> table reference
     */
    public Owners(Name alias) {
        this(alias, OWNERS);
    }

    /**
     * Create a <code>public.owners</code> table reference
     */
    public Owners() {
        this(DSL.name("owners"), null);
    }

    public <O extends Record> Owners(Table<O> path, ForeignKey<O, OwnersRecord> childPath, InverseForeignKey<O, OwnersRecord> parentPath) {
        super(path, childPath, parentPath, OWNERS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class OwnersPath extends Owners implements Path<OwnersRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> OwnersPath(Table<O> path, ForeignKey<O, OwnersRecord> childPath, InverseForeignKey<O, OwnersRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private OwnersPath(Name alias, Table<OwnersRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public OwnersPath as(String alias) {
            return new OwnersPath(DSL.name(alias), this);
        }

        @Override
        public OwnersPath as(Name alias) {
            return new OwnersPath(alias, this);
        }

        @Override
        public OwnersPath as(Table<?> alias) {
            return new OwnersPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.OWNERS_LAST_NAME_IDX);
    }

    @Override
    public Identity<OwnersRecord, Integer> getIdentity() {
        return (Identity<OwnersRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<OwnersRecord> getPrimaryKey() {
        return Keys.OWNERS_PKEY;
    }

    private transient PetsPath _pets;

    /**
     * Get the implicit to-many join path to the <code>public.pets</code> table
     */
    public PetsPath pets() {
        if (_pets == null)
            _pets = new PetsPath(this, null, Keys.PETS__PETS_OWNER_ID_FKEY.getInverseKey());

        return _pets;
    }

    @Override
    public Owners as(String alias) {
        return new Owners(DSL.name(alias), this);
    }

    @Override
    public Owners as(Name alias) {
        return new Owners(alias, this);
    }

    @Override
    public Owners as(Table<?> alias) {
        return new Owners(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Owners rename(String name) {
        return new Owners(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Owners rename(Name name) {
        return new Owners(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Owners rename(Table<?> name) {
        return new Owners(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Owners where(Condition condition) {
        return new Owners(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Owners where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Owners where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Owners where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Owners where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Owners where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Owners where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Owners where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Owners whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Owners whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
