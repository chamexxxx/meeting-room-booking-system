package com.github.chamexxxx.meetingroombookingsystem.control.forms;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.function.Consumer;

public abstract class Form extends VBox {
    protected Button button = createButton();
    private Consumer<HashMap<String, String>> onAction;

    public final void setOnAction(Consumer<HashMap<String, String>> onAction) {
        this.onAction = onAction;
    }

    public abstract void reset();

    protected abstract HashMap<String, String> getFormData();

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

    protected Button createButton() {
        var button = new Button();

        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("primary-button");

        setOnButtonAction(button);

        return button;
    }

    private void setOnButtonAction(Button button) {
        button.setOnAction(event -> {
            if (onAction != null) {
                onAction.accept(getFormData());
            }
        });
    }
}
