package com.github.chamexxxx.meetingroombookingsystem;

import com.github.chamexxxx.meetingroombookingsystem.models.Account;
import com.github.chamexxxx.meetingroombookingsystem.models.Meet;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
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
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        configureStage(stage);

        configureDatabase();

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

    private void configureDatabase() throws SQLException {
        Database.createTables();
    }

    public static void main(String[] args) {
        launch();
    }
}