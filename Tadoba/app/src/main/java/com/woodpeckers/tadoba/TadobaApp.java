package com.woodpeckers.tadoba;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * Created by vaishaliagarwal on 10/08/16.
 */
public class TadobaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        int storedDbVersion = ApplicationSettings.getApplicationDatabaseVersion(this);
        if (storedDbVersion != BirdDatabaseHelper.DATABASE_VERSION) {
            BirdDatabaseHelper dbHelper = new BirdDatabaseHelper(this);
            ApplicationSettings.setApplicationDatabaseVersion(this, BirdDatabaseHelper.DATABASE_VERSION);
            parseJasonAndWriteToDatabase();
        }

    }

    private void parseJasonAndWriteToDatabase() {
        Log.e("TAG_TEST", "parseJasonAndWriteToDatabase() called ");
        String json = loadAssetTextAsString(this,"Tadoba.json");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BirdDatabaseHelper dbHelper = new BirdDatabaseHelper(this);
            List<BirdFamily> birdFamiliesData = objectMapper.readValue(json, BirdFamilyCollection.class).getFamilies();

            for(BirdFamily family : birdFamiliesData) {
                ContentValues birdFamilyContentValues = BirdDatabaseHelper.getBirdFamilyContentValues(family);
                getContentResolver().insert(BirdContract.BirdFamilyEntry.CONTENT_URI, birdFamilyContentValues);
                insertAllBirdsForBirdFamily(dbHelper, family);
            }
        }
        catch(IOException e) {
            Log.e("TAG_TEST", "Error parsing the jason " + json);
        }
    }

    private void insertAllBirdsForBirdFamily(BirdDatabaseHelper dbHelper, BirdFamily family) {
        List<Bird> birds = family.getBirdList();
        for (Bird bird : birds) {
            ContentValues birdContentValues = BirdDatabaseHelper.getBirdContentValues(family.getFamilyID(), bird);
            getContentResolver().insert(BirdContract.BirdEntry.CONTENT_URI, birdContentValues);
        }

    }

    private String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ( (str = in.readLine()) != null ) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("TAG_TEST", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("TAG_TEST", "Error closing asset " + name);
                }
            }
        }

        return null;
    }

}
