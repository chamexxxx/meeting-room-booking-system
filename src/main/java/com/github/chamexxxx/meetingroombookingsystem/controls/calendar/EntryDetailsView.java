package com.github.chamexxxx.meetingroombookingsystem.controls.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.view.Messages;
import com.calendarfx.view.TimeField;
import com.github.chamexxxx.meetingroombookingsystem.controls.ParticipantsSection;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import com.github.chamexxxx.meetingroombookingsystem.models.Participant;
import com.github.chamexxxx.meetingroombookingsystem.utils.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Implementing your own {@link com.calendarfx.view.popover.EntryDetailsView}
 * and {@link com.calendarfx.view.popover.EntryHeaderView} for use in a dialog.
 */
public class EntryDetailsView extends VBox {
    private final Entry<Meet> entry;
    private final Meet meet;
    private final TextField titleField = new TextField();
    private final TimeField startTimeField;
    private final TimeField endTimeField;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private boolean validated = false;
    private final ParticipantsSection participantsSection;

    public EntryDetailsView(Entry<Meet> entry) {
        this.entry = entry;

        meet = entry.getUserObject();

        var participants = meet.getParticipants();

        titleField.setText(entry.getTitle());
        titleField.getStyleClass().add("entry-details__title");
        titleField.setPromptText("Room name");

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

        startDateLabel.getStyleClass().add("text-sm");
        endDateLabel.getStyleClass().add("text-sm");

        startDateBox.getChildren().addAll(startDateLabel, startDatePicker, startTimeField);
        endDateBox.getChildren().addAll(endDateLabel, endDatePicker, endTimeField);

        startDatePicker.setValue(entry.getStartDate());
        endDatePicker.setValue(entry.getEndDate());

        participantsSection = new ParticipantsSection(new ArrayList<>(participants));

        participantsSection.getContainer().getStyleClass().add("section__content");

        var datesLabel = new Label("Booking time");
        datesLabel.getStyleClass().add("section__title");

        var datesContainer = new VBox(10, startDateBox, endDateBox);
        datesContainer.getStyleClass().add("section__content");

        var datesSection = new VBox(10, datesLabel, datesContainer);

        var container = new VBox(10, titleField, new Separator(), datesSection, participantsSection, new Separator());

        getChildren().add(container);
    }

    public boolean isValidated() {
        return validated;
    }

    public void save() {
        var title = titleField.getText().trim();
        var startDate = startDatePicker.getValue();
        var startTime = startTimeField.getValue();
        var endDate = endDatePicker.getValue();
        var endTime = endTimeField.getValue();
        var startDateTime = startDate.atTime(startTime);
        var endDateTime = endDate.atTime(endTime);

        validated = validate(title, startDateTime, endDateTime);

        if (!validated) {
            return;
        }

        entry.setTitle(title);
        entry.changeStartDate(startDate);
        entry.changeStartTime(startTime);
        entry.changeEndDate(endDate);
        entry.changeEndTime(endTime);

        var participantModels = participantsSection.getParticipants()
                .stream()
                .filter(participant -> participant.getName() != null)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        syncParticipants(participantModels);

        entry.setUserObject(meet);
    }

    private boolean validate(String title, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (title.isEmpty()) {
            Dialogs.error("The title is required");
            return false;
        }

        var wholeMinutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime);

        if (wholeMinutes < 30) {
            Dialogs.error("Minimum booking interval 30 minutes");
            return false;
        }

        var hours = wholeMinutes / 60;
        var minutes = wholeMinutes - hours * 60;

        if (hours > 24 || (hours == 24 && minutes > 0)) {
            Dialogs.error("Maximum booking interval 24 hours");
            return false;
        }

        return true;
    }

    private void syncParticipants(ObservableList<Participant> participants) {
        var participantsIterator = meet.getParticipants().iterator();

        while (participantsIterator.hasNext()) {
            var participant = participantsIterator.next();
            var optionalParticipant = participants
                    .stream()
                    .filter(p -> p.getId() == participant.getId())
                    .findFirst();

            if (optionalParticipant.isPresent()) {
                var participantModel = optionalParticipant.get();

                if (!participantModel.getName().equals(participant.getName())) {
                    participant.setName(participantModel.getName());
                }
            } else {
                participantsIterator.remove();
            }
        }

        participants.forEach(participant -> {
            if (participant.getId() == 0) {
                participant.setMeetId(meet.getId());
                meet.getParticipants().add(participant);
            }
        });
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

