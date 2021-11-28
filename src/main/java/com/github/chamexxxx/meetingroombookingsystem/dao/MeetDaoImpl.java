package com.github.chamexxxx.meetingroombookingsystem.dao;

import com.github.chamexxxx.meetingroombookingsystem.dto.MeetDto;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class MeetDaoImpl extends BaseDaoImpl<MeetDto, String> implements MeetDao {
    public MeetDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, MeetDto.class);
    }
}
