package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Dialog<R> extends javafx.scene.control.Dialog<R> {
    public Dialog() {
        super();
        setIcon(Application.getLogoImage());
    }

    public void setIcon(Image image) {
        var stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(image);
    }
}
