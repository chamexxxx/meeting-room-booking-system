package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.view.DayEntryView;
import com.github.chamexxxx.meetingroombookingsystem.dto.MeetDto;
import com.github.chamexxxx.meetingroombookingsystem.dto.ParticipantDto;
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

        participants.addListener((ListChangeListener<ParticipantDto>) c -> {
            participantsContainer.getChildren().clear();
            participantsContainer.getChildren().addAll(createParticipantLabels(participants));
        });

        getChildren().add(participantsContainer);
    }

    private MeetDto getMeet() {
        return (MeetDto) getEntry().getUserObject();
    }

    private VBox createParticipantsContainer() {
        var container = new VBox();

        container.setMinSize(100, 100);
        container.setManaged(false);
        container.setMouseTransparent(true);
        container.setPadding(new Insets(0, 3, 0, 3));

        return container;
    }

    private ArrayList<Label> createParticipantLabels(ObservableList<ParticipantDto> participants) {
        var labels = new ArrayList<Label>();

        if (participants != null) {
            participants.forEach(participant -> {
                labels.add(createParticipantLabel(participant));
            });
        }

        return labels;
    }

    protected Label createParticipantLabel(ParticipantDto participant) {
        var label = new Label("• " + participant.getName());

        label.setMouseTransparent(true);

        return label;
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);

        var titleHeight = titleLabel.prefHeight(contentWidth);
        var timeLabelHeight = startTimeLabel.prefHeight(contentWidth);
        var participantsBoxHeight = participantsContainer.prefHeight(contentWidth);

        if (contentHeight - titleHeight > timeLabelHeight) {
            participantsContainer.setVisible(true);
            participantsContainer.resizeRelocate(
                    snapPositionX(contentX),
                    snapPositionY(contentY + titleHeight + timeLabelHeight),
                    snapSizeX(contentWidth),
                    snapSizeY(participantsBoxHeight)
            );
        } else {
            participantsContainer.setVisible(false);
        }
    }
}
