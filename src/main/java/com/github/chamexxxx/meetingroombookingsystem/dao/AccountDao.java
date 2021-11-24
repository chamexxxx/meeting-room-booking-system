package com.github.chamexxxx.meetingroombookingsystem.dao;

import com.github.chamexxxx.meetingroombookingsystem.models.Account;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public interface AccountDao extends Dao<Account, String> {
    boolean verify(String username, String password) throws SQLException;
}
