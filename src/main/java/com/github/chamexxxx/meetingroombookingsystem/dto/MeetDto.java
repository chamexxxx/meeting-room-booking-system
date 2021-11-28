package com.github.chamexxxx.meetingroombookingsystem.dto;

import com.github.chamexxxx.meetingroombookingsystem.dao.MeetDaoImpl;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

@DatabaseTable(tableName = "meets", daoClass = MeetDaoImpl.class)
public class MeetDto {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, foreign = true)
    private AccountDto account;

    @DatabaseField(canBeNull = false)
    private String room;

    @DatabaseField(canBeNull = false)
    private Timestamp startDate;

    @DatabaseField(canBeNull = false)
    private Timestamp endDate;

    @ForeignCollectionField()
    private ForeignCollection<ParticipantDto> participants;

    public int getId() {
        return id;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
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

    public ForeignCollection<ParticipantDto> getParticipants() {
        return participants;
    }

    public MeetDto(String room, Timestamp startDate, Timestamp endDate) {
        this();
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MeetDto(String room, Timestamp startDate, Timestamp endDate, AccountDto account) {
        this(room, startDate, endDate);
        this.account = account;
    }

    public MeetDto() {
    }
}
