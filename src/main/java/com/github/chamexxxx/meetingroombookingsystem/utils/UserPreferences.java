package com.github.chamexxxx.meetingroombookingsystem.utils;

import java.util.prefs.Preferences;

public class UserPreferences {
    private static final Preferences userPrefs = Preferences.userRoot().node("account");

    public static String getAccount() {
        return userPrefs.get("username", null);
    }

    public static boolean accountExists() {
        return getAccount() != null;
    }

    public static void putAccount(String username) {
        userPrefs.put("username", username);
    }

    public static void removeAccount() {
        userPrefs.remove("username");
    }
}
