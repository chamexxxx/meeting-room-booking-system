package com.github.chamexxxx.meetingroombookingsystem.dao;

import com.github.chamexxxx.meetingroombookingsystem.dto.AccountDto;
import com.github.chamexxxx.meetingroombookingsystem.utils.PasswordHashing;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class AccountDaoImpl extends BaseDaoImpl<AccountDto, String> implements AccountDao {
    public AccountDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, AccountDto.class);
    }

    @Override
    public boolean verify(String username, String password) throws SQLException {
        var preparedQuery = queryBuilder()
                .where().eq("username", username)
                .prepare();

        var account = queryForFirst(preparedQuery);

        if (account == null) {
            return false;
        }

        return PasswordHashing.verify(password, account.getPassword());
    }

    @Override
    public AccountDto queryForUsername(String username) throws SQLException {
        return queryForFirst(queryBuilder().where().eq("username", username).prepare());
    }
}
