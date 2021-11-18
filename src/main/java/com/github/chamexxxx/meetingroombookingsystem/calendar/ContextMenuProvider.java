package com.github.chamexxxx.meetingroombookingsystem.calendar;

import com.calendarfx.view.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class ContextMenuProvider extends com.calendarfx.view.ContextMenuProvider {
    @Override
    protected ContextMenu getWeekDayViewMenu(DateControl.ContextMenuParameter param) {
        var menu = super.getWeekDayViewMenu(param);

        configureHoursMenuItem(menu.getItems().get(3));

        // remove menu items: Early / Late Hours and Grid
        menu.getItems().remove(1, 3);
        // remove menu item: Show Days
        menu.getItems().remove(2);

        return menu;
    }

    private void configureHoursMenuItem(MenuItem hoursItem) {
        hoursItem.setText("Scaling");
        removeHoursMenuItems((Menu) hoursItem);
    }

    private void removeHoursMenuItems(Menu hoursMenu) {
        hoursMenu.getItems().remove(1, 9);
    }
}
