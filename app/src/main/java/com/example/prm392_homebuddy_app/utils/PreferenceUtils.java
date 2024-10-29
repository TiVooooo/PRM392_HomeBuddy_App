package com.example.prm392_homebuddy_app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    private static final String PREFERENCES_FILE = "homebuddy_app_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_EXPIRATION = "expiration";
    private static final String KEY_ROLE = "user_role";
    private static final String KEY_USERID = "user_id";

    public static void setLoggedIn(Context context, boolean isLoggedIn) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static void saveToken(Context context, String token, long expiration, String role, String userId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_TOKEN, token);
        editor.putLong(KEY_EXPIRATION, expiration);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_USERID, userId);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return prefs.getString(KEY_TOKEN, null);
    }

    public static long getExpiration(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return prefs.getLong(KEY_EXPIRATION, 0);  // Sử dụng getLong để lấy expiration
    }

    public static boolean isTokenExpired(Context context) {
        long expirationTime = getExpiration(context);
        // Kiểm tra xem thời gian hiện tại có vượt qua thời gian hết hạn không
        return System.currentTimeMillis() > expirationTime;
    }
    public static String getUserRole(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return prefs.getString(KEY_ROLE, null);
    }
    public static String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USERID, null);
    }
}
