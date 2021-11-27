package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.view.*;
import com.github.chamexxxx.meetingroombookingsystem.utils.FontIconFactory;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Custom implementation of {@link com.calendarfx.view.ContextMenuProvider}
 * <ul>
 * <li>removes unnecessary menu items: Early / Late Hours, Grid, Show Days</li>
 * <li>renames the Show Hours menu item</li>
 * <li>removes all menu items from the Show Hours menu except the slider</li>
 * </ul>
 */
public class ContextMenuProvider extends com.calendarfx.view.ContextMenuProvider {
    @Override
    protected ContextMenu getWeekDayViewMenu(DateControl.ContextMenuParameter param) {
        var menu = super.getWeekDayViewMenu(param);

        configureHoursMenuItem(menu.getItems().get(3));
        removeUnnecessaryMenuItems(menu);
        configureMenuItems(menu);

        return menu;
    }

    private void configureHoursMenuItem(MenuItem hoursItem) {
        hoursItem.setText("Scaling");
        removeHoursMenuItems((Menu) hoursItem);
    }

    private void removeUnnecessaryMenuItems(ContextMenu menu) {
        // remove menu items: Early / Late Hours and Grid
        menu.getItems().remove(1, 3);
        // remove menu item: Show Days
        menu.getItems().remove(2);
    }

    private void configureMenuItems(ContextMenu menu) {
        menu.getItems().get(0).setGraphic(FontIconFactory.createFontIcon(FontIconFactory.ICON.ADD, 15));
        menu.getItems().get(1).setGraphic(FontIconFactory.createFontIcon(FontIconFactory.ICON.ZOOM, 15));
    }

    private void removeHoursMenuItems(Menu hoursMenu) {
        hoursMenu.getItems().remove(1, 9);
    }
}
