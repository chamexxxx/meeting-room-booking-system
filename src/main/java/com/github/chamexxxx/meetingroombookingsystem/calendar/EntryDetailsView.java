package com.github.chamexxxx.meetingroombookingsystem.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.view.Messages;
import com.calendarfx.view.TimeField;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Implementing your own {@link com.calendarfx.view.popover.EntryDetailsView} and {@link com.calendarfx.view.popover.EntryHeaderView} for use in a dialog
 */
public class EntryDetailsView extends VBox {
    private final Entry<?> entry;
    private final TextField titleField = new TextField();
    private final Label startDateLabel = new Label(Messages.getString("EntryDetailsView.FROM"));
    private final Label endDateLabel = new Label(Messages.getString("EntryDetailsView.TO"));

    public EntryDetailsView(Entry<?> entry) {
        this.entry = entry;

        configureRegion();

        titleField.setText(entry.getTitle());
        Bindings.bindBidirectional(titleField.textProperty(), entry.titleProperty());
        bindDisableProperty(titleField);

        var startTimeField = createTimeField(entry.getStartTime());
        var endTimeField = createTimeField(entry.getEndTime());
        var startDatePicker = createDatePicker(entry.getStartDate());
        var endDatePicker = createDatePicker(entry.getEndDate());

        entry.intervalProperty().addListener(it -> {
            startTimeField.setValue(entry.getStartTime());
            endTimeField.setValue(entry.getEndTime());
            startDatePicker.setValue(entry.getStartDate());
            endDatePicker.setValue(entry.getEndDate());
        });

        var startDateBox = new HBox(10);
        var endDateBox = new HBox(10);

        startDateBox.setAlignment(Pos.CENTER_LEFT);
        endDateBox.setAlignment(Pos.CENTER_LEFT);

        startDateBox.getChildren().addAll(startDateLabel, startDatePicker, startTimeField);
        endDateBox.getChildren().addAll(endDateLabel, endDatePicker, endTimeField);

        startDatePicker.setValue(entry.getStartDate());
        endDatePicker.setValue(entry.getEndDate());

        startDatePicker.valueProperty().addListener(evt -> entry.changeStartDate(startDatePicker.getValue(), true));
        startTimeField.valueProperty().addListener(evt -> entry.changeStartTime(startTimeField.getValue(), true));

        endDatePicker.valueProperty().addListener(evt -> entry.changeEndDate(endDatePicker.getValue(), false));
        endTimeField.valueProperty().addListener(evt -> entry.changeEndTime(endTimeField.getValue(), false));

        var container = new VBox(10, titleField, startDateBox, endDateBox);

        getChildren().add(container);
    }

    private void configureRegion() {
        setMinWidth(500);
        setMinHeight(150);
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
