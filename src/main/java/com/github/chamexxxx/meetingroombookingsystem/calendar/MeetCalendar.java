package com.github.chamexxxx.meetingroombookingsystem.calendar;

import com.calendarfx.model.*;
import com.calendarfx.view.page.WeekPage;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Implementing a weekly calendar for booking meets based on the CalendarFX dependency
 */
public class MeetCalendar {
    private final WeekPage weekPage = new WeekPage();
    private final Calendar calendar = new Calendar("meets");
    private final CalendarSource calendarSource = new CalendarSource("Meets");
    private final ObservableList<CalendarSource> calendarSources = FXCollections.observableArrayList();
    private Consumer<Entry<?>> onCreateEntryAction;
    private BiConsumer<Entry<?>, Interval> onUpdateEntryAction;
    private Consumer<Entry<?>> onDeleteEntryAction;

    public MeetCalendar(ArrayList<Entry<Meet>> entries) {
        startUpdatingTimeThread();
        initializeEntries(entries);
        configure();
    }

    public WeekPage getWeekPage() {
        return weekPage;
    }

    public void setOnCreateEntryAction(Consumer<Entry<?>> action) {
        this.onCreateEntryAction = action;
    }

    public void setOnUpdateEntryAction(BiConsumer<Entry<?>, Interval> onUpdateEntryAction) {
        this.onUpdateEntryAction = onUpdateEntryAction;
    }

    public void setOnDeleteEntryAction(Consumer<Entry<?>> onDeleteEntryAction) {
        this.onDeleteEntryAction = onDeleteEntryAction;
    }

    private void startUpdatingTimeThread() {
        var updateTimeThread = createUpdatingTimeThread();

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }

    private Thread createUpdatingTimeThread() {
        return new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        weekPage.setToday(LocalDate.now());
                        weekPage.setTime(LocalTime.now());
                    });

                    try {
                        // update every 5 seconds
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        };
    }

    private void initializeEntries(ArrayList<Entry<Meet>> entries) {
        entries.forEach(calendar::addEntry);
    }

    private void configure() {
        configureCalendar();
        configureWeekPage();
    }

    private void configureCalendar() {
        calendar.addEventHandler(this::calendarHandler);
        calendar.setStyle(com.calendarfx.model.Calendar.Style.STYLE7);
        calendarSource.getCalendars().add(calendar);
        calendarSources.add(calendarSource);
    }

    private void configureWeekPage() {
        Bindings.bindContentBidirectional(weekPage.getCalendarSources(), calendarSources);
        weekPage.setSelectionMode(SelectionMode.SINGLE);
        weekPage.setContextMenuCallback(new ContextMenuProvider());
        weekPage.getDetailedWeekView().getTimeScaleView().setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
        configureEntryViewFactory();

        var entryContextMenuCallback = weekPage.getEntryContextMenuCallback();

        weekPage.setEntryContextMenuCallback(param -> {
            var menu = entryContextMenuCallback.call(param);

            menu.getItems().get(0).setText("Edit");
            menu.getItems().remove(1, 2);

            return menu;
        });

        weekPage.setEntryDetailsCallback(param -> {
            InputEvent evt = param.getInputEvent();
            var entry = param.getEntry();

            if (evt instanceof ContextMenuEvent) {
                var dialog = new EntryDialog(entry);

                dialog.showAndWait();

                return true;
            }

            return true;
        });
    }

    private void calendarHandler(CalendarEvent event) {
        var eventType = event.getEventType();
        System.out.println(event);

        if (eventType.toString().equals("ENTRY_CALENDAR_CHANGED")) {
            var calendar = event.getCalendar();
            var entry = event.getEntry();

            if (calendar != null) {
                onCreateEntryAction.accept(entry);
            } else {
                onDeleteEntryAction.accept(entry);
            }
        } else if (eventType.toString().equals("ENTRY_INTERVAL_CHANGED")) {
            var entry = event.getEntry();
            var oldInterval = event.getOldInterval();

            onUpdateEntryAction.accept(entry, oldInterval);
        }
    }

    private void configureEntryViewFactory() {
        var weekDayViewFactory = weekPage.getDetailedWeekView().getWeekView().getWeekDayViewFactory();

        weekPage.getDetailedWeekView().getWeekView().setWeekDayViewFactory(param -> {
            var weekDayView = weekDayViewFactory.call(param);

            weekDayView.setEntryViewFactory(DayEntryView::new);

            return weekDayView;
        });
    }
}
