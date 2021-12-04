package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.view.Messages;
import com.calendarfx.view.TimeField;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import com.github.chamexxxx.meetingroombookingsystem.models.Participant;
import com.github.chamexxxx.meetingroombookingsystem.utils.FontIconFactory;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.control.textfield.CustomTextField;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void save() {
        entry.setTitle(titleField.getText());
        entry.changeStartDate(startDatePicker.getValue());
        entry.changeStartTime(startTimeField.getValue());
        entry.changeEndDate(endDatePicker.getValue());
        entry.changeEndTime(endTimeField.getValue());

        var participantModels = participantsSection.getParticipants();

        syncParticipants(participantModels);

        entry.setUserObject(meet);
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

/**
 * A container with a list of participants with the ability to create, edit and delete
 */
class ParticipantsSection extends VBox {
    private final VBox fields = new VBox(10);
    private final ObservableList<Participant> participants = FXCollections.observableArrayList();
    private final VBox container = new VBox();
    private final ScrollPane scrollPane;

    public ParticipantsSection(ArrayList<Participant> initialParticipants) {
        var label = new Label("Participants");

        label.getStyleClass().add("section__title");
        label.setPadding(new Insets(0, 0, 10, 0));

        initializeModelsListener();

        initialParticipants.forEach(p -> participants.addAll(new Participant(p.getId(), p.getMeetId(), p.getName())));

        if (fields.getChildren().size() == 0) {
            fields.setVisible(false);
        }

        var buttonBox = createButtonBox();

        scrollPane = createScrollPane(fields);
        container.getChildren().add(scrollPane);

        getChildren().addAll(label, container, buttonBox);
    }

    public ObservableList<Participant> getParticipants() {
        return participants;
    }

    public VBox getContainer() {
        return container;
    }

    private void initializeModelsListener() {
        participants.addListener((ListChangeListener<Participant>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (Participant addITem : c.getAddedSubList()) {
                        var customTextField = new CustomTextField();

                        customTextField.setText(addITem.getName());
                        customTextField.setPromptText("Participant name");
                        customTextField.getStyleClass().add("field");

                        fields.getChildren().add(customTextField);

                        var fontIcon = FontIconFactory.createFontIcon(FontIconFactory.ICON.DELETE, 17);

                        fontIcon.getStyleClass().add("cursor-hand");

                        customTextField.setRight(fontIcon);

                        customTextField.getRight().setOnMouseClicked(event -> {
                            fields.getChildren().remove(customTextField);
                            participants.remove(addITem);
                        });

                        customTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                            addITem.setName(newValue);
                        });
                    }
                }
            }
        });
    }

    private HBox createButtonBox() {
        var addButton = createAddButton();
        var buttonBox = new HBox(addButton);

        buttonBox.setPadding(new Insets(10, 0, 10, 0));
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        return buttonBox;
    }

    private ScrollPane createScrollPane(Pane pane) {
        var scrollPane = new ScrollPane(pane);

        scrollPane.setPrefHeight(200);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));
        scrollPane.setFocusTraversable(false);

        return scrollPane;
    }

    private Button createAddButton() {
        var button = new Button("Add participant".toUpperCase());

        button.getStyleClass().add("action-button");
        button.setOnAction(event -> {
            var participant = new Participant();

            participant.setName(null);
            participants.add(participant);

            fields.setVisible(true);
            scrollPane.setVvalue(1);
        });

        return button;
    }
}
