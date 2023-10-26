package com.ufund.api.ufundapi;

public class AuthUtils {

    public static boolean isAdmin(String username){
        return username.equals("admin");
    }

    public static boolean isLoggedIn(String token){
        boolean result = token.equals("");
        return !result;
    }
}
