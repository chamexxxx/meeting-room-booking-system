package com.github.chamexxxx.meetingroombookingsystem;

import com.github.chamexxxx.meetingroombookingsystem.utils.UserSession;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class Application extends javafx.application.Application {
    private static final String[] stylesheetNames = {"variables", "jfx", "calendarfx", "app"};
    private static final Image logoImage = new Image(Objects.requireNonNull(Application.class.getResource("logo.png")).toString());

    @Override
    public void start(Stage stage) {
        configureStage(stage);

        Router.setStage(stage);
        Router.setInitialSceneConfigurator(scene -> {
            addStylesheetsToScene(scene, getAllStylesheets());
        });
        Router.addScene("login", "login-view.fxml", new Router.StageOptions(800, 600, false));
        Router.addScene("register", "register-view.fxml", new Router.StageOptions(800, 600, false));
        Router.addScene("home", "home-view.fxml");

        Router.switchScene(UserSession.accountExists() ? "home" : "login");

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

    public static Image getLogoImage() {
        return logoImage;
    }

    private void configureStage(Stage stage) {
        stage.getIcons().add(logoImage);
        stage.setTitle("Meeting room booking system");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setMaximized(true);
    }

    private static void configureDatabase() throws SQLException {
        Database.createTables();
    }

    private void addStylesheetsToScene(Scene scene, ArrayList<String> stylesheets) {
        for (String stylesheet : stylesheets) {
            scene.getStylesheets().add(stylesheet);
        }
    }

    public static void main(String[] args) throws SQLException {
        Locale.setDefault(Locale.US);
        configureDatabase();

        if (Arrays.asList(args).contains("--generate-fake-data")) {
            (new FakeDataGenerator()).generate();
        }

        launch();
    }
}