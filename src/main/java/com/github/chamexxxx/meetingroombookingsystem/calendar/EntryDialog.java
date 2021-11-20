package com.github.chamexxxx.meetingroombookingsystem.calendar;

import com.calendarfx.model.Entry;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * Implementation of a dialog for editing entries in the calendar
 */
public class EntryDialog extends Dialog<Meet> {
    public EntryDialog(Entry<?> entry) {
        super();
        configure(entry);
    }

    private void configure(Entry<?> entry) {
        setDefaultTitle();
        setDefaultButtons();

        var entryDetailsView = new EntryDetailsView(entry);

        getDialogPane().setContent(entryDetailsView);
    }

    private void setDefaultTitle() {
        setTitle("Editing an appointment");
    }

    private void setDefaultButtons() {
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }
}
