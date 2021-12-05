package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.Router;
import com.github.chamexxxx.meetingroombookingsystem.controls.forms.RegisterForm;
import com.github.chamexxxx.meetingroombookingsystem.controls.RouterLink;
import com.github.chamexxxx.meetingroombookingsystem.dto.AccountDto;
import com.github.chamexxxx.meetingroombookingsystem.utils.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private RegisterForm registerForm;

    @FXML
    private RouterLink loginLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerForm.setOnAction(this::onRegisterButtonClick);
        loginLink.setOnAfterAction(event -> registerForm.reset());
    }

    private void onRegisterButtonClick(HashMap<String, String> formData) {
        var username = formData.get("username");
        var password = formData.get("password");
        var confirmPassword = formData.get("confirmPassword");

        if (!everyoneIsNotEmpty(username, password, confirmPassword)) {
            Dialogs.error("All fields are required");
            return;
        }

        if (!UsernameValidator.isValid(username)) {
            Dialogs.error(UsernameValidator.message);
            return;
        };

        if (!PasswordValidator.isValid(password)) {
            Dialogs.error(PasswordValidator.message);
            return;
        }

        if (!password.equals(confirmPassword)) {
            Dialogs.error("Passwords are not identical");
            return;
        }

        var user = new AccountDto();

        user.setUsername(username);
        user.setPassword(password);

        try {
            Database.getAccountDao().create(user);
            UserSession.setAccount(user);
            Router.switchScene("home");
            registerForm.reset();
        } catch (SQLException e) {
            var errorCode = e.getErrorCode();

            if (errorCode == 0) {
                Dialogs.error("A user with the same name already exists");
            }
        }
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
