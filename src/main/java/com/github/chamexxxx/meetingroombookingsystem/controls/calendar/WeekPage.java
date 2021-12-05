package com.github.chamexxxx.meetingroombookingsystem.controls.calendar;

import com.calendarfx.model.Entry;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;

/**
 * Custom {@link com.calendarfx.view.page.WeekPage} implementation changing default skin creation
 */
public class WeekPage extends com.calendarfx.view.page.WeekPage {
    public WeekPage() {
        super();
        configure();
    }

    private void configure() {
        getDetailedWeekView().setShowAllDayView(false);
        setContextMenuCallback(new ContextMenuProvider());
        setEntryContextMenuCallback(EntryContextMenu::new);
        setEntryDetailsCallback(param -> new EntryDetailsCallback().call(param));
        configureEntryFactory();
        configureEntryViewFactory();
        configureEntryEditPolicy();
    }

    private void configureEntryFactory() {
        var entryFactory = getEntryFactory();

        setEntryFactory(param -> {
            var entry = entryFactory.call(param);

            entry.setTitle("Room");
            entry.changeEndTime(entry.getStartTime().plusHours(2));

            return entry;
        });
    }

    private void configureEntryViewFactory() {
        var weekDayViewFactory = getDetailedWeekView().getWeekView().getWeekDayViewFactory();

        getDetailedWeekView().getWeekView().setWeekDayViewFactory(param -> {
            var weekDayView = weekDayViewFactory.call(param);

            weekDayView.setEntryViewFactory(DayEntryView::new);

            return weekDayView;
        });
    }

    private void configureEntryEditPolicy() {
        setEntryEditPolicy(param -> {
            var editOperation = param.getEditOperation();

            return !editOperation.equals(EditOperation.CHANGE_START) && !editOperation.equals(EditOperation.CHANGE_END);
        });
    }
}
