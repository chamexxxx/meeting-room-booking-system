package com.github.chamexxxx.meetingroombookingsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Locale.setDefault(Locale.US);

        var scene = createScene();

        configureStage(stage);

        var initialScene = createScene();

        Router.setInitialScene(initialScene);
        Router.addScene("login", "login-view.fxml");
        Router.addScene("register", "register-view.fxml");
        Router.addScene("home", "home-view.fxml");

        configureDatabase();

        stage.setScene(initialScene);
        stage.show();
    }

    private Scene createScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home-view.fxml"));
        return new Scene(fxmlLoader.load(), 800, 600);
    }

    private void configureStage(Stage stage) {
        stage.getIcons().add(new Image("file:logo.png"));
        stage.setTitle("Meeting room booking system");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setMaximized(true);
    }

    private void configureDatabase() throws SQLException {
        Database.createTables();
    }

    public static void main(String[] args) {
        launch();
    }
}