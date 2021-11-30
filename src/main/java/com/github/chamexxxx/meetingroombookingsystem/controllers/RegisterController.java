package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.Router;
import com.github.chamexxxx.meetingroombookingsystem.control.forms.RegisterForm;
import com.github.chamexxxx.meetingroombookingsystem.control.RouterLink;
import com.github.chamexxxx.meetingroombookingsystem.dto.AccountDto;
import com.github.chamexxxx.meetingroombookingsystem.utils.PasswordHashing;
import com.github.chamexxxx.meetingroombookingsystem.utils.PasswordValidator;
import com.github.chamexxxx.meetingroombookingsystem.utils.UserSession;
import com.github.chamexxxx.meetingroombookingsystem.utils.UsernameValidator;
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
            showAlertError("All fields are required");
            return;
        }

        if (!UsernameValidator.isValid(username)) {
            showAlertError(UsernameValidator.message);
            return;
        };

        if (!PasswordValidator.isValid(password)) {
            showAlertError(PasswordValidator.message);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlertError("Passwords are not identical");
            return;
        }

        var user = new AccountDto();

        user.setUsername(username);

        try {
            Database.getAccountDao().create(user);
            UserSession.setAccount(user);
            Router.switchScene("home");
            registerForm.reset();
        } catch (SQLException e) {
            var errorCode = e.getErrorCode();

            if (errorCode == 0) {
                showAlertError("A user with the same name already exists");
            }
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
