package com.github.chamexxxx.meetingroombookingsystem.dao;

import com.github.chamexxxx.meetingroombookingsystem.models.Account;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class AccountDaoImpl extends BaseDaoImpl<Account, String> {
    public AccountDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Account.class);
    }
}
