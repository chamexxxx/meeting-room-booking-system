package com.github.chamexxxx.meetingroombookingsystem.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Participant {
    private final IntegerProperty id;
    private final IntegerProperty meetId;
    private final StringProperty name;

    public Participant(Integer id, Integer meetId, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.meetId = new SimpleIntegerProperty(meetId);
        this.name = new SimpleStringProperty(name);
    }

    public Participant() {
        this.id = new SimpleIntegerProperty();
        this.meetId = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty meetIdProperty() {
        return meetId;
    }

    public int getMeetId() {
        return meetId.get();
    }

    public void setMeetId(int meetId) {
        this.meetId.set(meetId);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
