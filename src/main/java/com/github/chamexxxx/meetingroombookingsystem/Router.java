package com.github.chamexxxx.meetingroombookingsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class Router {
    private static Stage stage = null;
    private static StageOptions defaultStageOptions;
    private static Scene initialScene = null;
    private final static HashMap<String, Route> routes = new HashMap<>();

    public static class StageOptions {
        private final double width;
        private final double height;
        private final boolean maximized;

        public StageOptions(double width, double height, boolean maximized) {
            this.width = width;
            this.height = height;
            this.maximized = maximized;
        }
    }

    private static class Route {
        public final String resourceName;
        public final StageOptions stageOptions;

        public Route(String resourceName, StageOptions stageOptions) {
            this.resourceName = resourceName;
            this.stageOptions = stageOptions;
        }
    }

    public static void bind(Stage stage, Scene initialScene) {
        setStage(stage);
        setInitialScene(initialScene);
    }

    public static void setStage(Stage stage) {
        Router.stage = stage;
        Router.defaultStageOptions = new StageOptions(stage.getWidth(), stage.getHeight(), stage.isMaximized());
    }

    public static void setInitialScene(Scene initialScene) {
        Router.initialScene = initialScene;
    }

    public static void addScene(String sceneName, String resourceName) {
        addScene(sceneName, resourceName, null);
    }

    public static void addScene(String sceneName, String resourceName, StageOptions stageOptions) {
        routes.put(sceneName, new Route(resourceName, stageOptions));
    }

    public static void switchScene(String sceneName) {
        try {
            var route = routes.get(sceneName);
            var root = load(route.resourceName);

            initialScene.setRoot(root);

            if (route.stageOptions != null) {
                stage.setWidth(route.stageOptions.width);
                stage.setHeight(route.stageOptions.width);
                stage.setMaximized(route.stageOptions.maximized);
            } else {
                stage.setWidth(defaultStageOptions.width);
                stage.setHeight(defaultStageOptions.height);
                stage.setMaximized(defaultStageOptions.maximized);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Parent load(String resourceName) throws IOException {
        var fxmlLoader = new FXMLLoader(Application.class.getResource(resourceName));
        return fxmlLoader.load();
    }
}
