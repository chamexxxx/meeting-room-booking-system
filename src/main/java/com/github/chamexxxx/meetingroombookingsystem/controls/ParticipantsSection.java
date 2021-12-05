package com.github.chamexxxx.meetingroombookingsystem.controls;

import com.github.chamexxxx.meetingroombookingsystem.models.Participant;
import com.github.chamexxxx.meetingroombookingsystem.utils.FontIconFactory;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.CustomTextField;

import java.util.ArrayList;

/**
 * A container with a list of participants with the ability to create, edit and delete
 */
public class ParticipantsSection extends VBox {
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
