package com.github.chamexxxx.meetingroombookingsystem;

import com.calendarfx.model.*;
import com.calendarfx.view.page.WeekPage;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private WeekPage weekPage;

    Calendar meetCalendar = new Calendar("meets");
    CalendarSource meetCalendarSource = new CalendarSource("Meets");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            configure();
            initializeMeetEntries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configure() {
        configureWeekPage();
        configureCalendar();
    }

    private void configureWeekPage() {
        weekPage.getCalendarSources().add(meetCalendarSource);
    }

    private void configureCalendar() {
        meetCalendar.addEventHandler(this::calendarHandler);
        meetCalendarSource.getCalendars().add(meetCalendar);
    }

    private void initializeMeetEntries() throws SQLException {
        var meets = getMeets();

        var entries = convertMeetsToEntries(meets);

        for (Entry<Meet> entry : entries) {
            meetCalendar.addEntry(entry);
        }
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

        if (eventType.toString().equals("ENTRY_CALENDAR_CHANGED")) {
            var calendar = event.getCalendar();
            var entry = event.getEntry();

            try {
                if (calendar != null) {
                    createMeet(entry);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Timestamp getEntryStartTimestamp(Entry <?> entry) {
        return localDateTimeToTimestamp(entry.getStartAsLocalDateTime());
    }

    private Timestamp getEntryEndTimestamp(Entry <?> entry) {
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

        return columns;
    }

    private List<Meet> getMeets() throws SQLException {
        return Database.meetDao.queryForAll();
    }

    private void configureWeekPage(WeekPage weekPage) {
        weekPage.getCalendarSources().add(meetCalendarSource);

        for (String day : days) {
            var column = new TableColumn<Meet, String>(day);

            this.configureColumn(column, 50);

            columns.add(column);
        }

        return columns;
    }

    private void configureTable(TableView<Meet> table, ObservableList<Meet> items) {
        table.setItems(items);
        table.setSelectionModel(null);
        table.setFocusTraversable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configureColumn(TableColumn<?,?> column, Integer size) {
        column.setReorderable(false);
        column.setSortable(false);
        column.setMaxWidth(1f * Integer.MAX_VALUE * size);
    }
}
