package com.woodpeckers.tadoba;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by vaishaliagarwal on 06/08/16.
 */
public class BirdProvider extends ContentProvider {


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BirdDatabaseHelper birdDatabaseHelper;

    private static final int BIRD = 100;
    private static final int BIRDS_WITH_FAMILY_ID = 101;
    private static final int BIRD_FAMILY = 200;

    private static final SQLiteQueryBuilder birdsByFamilyIdQueryBuilder;

    static{
        birdsByFamilyIdQueryBuilder = new SQLiteQueryBuilder();

        birdsByFamilyIdQueryBuilder.setTables(
                        BirdContract.BirdEntry.TABLE_NAME + " INNER JOIN " +
                        BirdContract.BirdFamilyEntry.TABLE_NAME +
                        " ON " + BirdContract.BirdEntry.TABLE_NAME +
                        "." + BirdContract.BirdEntry.COLUMN_FAMILY_KEY +
                        " = " + BirdContract.BirdFamilyEntry.TABLE_NAME +
                        "." + BirdContract.BirdFamilyEntry.COLUMN_ID);
    }

    private static final String sBirdFamilyIdSelection = BirdContract.BirdEntry.TABLE_NAME+
                    "." + BirdContract.BirdEntry.COLUMN_FAMILY_KEY + " = ? ";

    private static final String sBirdIdSelection = BirdContract.BirdEntry.TABLE_NAME +
            "." + BirdContract.BirdEntry.COLUMN_ID + " = ? ";

    private Cursor getBirdsByFamilyId(Uri uri, String[] projection, String sortOrder) {
        String birdFamilyId = BirdContract.BirdEntry.getFamilyIdFromUri(uri);

        String[] selectionArgs = new String[]{birdFamilyId};
        String selection = sBirdFamilyIdSelection;

        return birdsByFamilyIdQueryBuilder.query(birdDatabaseHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getBirdById(Uri uri, String[] projection, String sortOrder) {
        String birdId = BirdContract.BirdEntry.getIdFromUri(uri);

        String[] selectionArgs = new String[]{birdId};
        String selection = sBirdIdSelection;

        return birdsByFamilyIdQueryBuilder.query(birdDatabaseHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    /*
     */
    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BirdContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, BirdContract.PATH_BIRD_FAMILY, BIRD_FAMILY);
        matcher.addURI(authority, BirdContract.PATH_BIRD, BIRD);
        matcher.addURI(authority, BirdContract.PATH_BIRD + "/*", BIRDS_WITH_FAMILY_ID);
        matcher.addURI(authority, BirdContract.PATH_BIRD + "/*/#", BIRD);
        return matcher;
    }



    @Override
    public boolean onCreate() {
        birdDatabaseHelper = new BirdDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case BIRD:
            {
                cursor = getBirdById(uri, projection, sortOrder);
                break;
            }
            case BIRDS_WITH_FAMILY_ID: {
                cursor = getBirdsByFamilyId(uri, projection, sortOrder);
                break;
            }
            case BIRD_FAMILY: {
                cursor = birdDatabaseHelper.getReadableDatabase().query(
                        BirdContract.BirdFamilyEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
      // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case BIRD:
                return BirdContract.BirdEntry.CONTENT_ITEM_TYPE;
            case BIRDS_WITH_FAMILY_ID:
                return BirdContract.BirdEntry.CONTENT_TYPE;
            case BIRD_FAMILY:
                return BirdContract.BirdFamilyEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = birdDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case BIRD: {
                long _id = db.insert(BirdContract.BirdEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BirdContract.BirdEntry.buildBirdUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case BIRD_FAMILY: {
                long _id = db.insert(BirdContract.BirdFamilyEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BirdContract.BirdFamilyEntry.buildBirdFamilyUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = birdDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case BIRD:
                rowsDeleted = db.delete(
                        BirdContract.BirdEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case BIRD_FAMILY:
                rowsDeleted = db.delete(
                        BirdContract.BirdFamilyEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = birdDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case BIRD:
                rowsUpdated = db.update(BirdContract.BirdEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case BIRD_FAMILY:
                rowsUpdated = db.update(BirdContract.BirdFamilyEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = birdDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BIRD:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(BirdContract.BirdEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }


    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        birdDatabaseHelper.close();
        super.shutdown();
    }
}
