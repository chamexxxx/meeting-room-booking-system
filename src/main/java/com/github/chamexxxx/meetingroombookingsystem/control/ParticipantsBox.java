package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.dto.ParticipantDto;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * A container with a list of participants with the ability to create, edit and delete
 */
public class ParticipantsBox extends ToDoList<ParticipantDto> {
    public ParticipantsBox(ArrayList<ParticipantDto> initialParticipants) {
        super(ParticipantDto::new, initialParticipants);

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
