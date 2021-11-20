package com.github.chamexxxx.meetingroombookingsystem.calendar;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.util.Callback;

public class EntryContextMenu extends ContextMenu {
    public EntryContextMenu(DateControl.EntryContextMenuParameter param) {
        var entryView = param.getEntryView();
        var control = param.getDateControl();

        var entry = entryView.getEntry();

        /*
         * Show dialog with entry details.
         */
        MenuItem editItem = new MenuItem("Edit");

        editItem.setOnAction(evt -> {
            Callback<DateControl.EntryDetailsParameter, Boolean> detailsCallback = control.getEntryDetailsCallback();
            if (detailsCallback != null) {
                ContextMenuEvent ctxEvent = param.getContextMenuEvent();
                DateControl.EntryDetailsParameter entryDetailsParam = new DateControl.EntryDetailsParameter(ctxEvent, control, entryView.getEntry(), control, ctxEvent.getScreenX(), ctxEvent.getScreenY());
                detailsCallback.call(entryDetailsParam);
            }
        });

        getItems().add(editItem);

        if (control.getEntryEditPolicy().call(new DateControl.EntryEditParameter(control, entry, DateControl.EditOperation.DELETE))) {
            /*
             * Delete calendar entry.
             */
            MenuItem delete = new MenuItem("Delete");

            delete.setDisable(param.getCalendar().isReadOnly());
            delete.setOnAction(evt -> {
                Calendar calendar = entry.getCalendar();

                if (!calendar.isReadOnly()) {
                    var selections = control.getSelections();
                    removeEntryFromCalendar(entry);
                    selections.forEach(this::removeEntryFromCalendar);
                }
            });

            getItems().add(delete);
        }
    }

    private void removeEntryFromCalendar(Entry<?> entry) {
        if (entry.isRecurrence()) {
            entry.getRecurrenceSourceEntry().removeFromCalendar();
        } else {
            entry.removeFromCalendar();
        }
    }
}
