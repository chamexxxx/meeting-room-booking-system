package com.github.chamexxxx.meetingroombookingsystem;

import com.github.chamexxxx.meetingroombookingsystem.models.Account;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;

public class Database {
    public static Dao<Meet, ?> meetDao;
    public static Dao<Account, ?> accountDao;

    static {
        try {
            var connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");

            meetDao = DaoManager.createDao(connectionSource, Meet.class);
            accountDao = DaoManager.createDao(connectionSource, Account.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
