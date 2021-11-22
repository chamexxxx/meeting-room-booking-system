package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.utils.FontIconFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.CustomTextField;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ToDoList<M extends ToDoList.Model> extends VBox {
    public interface Model {
        String getName();
        void setName(String name);
    }

    private final Supplier<M> supplier;
    private final VBox taskFields = new VBox(10);
    private final ScrollPane scrollPane;

    public ToDoList(Supplier<M> supplier, ArrayList<M> tasks) {
        this.supplier = supplier;

        initializeTaskFields(tasks);

        if (taskFields.getChildren().size() == 0) {
            taskFields.setVisible(false);
        }

        var buttonBox = createButtonBox();

        scrollPane = createScrollPane(taskFields);

        getChildren().addAll(scrollPane, buttonBox);
    }

    public ArrayList<M> getModels() {
        var tasks = new ArrayList<M>();

        taskFields.getChildren().forEach(taskField -> {
            if (taskField instanceof CustomTextField) {
                var text = ((CustomTextField) taskField).getText();

                var model = createModel();

                model.setName(text);

                tasks.add(model);
            }
        });

        return tasks;
    }

    protected String getAddButtonText() {
        return "Add task".toUpperCase();
    }

    protected String getFieldPromptText() {
        return "Thing";
    }

    private M createModel() {
        return supplier.get();
    }

    private void initializeTaskFields(ArrayList<M> tasks) {
        tasks.forEach(task -> {
            createTaskField(task.getName());
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
            createTaskField(null);
            taskFields.setVisible(true);
            scrollPane.setVvalue(1);
        });

        return button;
    }

    private void createTaskField(@Nullable String name) {
        var customTextField = new CustomTextField();

        taskFields.getChildren().add(customTextField);

        customTextField.setText(name);
        customTextField.setPromptText(getFieldPromptText());

//        var fontIcon = new FontIcon("fltral-delete-24");
//        fontIcon.setIconSize(20);

        var fontIcon = FontIconFactory.createFontIcon(FontIconFactory.ICON.DELETE, 20);

        customTextField.setRight(fontIcon);

        customTextField.getRight().setOnMouseClicked(event -> {
            taskFields.getChildren().remove(customTextField);
        });
    }
}
