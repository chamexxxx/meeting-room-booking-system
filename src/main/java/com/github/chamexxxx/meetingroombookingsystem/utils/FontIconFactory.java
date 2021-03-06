package com.github.chamexxxx.meetingroombookingsystem.utils;

import org.kordamp.ikonli.javafx.FontIcon;

public class FontIconFactory {
    public enum ICON {
        DELETE,
        USER,
        USER_BOARD,
        LOCK,
        POWER,
        EDIT,
        ADD,
        ZOOM
    }

    public static FontIcon createFontIcon(ICON icon, int size) {
        var iconCode = getIconCode(icon);
        var fontIcon = new FontIcon(iconCode);

        fontIcon.setIconSize(size);

        return fontIcon;
    }

    private static String getIconCode(ICON icon) {
        String iconCode = null;

        switch (icon) {
            case DELETE:
                iconCode = "fltral-delete-24";
                break;
            case USER:
                iconCode = "fltfmz-person-16";
                break;
            case USER_BOARD:
                iconCode = "fltfmz-person-board-16";
                break;
            case LOCK:
                iconCode = "fltfal-lock-24";
                break;
            case POWER:
                iconCode = "fltfmz-power-24";
                break;
            case EDIT:
                iconCode = "fltfal-edit-16";
                break;
            case ADD:
                iconCode = "fltfal-add-24";
                break;
            case ZOOM:
                iconCode = "fltrmz-zoom-in-24";
                break;
        }

        return iconCode;
    }
}
