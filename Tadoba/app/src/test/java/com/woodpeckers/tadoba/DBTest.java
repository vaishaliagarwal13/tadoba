package com.woodpeckers.tadoba;

import android.content.ContentValues;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * Created by vaishaliagarwal on 06/08/16.
 */
public class DBTest extends AndroidTestCase {
    private BirdDatabaseHelper db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new BirdDatabaseHelper(context);
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    //@Test
    public void testAddEntry(){
        ContentValues contentValues = new ContentValues();


    }
}
