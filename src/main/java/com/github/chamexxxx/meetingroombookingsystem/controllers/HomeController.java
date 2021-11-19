package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.calendarfx.model.*;
import com.calendarfx.view.page.WeekPage;
import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.EntryDialog;
import com.github.chamexxxx.meetingroombookingsystem.calendar.ContextMenuProvider;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private WeekPage weekPage;

    ObservableList<CalendarSource> meetCalendarSources = FXCollections.observableArrayList();
    Calendar meetCalendar = new Calendar("meets");
    CalendarSource meetCalendarSource = new CalendarSource("Meets");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            startUpdatingTimeThread();
            initializeMeetEntries();
            configure();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeMeetEntries() throws SQLException {
        var meets = getMeets();

        var entries = convertMeetsToEntries(meets);

        for (Entry<Meet> entry : entries) {
            meetCalendar.addEntry(entry);
        }
    }

    private void configure() {
        configureCalendar();
        configureWeekPage();
    }

    private void configureCalendar() {
        meetCalendar.addEventHandler(this::calendarHandler);
        meetCalendar.setStyle(Calendar.Style.STYLE7);
        meetCalendarSource.getCalendars().add(meetCalendar);
        meetCalendarSources.add(meetCalendarSource);
    }

    private void configureWeekPage() {
        Bindings.bindContentBidirectional(weekPage.getCalendarSources(), meetCalendarSources);
        weekPage.setSelectionMode(SelectionMode.SINGLE);
        weekPage.setContextMenuCallback(new ContextMenuProvider());
        weekPage.getDetailedWeekView().getTimeScaleView().setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));

        var entryContextMenuCallback = weekPage.getEntryContextMenuCallback();

        weekPage.setEntryContextMenuCallback(param -> {
            var menu = entryContextMenuCallback.call(param);

            menu.getItems().remove(0, 2);

            return menu;
        });

        weekPage.setEntryDetailsCallback(param -> {
            InputEvent evt = param.getInputEvent();

            if (evt instanceof ContextMenuEvent) {

                return true;
            }

            return true;
        });
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

    private ArrayList<Entry<Meet>> convertMeetsToEntries(List<Meet> meets) {
        var entries = new ArrayList<Entry<Meet>>();

        for (Meet meet : meets) {
            var startDate = meet.getStartDate();
            var endDate = meet.getEndDate();

            var entry = new Entry<Meet>(meet.getRoom());

            entry.setInterval(startDate.toLocalDateTime(), endDate.toLocalDateTime());

            entries.add(entry);
        }

        return entries;
    }

    private void calendarHandler(CalendarEvent event) {
        var eventType = event.getEventType();
        System.out.println(event);

        try {
            if (eventType.toString().equals("ENTRY_CALENDAR_CHANGED")) {
                var calendar = event.getCalendar();
                var entry = event.getEntry();

                if (calendar != null) {
                    createMeet(entry);
                } else {
                    deleteMeet(entry);
                }
            } else if (eventType.toString().equals("ENTRY_INTERVAL_CHANGED")) {
                var entry = event.getEntry();
                var oldInterval = event.getOldInterval();

                changeMeetDates(entry, oldInterval.getStartDateTime(), oldInterval.getEndDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Timestamp getEntryStartTimestamp(Entry<?> entry) {
        return localDateTimeToTimestamp(entry.getStartAsLocalDateTime());
    }

    private Timestamp getEntryEndTimestamp(Entry<?> entry) {
        return localDateTimeToTimestamp(entry.getEndAsLocalDateTime());
    }

    private Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    private void createMeet(Entry<?> entry) throws SQLException {
        var startTimestamp = getEntryStartTimestamp(entry);
        var endTimestamp = getEntryEndTimestamp(entry);

        var meet = new Meet();

        meet.setRoom(entry.getTitle());
        meet.setStartDate(startTimestamp);
        meet.setEndDate(endTimestamp);

        Database.meetDao.create(meet);
    }

    private void deleteMeet(Entry<?> entry) throws SQLException {
        var startDate = localDateTimeToTimestamp(entry.getStartAsLocalDateTime());
        var endDate = localDateTimeToTimestamp(entry.getEndAsLocalDateTime());

        var deleteBuilder = Database.meetDao.deleteBuilder();

        deleteBuilder.where().eq("startDate", startDate);
        deleteBuilder.where().eq("endDate", endDate);

        deleteBuilder.delete();
    }

    private void changeMeetDates(Entry<?> entry, LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        var updateBuilder = Database.meetDao.updateBuilder();

        updateBuilder.where().eq("startDate", localDateTimeToTimestamp(startDate));
        updateBuilder.where().eq("endDate", localDateTimeToTimestamp(endDate));

        updateBuilder.updateColumnValue("startDate", localDateTimeToTimestamp(entry.getStartAsLocalDateTime()));
        updateBuilder.updateColumnValue("endDate", localDateTimeToTimestamp(entry.getEndAsLocalDateTime()));

        updateBuilder.update();
    }

    private List<Meet> getMeets() throws SQLException {
        return Database.meetDao.queryForAll();
    }
}
