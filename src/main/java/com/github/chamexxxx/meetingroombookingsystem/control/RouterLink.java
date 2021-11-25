package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.Router;
import javafx.scene.control.Hyperlink;

public class RouterLink extends Hyperlink {
    private String to;

    public RouterLink() {
        super();
        setOnAction(event -> Router.switchScene(to));
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
