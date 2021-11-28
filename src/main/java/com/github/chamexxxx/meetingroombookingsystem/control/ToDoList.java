package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.utils.FontIconFactory;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.CustomTextField;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Implementation of a to-do list, including creating, editing and deleting tasks
 * @param <M> the type of the model
 */
public class ToDoList<M extends ToDoList.Model> extends VBox {
    public interface Model {
        String getName();
        void setName(String name);
    }

    private final Supplier<M> supplier;
    private final VBox fields = new VBox(10);
    private final ObservableList<M> models = FXCollections.observableArrayList();
    private final ScrollPane scrollPane;

    public ToDoList(Supplier<M> supplier, ArrayList<M> initialTasks) {
        this.supplier = supplier;

        initializeModelsListener();

        models.addAll(initialTasks);

        if (fields.getChildren().size() == 0) {
            fields.setVisible(false);
        }

        var buttonBox = createButtonBox();

        scrollPane = createScrollPane(fields);

        getChildren().addAll(scrollPane, buttonBox);
    }

    public ObservableList<M> getModels() {
        return models;
    }

    protected String getAddButtonText() {
        return "Add task".toUpperCase();
    }

    protected String getFieldPromptText() {
        return "Thing";
    }

    private void initializeModelsListener() {
        models.addListener((ListChangeListener<M>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (M addITem : c.getAddedSubList()) {
                        var customTextField = new CustomTextField();

                        customTextField.setText(addITem.getName());
                        customTextField.setPromptText(getFieldPromptText());
                        customTextField.getStyleClass().add("field");

                        fields.getChildren().add(customTextField);

                        var fontIcon = FontIconFactory.createFontIcon(FontIconFactory.ICON.DELETE, 20);

                        fontIcon.getStyleClass().add("cursor-hand");

                        customTextField.setRight(fontIcon);

                        customTextField.getRight().setOnMouseClicked(event -> {
                            fields.getChildren().remove(customTextField);
                            models.remove(addITem);
                        });

                        customTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                            addITem.setName(newValue);
                        });
                    }
                }
            }
        });
    }

    private HBox createButtonBox() {
        var addButton = createAddButton();
        var buttonBox = new HBox(addButton);

        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        return buttonBox;
    }

    private ScrollPane createScrollPane(Pane pane) {
        var scrollPane = new ScrollPane(pane);

        scrollPane.setMinHeight(150);
        scrollPane.setMaxHeight(250);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));
        scrollPane.setFocusTraversable(false);

        return scrollPane;
    }

    private Button createAddButton() {
        var button = new Button(getAddButtonText());

        button.getStyleClass().add("action-button");
        button.setOnAction(event -> {
            var model = supplier.get();

            model.setName(null);
            models.add(model);

            fields.setVisible(true);
            scrollPane.setVvalue(1);
        });

        return button;
    }
}
