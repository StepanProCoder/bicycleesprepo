package com.staple.probkaesp;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveLoadResult {
    public static void saveResult(String mainTag, String subTag, String result, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mainTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(subTag, result);
        editor.apply();
    }

    public static String loadResult(String mainTag, String subTag, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mainTag, Context.MODE_PRIVATE);
        return sharedPreferences.getString(subTag, "");
    }

}
