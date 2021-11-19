package com.github.chamexxxx.meetingroombookingsystem.control;

import javafx.scene.control.PasswordField;

import java.util.HashMap;

public class RegisterForm extends LoginForm {
    public final PasswordField confirmPassword = new PasswordField();

    public RegisterForm() {
        super();
        configureControl(confirmPassword);
        confirmPassword.setPromptText("Confirm password");
        getChildren().add(2, confirmPassword);
    }

    @Override
    protected final void setDefaultButtonText() {
        button.setText("Sign up".toUpperCase());
    }

    @Override
    protected final HashMap<String, String> getFormData() {
        var hashMap = super.getFormData();

        hashMap.put("confirmPassword", confirmPassword.getText());

        return hashMap;
    }
}
