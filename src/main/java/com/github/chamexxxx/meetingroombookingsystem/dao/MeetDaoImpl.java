package com.github.chamexxxx.meetingroombookingsystem.dao;

import com.github.chamexxxx.meetingroombookingsystem.dto.Meet;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class MeetDaoImpl extends BaseDaoImpl<Meet, String> implements MeetDao {
    public MeetDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Meet.class);
    }
}
