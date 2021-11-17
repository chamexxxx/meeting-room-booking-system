package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.github.chamexxxx.meetingroombookingsystem.control.RegisterForm;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private RegisterForm registerForm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerForm.setOnAction(this::onRegisterButtonClick);
    }

    private void onRegisterButtonClick(HashMap<String, String> formData) {
        var username = formData.get("username");
        var password = formData.get("password");
        var confirmPassword = formData.get("confirmPassword");

        if (!everyoneIsNotEmpty(username, password, confirmPassword)) {
            showRequiredFieldsError();
            return;
        }

        if (!password.equals(confirmPassword)) {
            showConfirmPasswordError();
            return;
        }
    }

    private void showRequiredFieldsError() {
        showAlertError("All fields are required");
    }

    private void showConfirmPasswordError() {
        showAlertError("Passwords are not identical");
    }

    private void showAlertError(String contentText) {
        var errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }

    private Boolean everyoneIsNotEmpty(String... strings) {
        for (String str: strings) {
            if (str.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
