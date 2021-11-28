package com.github.chamexxxx.meetingroombookingsystem.dto;

import com.github.chamexxxx.meetingroombookingsystem.dao.AccountDaoImpl;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts", daoClass = AccountDaoImpl.class)
public class AccountDto {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(unique = true)
    private String username;

    @DatabaseField
    private String password;

    @ForeignCollectionField()
    private ForeignCollection<MeetDto> meets;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountDto() {

    }
}
