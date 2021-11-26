package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.Router;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

public class RouterLink extends Hyperlink {
    private String to;
    private EventHandler<ActionEvent> onBeforeAction;
    private EventHandler<ActionEvent> onAfterAction;

    public RouterLink() {
        super();
        setOnAction(event -> {
            if (onBeforeAction != null) {
                onBeforeAction.handle(event);
            }

            Router.switchScene(to);

            if (onAfterAction != null) {
                onAfterAction.handle(event);
            }
        });
    }

    public void setOnBeforeAction(EventHandler<ActionEvent> onBeforeAction) {
        this.onBeforeAction = onBeforeAction;
    }

    public void setOnAfterAction(EventHandler<ActionEvent> onAfterAction) {
        this.onAfterAction = onAfterAction;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
