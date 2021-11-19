package com.github.chamexxxx.meetingroombookingsystem.control;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.function.Consumer;

public class LoginForm extends VBox {
    public TextField usernameField = new TextField();
    public PasswordField passwordField = new PasswordField();
    public Button button = new Button();
    public Consumer<HashMap<String, String>> onAction;

    public LoginForm() {
        setDefaultSpacing();
        configureControls(usernameField, passwordField, button);
        setDefaultFieldPromptTexts();
        configureButton();
        getChildren().setAll(usernameField, passwordField, button);
    }

    public final void setOnAction(Consumer<HashMap<String, String>> onAction) {
        this.onAction = onAction;
    }

    protected final void configureControls(Control... fields) {
        for (var field : fields) {
            configureControl(field);
        }
    }

    protected final void configureControl(Control field) {
        field.setFocusTraversable(false);
        field.setPrefHeight(30);
    }

    protected void setDefaultButtonText() {
        button.setText("Log in".toUpperCase());
    }

    private void configureButton() {
        button.setMaxWidth(Double.MAX_VALUE);
        setDefaultButtonText();
        setOnDefaultButtonAction();
    }

    protected HashMap<String, String> getFormData() {
        var hashMap = new HashMap<String, String>();

        hashMap.put("username", usernameField.getText());
        hashMap.put("password", passwordField.getText());

        return hashMap;
    }

    private void setDefaultSpacing() {
        setSpacing(10);
    }

    private void setDefaultFieldPromptTexts() {
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
    }

    private void setOnDefaultButtonAction() {
        button.setOnAction(event -> {
            if (onAction != null) {
                onAction.accept(getFormData());
            }
        });
    }
}
