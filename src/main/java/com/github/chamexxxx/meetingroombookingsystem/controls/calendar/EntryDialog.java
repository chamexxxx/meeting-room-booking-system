package com.github.chamexxxx.meetingroombookingsystem.controls.calendar;

import com.calendarfx.model.Entry;
import com.github.chamexxxx.meetingroombookingsystem.Application;
import com.github.chamexxxx.meetingroombookingsystem.controls.Dialog;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * Implementation of a dialog for editing entries in the calendar.
 */
public class EntryDialog extends Dialog<Meet> {
    private EntryDetailsView entryDetailsView;

    public EntryDialog(Entry<Meet> entry) {
        super();
        configure(entry);
    }

    private void configure(Entry<Meet> entry) {
        setDefaultTitle();
        setDefaultButtons();

        entryDetailsView = new EntryDetailsView(entry);

        entryDetailsView.setPrefWidth(600);
        entryDetailsView.setPrefHeight(500);

        getDialogPane().setContent(entryDetailsView);
        getDialogPane().getStylesheets().addAll(Application.getAllStylesheets());
    }

    private void setDefaultTitle() {
        setTitle("Editing an appointment");
    }

    private void setDefaultButtons() {
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        var saveButton = (Button) getDialogPane().lookupButton(saveButtonType);
        var cancelButton = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);

        saveButton.getStyleClass().add("primary-button");
        cancelButton.getStyleClass().add("secondary-button");

        saveButton.addEventFilter(ActionEvent.ACTION, event -> {
            entryDetailsView.save();

            if (!entryDetailsView.isValidated()) {
                event.consume();
            }
        });
    }
}
