package com.github.chamexxxx.meetingroombookingsystem;

import com.github.chamexxxx.meetingroombookingsystem.utils.UserPreferences;
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

    public static String getResourceInExternalForm(String resourceName) {
        return Objects.requireNonNull(Application.class.getResource(resourceName)).toExternalForm();
    }

    public static String getStylesheet(String fileName) {
        return getResourceInExternalForm(fileName + ".css");
    }

    private Scene createInitialScene() throws IOException {
        var resourceName = UserPreferences.accountExists() ? "home-view.fxml" : "register-view.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(resourceName));

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