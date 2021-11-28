package com.github.chamexxxx.meetingroombookingsystem;

import com.github.chamexxxx.meetingroombookingsystem.dao.AccountDao;
import com.github.chamexxxx.meetingroombookingsystem.dao.MeetDao;
import com.github.chamexxxx.meetingroombookingsystem.dto.AccountDto;
import com.github.chamexxxx.meetingroombookingsystem.dto.MeetDto;
import com.github.chamexxxx.meetingroombookingsystem.dto.ParticipantDto;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    private static MeetDao meetDao;
    private static AccountDao accountDao;
    private static ConnectionSource connectionSource = null;

    static {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");

            meetDao = DaoManager.createDao(connectionSource, MeetDto.class);
            accountDao = DaoManager.createDao(connectionSource, AccountDto.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MeetDao getMeetDao() {
        return meetDao;
    }

    public static AccountDao getAccountDao() {
        return accountDao;
    }

    public static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, AccountDto.class);
        TableUtils.createTableIfNotExists(connectionSource, MeetDto.class);
        TableUtils.createTableIfNotExists(connectionSource, ParticipantDto.class);
    }
}
