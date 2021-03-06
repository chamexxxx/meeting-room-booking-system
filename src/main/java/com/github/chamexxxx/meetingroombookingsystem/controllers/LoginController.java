package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.Router;
import com.github.chamexxxx.meetingroombookingsystem.controls.forms.LoginForm;
import com.github.chamexxxx.meetingroombookingsystem.controls.RouterLink;
import com.github.chamexxxx.meetingroombookingsystem.utils.Dialogs;
import com.github.chamexxxx.meetingroombookingsystem.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private LoginForm loginForm;

    @FXML
    private RouterLink registrationLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginForm.setOnAction(this::onLoginButtonClick);
        registrationLink.setOnAfterAction(event -> loginForm.reset());
    }

    @FXML
    protected void onLoginButtonClick(HashMap<String, String> formData) {
        var username = formData.get("username");
        var password = formData.get("password");

        try {
            var accountIsVerified = Database.getAccountDao().verify(username, password);

            if (!accountIsVerified) {
                Dialogs.error("Wrong login or password");
                return;
            }

            var account = Database.getAccountDao().queryForUsername(username);

            UserSession.setAccount(account);
            Router.switchScene("home");
            loginForm.reset();
        } catch (SQLException e) {
            e.printStackTrace();
            Dialogs.error("Wrong login or password");
        }
    }
}