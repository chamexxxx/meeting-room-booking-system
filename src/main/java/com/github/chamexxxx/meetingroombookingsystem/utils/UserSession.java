package com.github.chamexxxx.meetingroombookingsystem.utils;

import com.github.chamexxxx.meetingroombookingsystem.Database;
import com.github.chamexxxx.meetingroombookingsystem.dto.AccountDto;

import java.sql.SQLException;

public class UserSession {
    private static AccountDto account = null;

    public static AccountDto getAccount() {
        try {
            if (account == null) {
                var usernameFromStore = UserPreferences.getUsername();

                if (usernameFromStore != null) {
                    account = Database.getAccountDao().queryForUsername(usernameFromStore);
                    UserPreferences.putUsername(account.getUsername());
                }
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

    public static void setAccount(AccountDto account) {
        UserPreferences.putUsername(account.getUsername());
        UserSession.account = account;
    }

    public static void removeAccount() {
        UserPreferences.removeUsername();
        UserSession.account = null;
    }
}
