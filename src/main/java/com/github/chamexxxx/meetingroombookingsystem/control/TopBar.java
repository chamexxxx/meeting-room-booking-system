package com.github.chamexxxx.meetingroombookingsystem.control;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class TopBar extends HBox {
    public TopBar() {
        var accountMenu = new AccountMenu();

        accountMenu.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-border-radius: 10px; -fx-background-radius: 10px;");
        accountMenu.setPadding(new Insets(10));

        setAlignment(Pos.CENTER_RIGHT);
        setPadding(new Insets(30, 0, 30, 0));
        getChildren().add(accountMenu);
    }
}
