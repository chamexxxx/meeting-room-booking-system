package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.view.DayEntryView;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import com.github.chamexxxx.meetingroombookingsystem.models.Participant;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * {@link impl.com.calendarfx.view.DayEntryViewSkin} implementation for custom skin by default
 */
public class DayEntryViewSkin extends impl.com.calendarfx.view.DayEntryViewSkin {
    private final VBox participantsContainer;

    public DayEntryViewSkin(DayEntryView view) {
        super(view);

        var meet = getMeet();

        var participants = meet.getParticipants();

        var participantLabels = createParticipantLabels(participants);

        participantsContainer = createParticipantsContainer();

        participantsContainer.getChildren().addAll(participantLabels);

        participants.addListener((ListChangeListener<? super Participant>) c -> {
            participantsContainer.getChildren().clear();
            participantsContainer.getChildren().addAll(createParticipantLabels(participants));
        });

        getChildren().add(participantsContainer);
    }

    private Meet getMeet() {
        return (Meet) getEntry().getUserObject();
    }

    private VBox createParticipantsContainer() {
        var container = new VBox();

        container.setMinSize(100, 100);
        container.setManaged(false);
        container.setMouseTransparent(true);
        container.setPadding(new Insets(0, 3, 0, 3));

        return container;
    }

    private ArrayList<Label> createParticipantLabels(ObservableList<Participant> participants) {
        var labels = new ArrayList<Label>();

        if (participants != null) {
            participants.forEach(participant -> {
                labels.add(createParticipantLabel(participant));
            });
        }

        return labels;
    }

    protected Label createParticipantLabel(Participant participant) {
        var label = new Label("• " + participant.getName());

        label.setMouseTransparent(true);

        return label;
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        var startTimeHeight = startTimeLabel.prefHeight(contentWidth);
        var titleHeight = titleLabel.prefHeight(contentWidth);
        var participantsBoxHeight = participantsContainer.prefHeight(contentWidth);

        startTimeLabel.resizeRelocate(snapPosition(contentX), snapPosition(contentY), snapSize(contentWidth), snapSize(startTimeHeight));

        if (contentHeight - startTimeHeight > titleHeight) {
            titleLabel.setVisible(true);
            titleLabel.resizeRelocate(snapPosition(contentX), snapPosition(contentY + startTimeHeight), snapSize(contentWidth), snapSize(titleHeight));
        } else {
            titleLabel.setVisible(false);
        }

        if (contentHeight - titleHeight > startTimeHeight) {
            participantsContainer.setVisible(true);
            participantsContainer.resizeRelocate(
                    snapPositionX(contentX),
                    snapPositionY(contentY + titleHeight + startTimeHeight + 3),
                    snapSizeX(contentWidth),
                    snapSizeY(participantsBoxHeight)
            );
        } else {
            participantsContainer.setVisible(false);
        }
    }
}
