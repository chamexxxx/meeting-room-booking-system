package com.github.chamexxxx.meetingroombookingsystem.utils;

import java.util.HashMap;

public class UserSession {
    private static final HashMap<String, String> userData = new HashMap<>();

    public static String getAccount() {
        var account = userData.get("username");

        if (account != null) {
            return account;
        }

        var accountFromStore = UserPreferences.getAccount();

        if (accountFromStore != null) {
            userData.put("username", accountFromStore);
            return accountFromStore;
        }

        return null;
    }

    public static boolean accountExists() {
        return getAccount() != null;
    }

    public static void putAccount(String username) {
        userData.put("username", username);
        UserPreferences.putAccount(username);
    }

    public static void removeAccount() {
        userData.remove("username");
        UserPreferences.removeAccount();
    }
}
