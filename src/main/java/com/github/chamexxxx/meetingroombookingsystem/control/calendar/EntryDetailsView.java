package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.view.Messages;
import com.calendarfx.view.TimeField;
import com.github.chamexxxx.meetingroombookingsystem.control.ParticipantsBox;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Implementing your own {@link com.calendarfx.view.popover.EntryDetailsView} and {@link com.calendarfx.view.popover.EntryHeaderView} for use in a dialog
 */
public class EntryDetailsView extends VBox {
    private final Entry<Meet> entry;
    private final Meet meet;
    private final TextField titleField = new TextField();
    private final TimeField startTimeField;
    private final TimeField endTimeField;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private final ParticipantsBox participantsBox;

    public EntryDetailsView(Entry<Meet> entry) {
        this.entry = entry;

        meet = entry.getUserObject();

        var participants = meet.getParticipants();

        configureRegion();

        titleField.setText(entry.getTitle());
        titleField.getStyleClass().add("entry-details__title");
        bindDisableProperty(titleField);

        startTimeField = createTimeField(entry.getStartTime());
        endTimeField = createTimeField(entry.getEndTime());
        startDatePicker = createDatePicker(entry.getStartDate());
        endDatePicker = createDatePicker(entry.getEndDate());

        var startDateBox = new HBox(10);
        var endDateBox = new HBox(10);

        startDateBox.setAlignment(Pos.CENTER_LEFT);
        endDateBox.setAlignment(Pos.CENTER_LEFT);

        var startDateLabel = new Label(Messages.getString("EntryDetailsView.FROM"));
        var endDateLabel = new Label(Messages.getString("EntryDetailsView.TO"));

        startDateBox.getChildren().addAll(startDateLabel, startDatePicker, startTimeField);
        endDateBox.getChildren().addAll(endDateLabel, endDatePicker, endTimeField);

        startDatePicker.setValue(entry.getStartDate());
        endDatePicker.setValue(entry.getEndDate());

        participantsBox = new ParticipantsBox(new ArrayList<>(participants));

        var container = new VBox(10, titleField, startDateBox, endDateBox, new Separator(), participantsBox, new Separator());

        getChildren().add(container);
    }

    public void save() {
        entry.setTitle(titleField.getText());
        entry.changeStartDate(startDatePicker.getValue());
        entry.changeStartTime(startTimeField.getValue());
        entry.changeEndDate(endDatePicker.getValue());
        entry.changeEndTime(endTimeField.getValue());

        var participantModels = participantsBox.getModels();
        meet.getParticipants().addAll(participantModels);
        entry.setUserObject(meet);
    }

    private void configureRegion() {
        setPrefWidth(600);
        setPrefHeight(400);
    }

    private TimeField createTimeField(LocalTime localTime) {
        var timeField = new TimeField();

        timeField.setValue(localTime);
        bindDisableProperty(timeField);

        return timeField;
    }

    private DatePicker createDatePicker(LocalDate localDate) {
        var datePicker = new DatePicker();

        datePicker.setValue(localDate);
        bindDisableProperty(datePicker);

        return datePicker;
    }

    private void bindDisableProperty(Control control) {
        control.disableProperty().bind(entry.getCalendar().readOnlyProperty());
    }
}
