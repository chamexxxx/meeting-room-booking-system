package com.github.chamexxxx.meetingroombookingsystem.dao;

import com.github.chamexxxx.meetingroombookingsystem.dto.ParticipantDto;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class ParticipantDaoImpl extends BaseDaoImpl<ParticipantDto, String> implements ParticipantDao {
    public ParticipantDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ParticipantDto.class);
    }
}
