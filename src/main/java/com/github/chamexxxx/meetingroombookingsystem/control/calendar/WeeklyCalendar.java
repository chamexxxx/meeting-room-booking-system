package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.model.*;
import com.github.chamexxxx.meetingroombookingsystem.dto.MeetDto;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Implementing a weekly calendar for booking meets based on the CalendarFX dependency
 */
public class WeeklyCalendar {
    private final WeekPage weekPage = new WeekPage();
    private final Calendar calendar = new Calendar("meets");
    private final CalendarSource calendarSource = new CalendarSource("Meets");
    private final ObservableList<CalendarSource> calendarSources = FXCollections.observableArrayList();
    private Consumer<Entry<MeetDto>> onCreateEntryAction;
    private BiConsumer<Entry<MeetDto>, Interval> onUpdateEntryAction;
    private Consumer<Entry<MeetDto>> onDeleteEntryAction;

    public WeeklyCalendar(ArrayList<Entry<MeetDto>> entries) {
        startUpdatingTimeThread();
        initializeEntries(entries);
        configure();
    }

    public WeekPage getWeekPage() {
        return weekPage;
    }

    public void setOnCreateEntryAction(Consumer<Entry<MeetDto>> action) {
        this.onCreateEntryAction = action;
    }

    public void setOnUpdateEntryAction(BiConsumer<Entry<MeetDto>, Interval> onUpdateEntryAction) {
        this.onUpdateEntryAction = onUpdateEntryAction;
    }

    public void setOnDeleteEntryAction(Consumer<Entry<MeetDto>> onDeleteEntryAction) {
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

    private void initializeEntries(ArrayList<Entry<MeetDto>> entries) {
        entries.forEach(calendar::addEntry);
    }

    private void configure() {
        configureCalendar();
        bindWeekPage();
    }

    private void configureCalendar() {
        calendar.addEventHandler(this::calendarHandler);
        calendar.setStyle(com.calendarfx.model.Calendar.Style.STYLE7);
        calendarSource.getCalendars().add(calendar);
        calendarSources.add(calendarSource);
    }

    private void bindWeekPage() {
        Bindings.bindContentBidirectional(weekPage.getCalendarSources(), calendarSources);
    }

    private void calendarHandler(CalendarEvent event) {
        var eventType = event.getEventType();
        var entry = (Entry<MeetDto>) event.getEntry();

        if (eventType.equals(CalendarEvent.ENTRY_CALENDAR_CHANGED)) {
            var calendar = event.getCalendar();
            if (calendar != null) {
                onCreateEntryAction.accept(entry);
            } else {
                onDeleteEntryAction.accept(entry);
            }
        } else if (eventType.equals(CalendarEvent.ENTRY_INTERVAL_CHANGED)) {
            var oldInterval = event.getOldInterval();

            onUpdateEntryAction.accept(entry, oldInterval);
        }
    }
}
