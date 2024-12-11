package com.nutech.hometest.supports;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    
    public static String createUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.([a-zA-Z]{2,})$");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
