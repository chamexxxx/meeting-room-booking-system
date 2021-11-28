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
public class Meet {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, foreign = true)
    private Account account;

    @DatabaseField(canBeNull = false)
    private String room;

    @DatabaseField(canBeNull = false)
    private Timestamp startDate;

    @DatabaseField(canBeNull = false)
    private Timestamp endDate;

    @ForeignCollectionField()
    private ForeignCollection<Participant> participants;

    private final ObservableList<Participant> participantsList = FXCollections.observableArrayList();
    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
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

    public ObservableList<Participant> getParticipants() {
        return participantsList;
    }

    public Meet(String room, Timestamp startDate, Timestamp endDate) {
        this();
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Meet(String room, Timestamp startDate, Timestamp endDate, Account account) {
        this(room, startDate, endDate);
        this.account = account;
    }

    public Meet() {
        if (participants != null) {
            participantsList.addAll(participants);
        }

        participantsList.addListener((ListChangeListener<Participant>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    participants.addAll(c.getAddedSubList());
                } else if (c.wasRemoved()) {
                    participants.removeAll(c.getRemoved());
                }
            }
        });
    }
}
