package com.github.chamexxxx.meetingroombookingsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

public class Application extends javafx.application.Application {
    private final String[] stylesheetNames = {"variables", "app", "calendar"};

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Locale.setDefault(Locale.US);

        configureDatabase();
        configureStage(stage);

        var initialScene = createInitialScene();

        addStylesheetsToScene(initialScene, stylesheetNames);

        Router.setInitialScene(initialScene);
        Router.addScene("login", "login-view.fxml");
        Router.addScene("register", "register-view.fxml");
        Router.addScene("home", "home-view.fxml");

        stage.setScene(initialScene);
        stage.show();
    }

    private Scene createInitialScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("register-view.fxml"));
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

    private void addStylesheetsToScene(Scene scene, String[] resourceNames) {
        for (String resourceName : resourceNames) {
            scene.getStylesheets().add(getCssResource(resourceName + ".css"));
        }
    }

    private String getCssResource(String resourceName) {
        return Objects.requireNonNull(Application.class.getResource(resourceName)).toExternalForm();
    }

    public static void main(String[] args) {
        launch();
    }
}