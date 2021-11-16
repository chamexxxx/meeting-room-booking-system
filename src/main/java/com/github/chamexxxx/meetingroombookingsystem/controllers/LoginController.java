package com.github.chamexxxx.meetingroombookingsystem.controllers;

import com.github.chamexxxx.meetingroombookingsystem.control.LoginForm;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private LoginForm loginForm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginForm.setOnAction(this::onLoginButtonClick);
    }

    @FXML
    protected void onLoginButtonClick(HashMap<String, String> formData) {
        var username = formData.get("username");
        var password = formData.get("password");

        if (username.equals("username") && password.equals("password")) {
            // redirect to home scene
        } else {
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