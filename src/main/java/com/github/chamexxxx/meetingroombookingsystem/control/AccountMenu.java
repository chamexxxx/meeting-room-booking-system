package com.github.chamexxxx.meetingroombookingsystem.control;

import com.github.chamexxxx.meetingroombookingsystem.Router;
import com.github.chamexxxx.meetingroombookingsystem.utils.FontIconFactory;
import com.github.chamexxxx.meetingroombookingsystem.utils.UserPreferences;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;

public class AccountMenu extends AccountMenuButton {
    public AccountMenu() {
        super(UserPreferences.getAccount());

        logoutItem.setOnAction(event -> {
            UserPreferences.removeAccount();
            Router.switchScene("login");
        });
    }
}

class AccountMenuButton extends MenuButton {
    private final String username;
    protected MenuItem logoutItem = createMenuItem("Logout", FontIconFactory.createFontIcon(FontIconFactory.ICON.POWER, 15));

    public AccountMenuButton(String username) {
        this.username = username;

        getItems().add(logoutItem);
        getStyleClass().addAll("cursor-hand", "background-transparent");
        setFocusTraversable(false);
        setGraphic(createGraphic());
    }

    private HBox createGraphic() {
        var userLabel = createLabel(username);
        var userIcon = FontIconFactory.createFontIcon(FontIconFactory.ICON.USER_BOARD, 27);

        return createContainer(userIcon, userLabel);
    }

    private HBox createContainer(Node... nodes) {
        var container = new HBox(nodes);

        container.setAlignment(Pos.CENTER_RIGHT);

        return container;
    }

    private MenuItem createMenuItem(String text, FontIcon icon) {
        var menuItem = new MenuItem();
        var label = new Label(text);
        var container = new HBox(icon, label);

        menuItem.setGraphic(container);
        label.setMinWidth(110);
        label.setPadding(new Insets(0, 0, 0, 10));
        container.setAlignment(Pos.CENTER);

        return menuItem;
    }

    private Label createLabel(String text) {
        var userLabel = new Label(text);

        userLabel.setFont(new Font(15));
        HBox.setMargin(userLabel, new Insets(0, 0, 0, 5));

        return userLabel;
    }
}
