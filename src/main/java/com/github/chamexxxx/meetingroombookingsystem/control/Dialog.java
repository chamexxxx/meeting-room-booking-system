package com.github.chamexxxx.meetingroombookingsystem.control;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Dialog<R> extends javafx.scene.control.Dialog<R> {
    public Dialog() {
        super();
        setIcon(new Image("file:logo.png"));
    }

    public void setIcon(Image image) {
        var stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);
    }
}
