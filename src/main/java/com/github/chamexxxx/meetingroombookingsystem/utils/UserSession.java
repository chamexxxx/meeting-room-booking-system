package com.github.chamexxxx.meetingroombookingsystem.utils;

import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.models.Account;

import java.sql.SQLException;
import java.util.HashMap;

public class UserSession {
    private static final HashMap<String, String> userData = new HashMap<>();
    private static Account account = null;

    public static Account getAccount() {
        try {
            var username = userData.get("username");

            if (username != null) {
                account = Database.getAccountDao().queryForUsername(username);
            }

            var usernameFromStore = UserPreferences.getUsername();

            if (usernameFromStore != null) {
                userData.put("username", usernameFromStore);

                account = Database.getAccountDao().queryForUsername(usernameFromStore);
            }

            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean accountExists() {
        return getAccount() != null;
    }

    public static void putUsername(String username) {
        userData.put("username", username);
        UserPreferences.putUsername(username);
    }

    public static void removeUsername() {
        userData.remove("username");
        UserPreferences.removeUsername();
    }
}
