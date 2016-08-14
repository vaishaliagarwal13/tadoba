package com.woodpeckers.tadoba;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vaishaliagarwal on 06/08/16.
 */
public class BirdContract {

    public static final String CONTENT_AUTHORITY = "com.woodpeckers.tadoba";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BIRD_FAMILY = "bird_family";

    public static final String PATH_BIRD = "bird";


    public static class BirdFamilyEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BIRD_FAMILY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BIRD_FAMILY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BIRD_FAMILY;


        public static final String TABLE_NAME = "bird_family";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LATIN_NAME = "family_latin_name";
        public static final String COLUMN_COMMON_NAME = "family_common_name";
        public static final String COLUMN_FAMILY_PIC = "family_pic";

        public static Uri buildBirdFamilyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static class BirdEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BIRD).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BIRD;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BIRD;

        public static final String TABLE_NAME = "bird";
        public static final String COLUMN_ID = "bird_id";
        public static final String COLUMN_FAMILY_KEY = "bird_family_id";
        public static final String COLUMN_LATIN_NAME = "bird_latin_name";
        public static final String COLUMN_COMMON_NAME = "bird_common_name";
        public static final String COLUMN_BIRD_IMAGES = "bird_images";
        public static final String COLUMN_IS_VIEWED = "bird_is_viewed";
        public static final String COLUMN_VIEWED_DATE_TIME = "bird_viewed_datetime";

        public static Uri buildBirdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildBirdUriForFamily(String familyId) {
            return CONTENT_URI.buildUpon().appendPath(familyId).build();
        }

        public static String getFamilyIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}
