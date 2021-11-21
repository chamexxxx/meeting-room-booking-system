package com.github.chamexxxx.meetingroombookingsystem.models;

import com.github.chamexxxx.meetingroombookingsystem.dao.MeetDaoImpl;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

@DatabaseTable(tableName = "meets", daoClass = MeetDaoImpl.class)
public class Meet {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String room;

    @DatabaseField(canBeNull = false)
    private Timestamp startDate;

    @DatabaseField(canBeNull = false)
    private Timestamp endDate;

    @ForeignCollectionField(eager = false)
    ForeignCollection<Participant> participants;

    public int getId() {
        return id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public ForeignCollection<Participant> getParticipants() {
        return participants;
    }

    public Meet() {

    }
}
