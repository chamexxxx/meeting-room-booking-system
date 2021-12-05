package com.github.chamexxxx.meetingroombookingsystem.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class TopBar extends HBox {
    public TopBar() {
        setAlignment(Pos.CENTER_RIGHT);
        setPadding(new Insets(30, 0, 30, 0));
        getChildren().add(new AccountMenu());
    }
}
