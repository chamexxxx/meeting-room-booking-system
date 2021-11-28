package com.github.chamexxxx.meetingroombookingsystem.dao;

import com.github.chamexxxx.meetingroombookingsystem.dto.AccountDto;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public interface AccountDao extends Dao<AccountDto, String> {
    boolean verify(String username, String password) throws SQLException;
    AccountDto queryForUsername(String username) throws SQLException;
}
