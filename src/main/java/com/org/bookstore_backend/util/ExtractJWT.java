package com.org.bookstore_backend.util;


public class ExtractJWT {
    public static boolean isAdmin(String token) {
        String role = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        return "admin".equals(role);
    }

    private static String payloadJWTExtraction(String token, String s) {
        return token;
    }
}
