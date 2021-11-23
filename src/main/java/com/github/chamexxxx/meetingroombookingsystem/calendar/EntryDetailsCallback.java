package com.github.chamexxxx.meetingroombookingsystem.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
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

//                System.out.println(evt);
            return true;
        }

        return true;
    }
}
