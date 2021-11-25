package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

public class WeekPage extends com.calendarfx.view.page.WeekPage {
    public WeekPage() {
        super();
        configure();
    }

    private void configure() {
        setContextMenuCallback(new ContextMenuProvider());
        configureEntryViewFactory();
        setEntryContextMenuCallback(EntryContextMenu::new);
        setEntryDetailsCallback(param -> new EntryDetailsCallback().call(param));
    }

    private void configureEntryViewFactory() {
        var weekDayViewFactory = getDetailedWeekView().getWeekView().getWeekDayViewFactory();

        getDetailedWeekView().getWeekView().setWeekDayViewFactory(param -> {
            var weekDayView = weekDayViewFactory.call(param);

            weekDayView.setEntryViewFactory(DayEntryView::new);

            return weekDayView;
        });
    }
}
