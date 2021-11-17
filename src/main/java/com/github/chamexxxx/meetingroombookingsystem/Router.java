package com.github.chamexxxx.meetingroombookingsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class Router {
    private static Stage stage;
    private final static HashMap<String, Scene> sceneMap = new HashMap<>();

    public static void setStage(Stage stage) {
        Router.stage = stage;
    }

    public static void addScene(String sceneName, String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(resourceName));
        sceneMap.put(sceneName, new Scene(fxmlLoader.load(), 800, 600));
    }

    public static void switchScene(String sceneName) {
        stage.setScene(sceneMap.get(sceneName));
    }
}
