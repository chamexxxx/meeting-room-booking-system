package com.github.chamexxxx.meetingroombookingsystem.control.forms;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public abstract class Form extends VBox {
    protected final void configureControl(Control field) {
        if (field instanceof TextField) {
            field.getStyleClass().add("field");
        }

        field.setFocusTraversable(false);
        field.setPrefHeight(30);
    }

    protected final void configureControls(Control... fields) {
        for (var field : fields) {
            configureControl(field);
        }
    }

    public abstract void reset();
}
