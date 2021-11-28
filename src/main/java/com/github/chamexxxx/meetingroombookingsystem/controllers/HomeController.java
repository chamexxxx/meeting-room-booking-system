package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.calendarfx.model.*;
import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.control.calendar.WeeklyCalendar;
import com.github.chamexxxx.meetingroombookingsystem.dto.MeetDto;
import com.github.chamexxxx.meetingroombookingsystem.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private BorderPane borderPane;

    private WeeklyCalendar weeklyCalendar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            var meetEntries = getMeetEntries();

            weeklyCalendar = new WeeklyCalendar(meetEntries);
            handleCalendarActions();
            borderPane.setCenter(weeklyCalendar.getWeekPage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleCalendarActions() {
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

    private ArrayList<Entry<MeetDto>> getMeetEntries() throws SQLException {
        return convertMeetsToEntries(getMeets());
    }

    private ArrayList<Entry<MeetDto>> convertMeetsToEntries(List<MeetDto> meets) {
        var entries = new ArrayList<Entry<MeetDto>>();

        for (MeetDto meetDto : meets) {
            var startDate = meetDto.getStartDate();
            var endDate = meetDto.getEndDate();

            var entry = new Entry<MeetDto>(meetDto.getRoom());

            entry.setUserObject(meetDto);
            entry.setInterval(startDate.toLocalDateTime(), endDate.toLocalDateTime());

            entries.add(entry);
        }

        return entries;
    }

    private Timestamp getEntryStartTimestamp(Entry<MeetDto> entry) {
        return localDateTimeToTimestamp(entry.getStartAsLocalDateTime());
    }

    private Timestamp getEntryEndTimestamp(Entry<MeetDto> entry) {
        return localDateTimeToTimestamp(entry.getEndAsLocalDateTime());
    }

    private Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    private void createMeet(Entry<MeetDto> entry) throws SQLException {
        var startTimestamp = getEntryStartTimestamp(entry);
        var endTimestamp = getEntryEndTimestamp(entry);

        var meetDto = new MeetDto(entry.getTitle(), startTimestamp, endTimestamp, UserSession.getAccount());

        Database.getMeetDao().create(meetDto);

        entry.setUserObject(meetDto);
    }

    private void deleteMeet(Entry<MeetDto> entry) throws SQLException {
        var deleteBuilder = Database.getMeetDao().deleteBuilder();

        var meet = entry.getUserObject();

        deleteBuilder.where().eq("id", meet.getId());

        deleteBuilder.delete();
    }

    private void changeMeetDates(Entry<MeetDto> entry) throws SQLException {
        var updateBuilder = Database.getMeetDao().updateBuilder();

        var meet = entry.getUserObject();

        updateBuilder.where().eq("id", meet.getId());

        updateBuilder.updateColumnValue("startDate", localDateTimeToTimestamp(entry.getStartAsLocalDateTime()));
        updateBuilder.updateColumnValue("endDate", localDateTimeToTimestamp(entry.getEndAsLocalDateTime()));

        updateBuilder.update();
    }

    private List<MeetDto> getMeets() throws SQLException {
        return Database.getMeetDao().queryForAll();
    }
}
