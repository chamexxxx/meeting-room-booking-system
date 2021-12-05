package com.github.chamexxxx.meetingroombookingsystem.utils;

import javafx.scene.control.Alert;

public class Dialogs {
    public static void error(String contentText) {
        var errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }
}
