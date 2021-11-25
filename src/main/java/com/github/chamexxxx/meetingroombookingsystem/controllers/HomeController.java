package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.calendarfx.model.*;
import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.calendar.WeeklyCalendar;
import com.github.chamexxxx.meetingroombookingsystem.control.TopBar;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private GridPane gridPane;

    private WeeklyCalendar weeklyCalendar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            var meetEntries = getMeetEntries();
            weeklyCalendar = new WeeklyCalendar(meetEntries);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        gridPane.add(weeklyCalendar.getWeekPage(), 0, 1);

        weeklyCalendar.setOnCreateEntryAction(entry -> {
            try {
                createMeet(entry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        weeklyCalendar.setOnUpdateEntryAction((entry, interval) -> {
            try {
                changeMeetDates(entry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        weeklyCalendar.setOnDeleteEntryAction(entry -> {
            try {
                deleteMeet(entry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private ArrayList<Entry<Meet>> getMeetEntries() throws SQLException {
        return convertMeetsToEntries(getMeets());
    }

    private ArrayList<Entry<Meet>> convertMeetsToEntries(List<Meet> meets) {
        var entries = new ArrayList<Entry<Meet>>();

        for (Meet meet : meets) {
            var startDate = meet.getStartDate();
            var endDate = meet.getEndDate();

            var entry = new Entry<Meet>(meet.getRoom());

            entry.setUserObject(meet);
            entry.setInterval(startDate.toLocalDateTime(), endDate.toLocalDateTime());

            entries.add(entry);
        }

        return entries;
    }

    private Timestamp getEntryStartTimestamp(Entry<Meet> entry) {
        return localDateTimeToTimestamp(entry.getStartAsLocalDateTime());
    }

    private Timestamp getEntryEndTimestamp(Entry<Meet> entry) {
        return localDateTimeToTimestamp(entry.getEndAsLocalDateTime());
    }

    private Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    private void createMeet(Entry<Meet> entry) throws SQLException {
        var startTimestamp = getEntryStartTimestamp(entry);
        var endTimestamp = getEntryEndTimestamp(entry);

        var meet = new Meet(entry.getTitle(), startTimestamp, endTimestamp);

        Database.getMeetDao().create(meet);

        entry.setUserObject(meet);
    }

    private void deleteMeet(Entry<Meet> entry) throws SQLException {
        var deleteBuilder = Database.getMeetDao().deleteBuilder();

        var meet = entry.getUserObject();

        deleteBuilder.where().eq("id", meet.getId());

        deleteBuilder.delete();
    }

    private void changeMeetDates(Entry<Meet> entry) throws SQLException {
        var updateBuilder = Database.getMeetDao().updateBuilder();

        var meet = entry.getUserObject();

        updateBuilder.where().eq("id", meet.getId());

        updateBuilder.updateColumnValue("startDate", localDateTimeToTimestamp(entry.getStartAsLocalDateTime()));
        updateBuilder.updateColumnValue("endDate", localDateTimeToTimestamp(entry.getEndAsLocalDateTime()));

        updateBuilder.update();
    }

    private List<Meet> getMeets() throws SQLException {
        return Database.getMeetDao().queryForAll();
    }
}
