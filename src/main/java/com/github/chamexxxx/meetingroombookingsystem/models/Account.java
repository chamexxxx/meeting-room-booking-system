package com.github.chamexxxx.meetingroombookingsystem.models;

import com.github.chamexxxx.meetingroombookingsystem.dao.AccountDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts", daoClass = AccountDaoImpl.class)
public class Account {
    @DatabaseField(id = true)
    private String name;

    @DatabaseField
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account() {

    }
}
