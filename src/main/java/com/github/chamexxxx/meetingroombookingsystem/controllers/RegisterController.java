package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.control.RegisterForm;
import com.github.chamexxxx.meetingroombookingsystem.models.Account;
import com.github.chamexxxx.meetingroombookingsystem.utils.PasswordHashing;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.SQLException;
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
            showAlertError("All fields are required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlertError("Passwords are not identical");
            return;
        }

        var user = new Account();

        user.setUsername(username);
        user.setPassword(PasswordHashing.hash(password));

        try {
            Database.accountDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
