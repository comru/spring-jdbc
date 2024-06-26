/*
 * This file is generated by jOOQ.
 */
package io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.records;


import io.amplicode.spring.jdbc.service.owner.impl.jooq.generate.tables.Owners;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class OwnersRecord extends UpdatableRecordImpl<OwnersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.owners.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.owners.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.owners.first_name</code>.
     */
    public void setFirstName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.owners.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.owners.last_name</code>.
     */
    public void setLastName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.owners.last_name</code>.
     */
    public String getLastName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.owners.address</code>.
     */
    public void setAddress(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.owners.address</code>.
     */
    public String getAddress() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.owners.city</code>.
     */
    public void setCity(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.owners.city</code>.
     */
    public String getCity() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.owners.telephone</code>.
     */
    public void setTelephone(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.owners.telephone</code>.
     */
    public String getTelephone() {
        return (String) get(5);
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
     * Create a detached OwnersRecord
     */
    public OwnersRecord() {
        super(Owners.OWNERS);
    }

    /**
     * Create a detached, initialised OwnersRecord
     */
    public OwnersRecord(Integer id, String firstName, String lastName, String address, String city, String telephone) {
        super(Owners.OWNERS);

        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setCity(city);
        setTelephone(telephone);
        resetChangedOnNotNull();
    }
}
