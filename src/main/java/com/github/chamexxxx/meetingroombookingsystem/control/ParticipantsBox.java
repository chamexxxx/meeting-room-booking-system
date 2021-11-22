package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.models.Participant;
import javafx.scene.control.*;

import java.util.ArrayList;

public class ParticipantsBox extends ToDoList<Participant> {
    public ParticipantsBox(ArrayList<Participant> tasks) {
        super(Participant::new, tasks);

        var label = new Label("Participants");

        getChildren().add(0, label);
    }

    @Override
    protected String getAddButtonText() {
        return "Add participant".toUpperCase();
    }

    @Override
    protected String getFieldPromptText() {
        return "Participant name";
    }
}
