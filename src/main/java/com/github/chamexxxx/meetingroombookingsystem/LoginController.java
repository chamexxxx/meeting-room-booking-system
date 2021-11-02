package com.github.chamexxxx.meetingroombookingsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    protected void onLoginButtonClick() throws Exception {
        if (username.getText().equals("username") && password.getText().equals("password")) {
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