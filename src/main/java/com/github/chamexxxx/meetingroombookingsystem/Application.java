package com.github.chamexxxx.meetingroombookingsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Application extends javafx.application.Application {
    private static final String[] stylesheetNames = {"variables", "app", "calendar"};

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Locale.setDefault(Locale.US);

        configureDatabase();
        configureStage(stage);

        var initialScene = createInitialScene();

        addStylesheetsToScene(initialScene, getAllStylesheets());

        Router.setInitialScene(initialScene);
        Router.addScene("login", "login-view.fxml");
        Router.addScene("register", "register-view.fxml");
        Router.addScene("home", "home-view.fxml");

        stage.setScene(initialScene);
        stage.show();
    }

    public static ArrayList<String> getAllStylesheets() {
        var resources = new ArrayList<String>();

        for (String stylesheetName : stylesheetNames) {
            resources.add(getStylesheet(stylesheetName));
        }

        return resources;
    }

    public static String getStylesheet(String fileName) {
        return Objects.requireNonNull(Application.class.getResource(fileName + ".css")).toExternalForm();
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

    private void addStylesheetsToScene(Scene scene, ArrayList<String> stylesheets) {
        for (String stylesheet : stylesheets) {
            scene.getStylesheets().add(stylesheet);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}