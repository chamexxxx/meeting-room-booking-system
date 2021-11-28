package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.github.chamexxxx.meetingroombookingsystem.dto.Meet;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputEvent;
import javafx.util.Callback;

public class EntryDetailsCallback implements Callback<DateControl.EntryDetailsParameter, Boolean> {
    @Override
    public Boolean call(DateControl.EntryDetailsParameter param) {
        InputEvent evt = param.getInputEvent();
        var entry = (Entry<Meet>) param.getEntry();

        if (evt instanceof ContextMenuEvent) {
            var dialog = new EntryDialog(entry);

            dialog.showAndWait();
            return true;
        }

        return true;
    }
}
