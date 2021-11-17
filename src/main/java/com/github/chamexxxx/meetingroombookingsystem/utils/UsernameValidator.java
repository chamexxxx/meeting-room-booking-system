package com.github.chamexxxx.meetingroombookingsystem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator {
    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{3,}$");
    public static final String message =
            "The username can only contain the letters a-z and A-Z, the numbers 0-9 and _-, and the string must be at least three characters long.";

    public static boolean isValid(final String username) {
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}