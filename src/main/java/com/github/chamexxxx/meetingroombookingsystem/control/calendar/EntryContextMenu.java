package com.github.chamexxxx.meetingroombookingsystem.control.calendar;

import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.github.chamexxxx.meetingroombookingsystem.utils.FontIconFactory;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.util.Callback;

/**
 * An entry context menu implementation
 * that only allows editing and deleting entries,
 * and adds icons to menu items.
 *
 * <h2>Code Example</h2>The code below shows setting callback for week page.
 *
 * <pre>
 * weekPage.setEntryContextMenuCallback(EntryContextMenu::new);
 * </pre>
 */
public class EntryContextMenu extends ContextMenu {

    public EntryContextMenu(DateControl.EntryContextMenuParameter param) {
        var entryView = param.getEntryView();
        var control = param.getDateControl();

        var entry = entryView.getEntry();

        /*
         * Show dialog for edit entry.
         */
        var editItem = createEditMenuItem();

        editItem.setOnAction(evt -> {
            Callback<DateControl.EntryDetailsParameter, Boolean> detailsCallback = control.getEntryDetailsCallback();
            if (detailsCallback != null) {
                var ctxEvent = param.getContextMenuEvent();
                var entryDetailsParam = new DateControl.EntryDetailsParameter(ctxEvent, control, entryView.getEntry(), control, ctxEvent.getScreenX(), ctxEvent.getScreenY());
                detailsCallback.call(entryDetailsParam);
            }
        });

        getItems().add(editItem);

        /*
         * Delete calendar entry.
         */
        var deleteItem = createDeleteMenuItem();
        
        deleteItem.setOnAction(evt -> {
            var calendar = entry.getCalendar();

            if (!calendar.isReadOnly()) {
                var selections = control.getSelections();
                removeEntryFromCalendar(entry);
                selections.forEach(this::removeEntryFromCalendar);
            }
        });

        getItems().add(deleteItem);
    }

    private MenuItem createEditMenuItem() {
        var fontIcon = FontIconFactory.createFontIcon(FontIconFactory.ICON.EDIT, 15);

        return new MenuItem("Edit", fontIcon);
    }

    private MenuItem createDeleteMenuItem() {
        var fontIcon = FontIconFactory.createFontIcon(FontIconFactory.ICON.DELETE, 15);

        return new MenuItem("Delete", fontIcon);
    }

    private void removeEntryFromCalendar(Entry<?> entry) {
        if (entry.isRecurrence()) {
            entry.getRecurrenceSourceEntry().removeFromCalendar();
        } else {
            entry.removeFromCalendar();
        }
    }
}
