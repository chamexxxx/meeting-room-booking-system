package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

/**
 * Custom {@link com.calendarfx.view.page.WeekPage} implementation changing default skin creation
 */
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
        getDetailedWeekView().setShowAllDayView(false);
        setEntryEditPolicy(param -> {
            var editOperation = param.getEditOperation();

            return !editOperation.equals(EditOperation.CHANGE_START) && !editOperation.equals(EditOperation.CHANGE_END);
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
}
