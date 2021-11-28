package com.github.chamexxxx.meetingroombookingsystem.control.forms;

import org.controlsfx.control.textfield.CustomPasswordField;

import java.util.HashMap;

public class RegisterForm extends LoginForm {
    public final CustomPasswordField confirmPasswordField = new CustomPasswordField();

    public RegisterForm() {
        super();

        configureControl(confirmPasswordField);

        confirmPasswordField.setPromptText("Confirm password");
        confirmPasswordField.setLeft(createPasswordIconContainer());
        confirmPasswordField.getStyleClass().add("field-md");

        getChildren().add(2, confirmPasswordField);
    }

    @Override
    public void reset() {
        super.reset();
        confirmPasswordField.clear();
    }

    @Override
    protected final void setDefaultButtonText() {
        button.setText("Sign up".toUpperCase());
    }

    @Override
    protected final HashMap<String, String> getFormData() {
        var hashMap = super.getFormData();

        hashMap.put("confirmPassword", confirmPasswordField.getText());

        return hashMap;
    }
}
