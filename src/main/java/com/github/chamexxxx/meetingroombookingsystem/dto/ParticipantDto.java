package com.github.chamexxxx.meetingroombookingsystem.dto;

import com.github.chamexxxx.meetingroombookingsystem.control.ToDoList;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "participants")
public class ParticipantDto implements ToDoList.Model {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, foreign = true)
    private MeetDto meet;

    @DatabaseField(canBeNull = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
