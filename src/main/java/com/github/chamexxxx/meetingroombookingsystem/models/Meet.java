package com.github.chamexxxx.meetingroombookingsystem.models;

import com.github.chamexxxx.meetingroombookingsystem.dao.MeetDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "meets", daoClass = MeetDaoImpl.class)
public class Meet {
    @DatabaseField(canBeNull = false)
    private String room;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Meet() {

    }
}
