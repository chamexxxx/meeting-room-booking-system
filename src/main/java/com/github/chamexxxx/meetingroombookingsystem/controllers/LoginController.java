package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.Router;
import com.github.chamexxxx.meetingroombookingsystem.control.LoginForm;
import com.github.chamexxxx.meetingroombookingsystem.control.RouterLink;
import com.github.chamexxxx.meetingroombookingsystem.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

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
                showLoginError();
                return;
            }

            UserSession.putUsername(username);
            Router.switchScene("home");
            loginForm.reset();
        } catch (SQLException e) {
            e.printStackTrace();
            showLoginError();
        }
    }

    private void showLoginError() {
        var errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Wrong login or password");
        errorAlert.showAndWait();
    }
}