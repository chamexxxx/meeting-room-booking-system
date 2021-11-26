package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.util.Callback;

public class EntryContextMenu extends ContextMenu {

    public EntryContextMenu(DateControl.EntryContextMenuParameter param) {
        var entryView = param.getEntryView();
        var control = param.getDateControl();

        var entry = entryView.getEntry();

        /*
         * Show dialog for edit entry.
         */
        var editItem = new MenuItem("Edit");

        editItem.setOnAction(evt -> {
            Callback<DateControl.EntryDetailsParameter, Boolean> detailsCallback = control.getEntryDetailsCallback();
            if (detailsCallback != null) {
                var ctxEvent = param.getContextMenuEvent();
                var entryDetailsParam = new DateControl.EntryDetailsParameter(ctxEvent, control, entryView.getEntry(), control, ctxEvent.getScreenX(), ctxEvent.getScreenY());
                detailsCallback.call(entryDetailsParam);
            }
        });

        getItems().add(editItem);

        /*
         * Delete calendar entry.
         */
        var deleteItem = new MenuItem("Delete");
        
        deleteItem.setOnAction(evt -> {
            var calendar = entry.getCalendar();

            if (!calendar.isReadOnly()) {
                var selections = control.getSelections();
                removeEntryFromCalendar(entry);
                selections.forEach(this::removeEntryFromCalendar);
            }
        });

        getItems().add(deleteItem);
    }

    private void removeEntryFromCalendar(Entry<?> entry) {
        if (entry.isRecurrence()) {
            entry.getRecurrenceSourceEntry().removeFromCalendar();
        } else {
            entry.removeFromCalendar();
        }
    }
}
