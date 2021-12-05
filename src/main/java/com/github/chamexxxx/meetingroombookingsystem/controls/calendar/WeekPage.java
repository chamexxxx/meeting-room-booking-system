package com.github.chamexxxx.meetingroombookingsystem.controls.calendar;

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
        configureEntryViewFactory();
        configureEntryEditPolicy();
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
