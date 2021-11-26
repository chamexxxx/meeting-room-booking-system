package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.utils.FontIconFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

import java.util.HashMap;
import java.util.function.Consumer;

public class LoginForm extends VBox {
    public CustomTextField usernameField = new CustomTextField();
    public CustomPasswordField passwordField = new CustomPasswordField();
    public Button button = new Button();
    public Consumer<HashMap<String, String>> onAction;

    public LoginForm() {
        setDefaultSpacing();
        setDefaultFieldPromptTexts();

        configureControls(usernameField, passwordField, button);
        configureButton();

        usernameField.setLeft(createUsernameIconContainer());
        passwordField.setLeft(createPasswordIconContainer());

        getChildren().setAll(usernameField, passwordField, button);
    }

    public final void setOnAction(Consumer<HashMap<String, String>> onAction) {
        this.onAction = onAction;
    }

    public void reset() {
        usernameField.clear();
        passwordField.clear();
    }

    protected HBox createUsernameIconContainer() {
        return createIconContainer(FontIconFactory.createFontIcon(FontIconFactory.ICON.USER, 20));
    }

    protected HBox createPasswordIconContainer() {
        return createIconContainer(FontIconFactory.createFontIcon(FontIconFactory.ICON.LOCK, 20));
    }

    protected HBox createIconContainer(Node icon) {
        var container = new HBox(icon);

        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(0, 0, 0, 5));

        return container;
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

    protected HashMap<String, String> getFormData() {
        var hashMap = new HashMap<String, String>();

        hashMap.put("username", usernameField.getText());
        hashMap.put("password", passwordField.getText());

        return hashMap;
    }

    private void configureButton() {
        button.setMaxWidth(Double.MAX_VALUE);
        setDefaultButtonText();
        setOnDefaultButtonAction();
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
