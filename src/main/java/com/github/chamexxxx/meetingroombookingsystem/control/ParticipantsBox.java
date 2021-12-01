package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.models.Participant;
import javafx.geometry.Insets;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * A container with a list of participants with the ability to create, edit and delete
 */
public class ParticipantsBox extends ToDoList<Participant> {
    public ParticipantsBox(ArrayList<Participant> initialParticipants) {
        super(Participant::new, initialParticipants);

        var label = new Label("Participants");

        label.getStyleClass().add("text-sm");
        label.setPadding(new Insets(0, 0, 10, 0));

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
