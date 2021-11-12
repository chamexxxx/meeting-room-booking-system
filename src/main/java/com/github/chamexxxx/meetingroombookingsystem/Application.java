package com.github.chamexxxx.meetingroombookingsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(Locale.US);
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        configureStage(stage);

        stage.setScene(scene);
        stage.show();
    }

    private void configureStage(Stage stage) {
        stage.getIcons().add(new Image("file:logo.png"));
        stage.setTitle("Meeting room booking system");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setMaximized(true);
    }

    public static void main(String[] args) {
        launch();
    }
}