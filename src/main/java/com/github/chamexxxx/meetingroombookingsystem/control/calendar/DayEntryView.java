package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.model.Entry;
import javafx.scene.control.Skin;

/**
 * Custom {@link com.calendarfx.view.DayEntryView} implementation changing default skin creation
 */
public class DayEntryView extends com.calendarfx.view.DayEntryView {
    public DayEntryView(Entry<?> entry) {
        super(entry);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DayEntryViewSkin(this);
    }
}
