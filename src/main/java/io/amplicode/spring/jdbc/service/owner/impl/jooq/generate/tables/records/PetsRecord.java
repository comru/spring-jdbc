/*
 * This file is generated by jOOQ.
 */
package io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.records;


import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Pets;

import java.time.LocalDate;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class PetsRecord extends UpdatableRecordImpl<PetsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.pets.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.pets.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.pets.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.pets.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.pets.birth_date</code>.
     */
    public void setBirthDate(LocalDate value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.pets.birth_date</code>.
     */
    public LocalDate getBirthDate() {
        return (LocalDate) get(2);
    }

    /**
     * Setter for <code>public.pets.type_id</code>.
     */
    public void setTypeId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.pets.type_id</code>.
     */
    public Integer getTypeId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.pets.owner_id</code>.
     */
    public void setOwnerId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.pets.owner_id</code>.
     */
    public Integer getOwnerId() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PetsRecord
     */
    public PetsRecord() {
        super(Pets.PETS);
    }

    /**
     * Create a detached, initialised PetsRecord
     */
    public PetsRecord(Integer id, String name, LocalDate birthDate, Integer typeId, Integer ownerId) {
        super(Pets.PETS);

        setId(id);
        setName(name);
        setBirthDate(birthDate);
        setTypeId(typeId);
        setOwnerId(ownerId);
        resetChangedOnNotNull();
    }
}
