package com.github.chamexxxx.meetingroombookingsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    @FXML
    private TableView<Meet> table;

    private final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setSelectionModel(null);
        table.setFocusTraversable(false);

        ObservableList<Meet> meets = FXCollections.observableArrayList();

        table.setItems(meets);

        var columns = getTableColumns();

        table.getColumns().addAll(columns);
    }

    private ArrayList<TableColumn<Meet, String>> getTableColumns() {
        var columns = getWeeklyColumns();

        var column = new TableColumn<Meet, String>("Time");

        // set cell value factory here...

        columns.add(0, column);

        return columns;
    }

    private ArrayList<TableColumn<Meet, String>> getWeeklyColumns() {
        var columns = new ArrayList<TableColumn<Meet, String>>();

        for (String day : days) {
            var column = new TableColumn<Meet, String>(day);

            // set cell value factory here...

            column.setReorderable(false);
            column.setSortable(false);

            columns.add(column);
        }

        return columns;
    }
}
