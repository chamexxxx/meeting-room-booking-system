package com.github.chamexxxx.meetingroombookingsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;

public class Router {
    private static Scene initialScene = null;
    private final static HashMap<String, Parent> resourceMap = new HashMap<>();

    public static void setInitialScene(Scene initialScene) {
        Router.initialScene = initialScene;
    }

    public static void addScene(String sceneName, String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(resourceName));
        resourceMap.put(sceneName, fxmlLoader.load());
    }

    public static void switchScene(String sceneName) {
        var root = resourceMap.get(sceneName);
        initialScene.setRoot(root);
    }
}
