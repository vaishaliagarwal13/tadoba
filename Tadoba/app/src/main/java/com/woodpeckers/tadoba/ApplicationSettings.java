package com.woodpeckers.tadoba;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vaishaliagarwal on 10/08/16.
 */
public class ApplicationSettings {
    private static final String DATABASE_VERSION = "db_version";

    public static int getApplicationDatabaseVersion(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("com.woodpeckers.tadoba.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        return sharedPref.getInt(DATABASE_VERSION, -1);
    }

    public static void setApplicationDatabaseVersion(Context context, int dbVersion) {
        SharedPreferences sharedPref = context.getSharedPreferences("com.woodpeckers.tadoba.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(DATABASE_VERSION, dbVersion);
        editor.commit();
    }
}
