package com.github.chamexxxx.meetingroombookingsystem.control;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class TopBar extends VBox {
    public TopBar() {
        getChildren().add(new AccountMenu());
        setAlignment(Pos.CENTER_RIGHT);
        setPadding(new Insets(30, 10, 30, 10));
    }
}
