package com.github.chamexxxx.meetingroombookingsystem;

import com.calendarfx.model.*;
import com.calendarfx.view.page.WeekPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    @FXML
    private WeekPage weekPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Meet> meets = FXCollections.observableArrayList();

        configureWeekPage();
    }

    private void configureWeekPage() {
    }

    private ArrayList<TableColumn<Meet, String>> getTableColumns() {
        var columns = getWeeklyColumns();

        var column = new TableColumn<Meet, String>("Time");

        this.configureColumn(column, 20);

        columns.add(0, column);

        return columns;
    }

    private ArrayList<TableColumn<Meet, String>> getWeeklyColumns() {
        var columns = new ArrayList<TableColumn<Meet, String>>();

        for (String day : days) {
            var column = new TableColumn<Meet, String>(day);

            this.configureColumn(column, 50);

            columns.add(column);
        }

        return columns;
    }

    private void configureTable(TableView<Meet> table, ObservableList<Meet> items) {
        table.setItems(items);
        table.setSelectionModel(null);
        table.setFocusTraversable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configureColumn(TableColumn<?,?> column, Integer size) {
        column.setReorderable(false);
        column.setSortable(false);
        column.setMaxWidth(1f * Integer.MAX_VALUE * size);
    }
}
