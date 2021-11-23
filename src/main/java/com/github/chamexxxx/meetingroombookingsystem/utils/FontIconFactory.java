package com.github.chamexxxx.meetingroombookingsystem.utils;

import org.kordamp.ikonli.javafx.FontIcon;

public class FontIconFactory {
    public enum ICON {
        DELETE,
        USER
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
        }

        return iconCode;
    }
}
