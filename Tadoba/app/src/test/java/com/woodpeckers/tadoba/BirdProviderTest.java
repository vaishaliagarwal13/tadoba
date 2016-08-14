package com.woodpeckers.tadoba;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.woodpeckers.tadoba.BirdContract.BirdEntry;
import com.woodpeckers.tadoba.BirdContract.BirdFamilyEntry;
import com.woodpeckers.tadoba.BirdContract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by vaishaliagarwal on 06/08/16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class BirdProviderTest extends AndroidTestCase {

    public static final String LOG_TAG = BirdProviderTest.class.getSimpleName();

    /*
       This helper function deletes all records from both database tables using the ContentProvider.
       It also queries the ContentProvider to make sure that the database has been successfully
       deleted, so it cannot be used until the Query and Delete functions have been written
       in the ContentProvider.
       Students: Replace the calls to deleteAllRecordsFromDB with this one after you have written
       the delete functionality in the ContentProvider.
     */
    public void deleteAllRecordsFromProvider() {
        RuntimeEnvironment.application.getContentResolver().delete(
                BirdEntry.CONTENT_URI,
                null,
                null
        );
        RuntimeEnvironment.application.getContentResolver().delete(
                BirdFamilyEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = RuntimeEnvironment.application.getContentResolver().query(
                BirdEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Weather table during delete", 0, cursor.getCount());
        cursor.close();

        cursor = RuntimeEnvironment.application.getContentResolver().query(
                BirdFamilyEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Location table during delete", 0, cursor.getCount());
        cursor.close();
    }
    /*
         Student: Refactor this function to use the deleteAllRecordsFromProvider functionality once
         you have implemented delete functionality there.
      */
    public void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    // Since we want each test to start with a clean slate, run deleteAllRecords
    // in setUp (called by the test runner before each test).
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        deleteAllRecords();
    }

    /*
            This test checks to make sure that the content provider is registered correctly.
            Students: Uncomment this test to make sure you've correctly registered the WeatherProvider.
         */
    @Test
    public void testProviderRegistry() {

        PackageManager pm = RuntimeEnvironment.application.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(RuntimeEnvironment.application.getPackageName(),
                BirdProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: WeatherProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + BirdContract.CONTENT_AUTHORITY,
                    providerInfo.authority, BirdContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: WeatherProvider not registered at " + RuntimeEnvironment.application.getPackageName(),
                    false);
        }
    }

}
