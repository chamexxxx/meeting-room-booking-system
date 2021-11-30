package com.github.chamexxxx.meetingroombookingsystem.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "participants")
public class ParticipantDto {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, foreign = true)
    private MeetDto meet;

    @DatabaseField(canBeNull = false)
    private String name;

    public int getId() {
        return id;
    }

    public MeetDto getMeet() {
        return meet;
    }

    public void setMeet(MeetDto meet) {
        this.meet = meet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParticipantDto(String name) {
        this();
        this.name = name;
    }

    public ParticipantDto() {

    }
}
