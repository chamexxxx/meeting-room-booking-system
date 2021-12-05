package com.github.chamexxxx.meetingroombookingsystem.controls.calendar;

import com.calendarfx.model.Entry;
import javafx.scene.control.Skin;

/**
 * Custom {@link com.calendarfx.view.DayEntryView} implementation changing default skin creation.
 *
 * <h2>Code Example</h2>The code below shows setting callback for week day view.
 *
 * <pre>
 * var weekDayViewFactory = getDetailedWeekView().getWeekView().getWeekDayViewFactory();
 *
 * weekPage.getDetailedWeekView().getWeekView().setWeekDayViewFactory(param -> {
 *      var weekDayView = weekDayViewFactory.call(param);
 *
 *      weekDayView.setEntryViewFactory(DayEntryView::new);
 *
 *      return weekDayView;
 * });
 * </pre>
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
