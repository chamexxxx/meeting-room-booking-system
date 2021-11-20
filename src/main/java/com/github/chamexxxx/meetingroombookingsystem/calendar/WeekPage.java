package com.github.chamexxxx.meetingroombookingsystem.calendar;

import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputEvent;

public class WeekPage extends com.calendarfx.view.page.WeekPage {
    public WeekPage() {
        super();
        configure();
    }

    private void configure() {
        setContextMenuCallback(new ContextMenuProvider());
        configureEntryViewFactory();
        setEntryContextMenuCallback(EntryContextMenu::new);
        setEntryDetailsCallback(param -> {
            InputEvent evt = param.getInputEvent();
            var entry = param.getEntry();

            if (evt instanceof ContextMenuEvent) {
                var dialog = new EntryDialog(entry);

                dialog.showAndWait();

//                System.out.println(evt);
                return true;
            }

            return true;
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
