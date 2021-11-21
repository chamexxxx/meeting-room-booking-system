package com.github.chamexxxx.meetingroombookingsystem;

import com.github.chamexxxx.meetingroombookingsystem.models.Account;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import com.github.chamexxxx.meetingroombookingsystem.models.Participant;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    public static Dao<Meet, ?> meetDao;
    public static Dao<Account, ?> accountDao;
    private static ConnectionSource connectionSource = null;

    static {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");

            meetDao = DaoManager.createDao(connectionSource, Meet.class);
            accountDao = DaoManager.createDao(connectionSource, Account.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Account.class);
        TableUtils.createTableIfNotExists(connectionSource, Meet.class);
        TableUtils.createTableIfNotExists(connectionSource, Participant.class);
    }
}
