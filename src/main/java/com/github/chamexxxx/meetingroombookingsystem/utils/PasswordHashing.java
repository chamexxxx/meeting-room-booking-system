package com.github.chamexxxx.meetingroombookingsystem.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHashing {
    public static String hash(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static Boolean verify(String password, String hash) {
        return BCrypt.verifyer().verify(password.toCharArray(), hash).verified;
    }
}
