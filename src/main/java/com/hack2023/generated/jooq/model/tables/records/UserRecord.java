/*
 * This file is generated by jOOQ.
 */
package com.hack2023.generated.jooq.model.tables.records;


import com.hack2023.generated.jooq.model.tables.User;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record5<Long, String, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>zkp_service.user.id</code>.
     */
    public UserRecord setId(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>zkp_service.user.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>zkp_service.user.email</code>.
     */
    public UserRecord setEmail(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>zkp_service.user.email</code>.
     */
    public String getEmail() {
        return (String) get(1);
    }

    /**
     * Setter for <code>zkp_service.user.somehash</code>.
     */
    public UserRecord setSomehash(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>zkp_service.user.somehash</code>.
     */
    public String getSomehash() {
        return (String) get(2);
    }

    /**
     * Setter for <code>zkp_service.user.created_on</code>.
     */
    public UserRecord setCreatedOn(LocalDateTime value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>zkp_service.user.created_on</code>.
     */
    public LocalDateTime getCreatedOn() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>zkp_service.user.updated_on</code>.
     */
    public UserRecord setUpdatedOn(LocalDateTime value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>zkp_service.user.updated_on</code>.
     */
    public LocalDateTime getUpdatedOn() {
        return (LocalDateTime) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, String, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return User.USER.ID;
    }

    @Override
    public Field<String> field2() {
        return User.USER.EMAIL;
    }

    @Override
    public Field<String> field3() {
        return User.USER.SOMEHASH;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return User.USER.CREATED_ON;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return User.USER.UPDATED_ON;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getEmail();
    }

    @Override
    public String component3() {
        return getSomehash();
    }

    @Override
    public LocalDateTime component4() {
        return getCreatedOn();
    }

    @Override
    public LocalDateTime component5() {
        return getUpdatedOn();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getEmail();
    }

    @Override
    public String value3() {
        return getSomehash();
    }

    @Override
    public LocalDateTime value4() {
        return getCreatedOn();
    }

    @Override
    public LocalDateTime value5() {
        return getUpdatedOn();
    }

    @Override
    public UserRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UserRecord value2(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UserRecord value3(String value) {
        setSomehash(value);
        return this;
    }

    @Override
    public UserRecord value4(LocalDateTime value) {
        setCreatedOn(value);
        return this;
    }

    @Override
    public UserRecord value5(LocalDateTime value) {
        setUpdatedOn(value);
        return this;
    }

    @Override
    public UserRecord values(Long value1, String value2, String value3, LocalDateTime value4, LocalDateTime value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Long id, String email, String somehash, LocalDateTime createdOn, LocalDateTime updatedOn) {
        super(User.USER);

        setId(id);
        setEmail(email);
        setSomehash(somehash);
        setCreatedOn(createdOn);
        setUpdatedOn(updatedOn);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(com.hack2023.generated.jooq.model.tables.pojos.User value) {
        super(User.USER);

        if (value != null) {
            setId(value.getId());
            setEmail(value.getEmail());
            setSomehash(value.getSomehash());
            setCreatedOn(value.getCreatedOn());
            setUpdatedOn(value.getUpdatedOn());
        }
    }
}
