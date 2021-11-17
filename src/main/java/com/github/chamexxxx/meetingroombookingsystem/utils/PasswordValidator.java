package com.github.chamexxxx.meetingroombookingsystem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    public static final String message =
            "Password does not meet requirements:\n" +
                    "    a digit must occur at least once\n" +
                    "    a lower case letter must occur at least once\n" +
                    "    an upper case letter must occur at least once\n" +
                    "    a special character must occur at least once\n" +
                    "    no whitespace allowed in the entire string\n" +
                    "    anything, at least eight places though";

    public static boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}