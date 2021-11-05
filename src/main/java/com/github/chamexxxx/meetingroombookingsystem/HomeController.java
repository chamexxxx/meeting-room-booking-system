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
        ObservableList<Meet> meets = FXCollections.observableArrayList();

        this.configureTable(table, meets);

        var columns = getTableColumns();

        table.getColumns().addAll(columns);
    }

    private ArrayList<TableColumn<Meet, String>> getTableColumns() {
        var columns = getWeeklyColumns();

        var column = new TableColumn<Meet, String>("Time");

        this.configureColumn(column);

        columns.add(0, column);

        return columns;
    }

    private ArrayList<TableColumn<Meet, String>> getWeeklyColumns() {
        var columns = new ArrayList<TableColumn<Meet, String>>();

        for (String day : days) {
            var column = new TableColumn<Meet, String>(day);

            this.configureColumn(column);

            columns.add(column);
        }

        return columns;
    }

    private void configureTable(TableView<Meet> table, ObservableList<Meet> items) {
        table.setItems(items);
        table.setSelectionModel(null);
        table.setFocusTraversable(false);
    }

    private void configureColumn(TableColumn<?,?> column) {
        column.setReorderable(false);
        column.setSortable(false);
    }
}
