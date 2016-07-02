package com.woodpeckers.tadoba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity implements HomeActivityFragment.BirdFamilyInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.title_bird_families);
        HomeActivityFragment fragment = HomeActivityFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_generate_report) {
            return true;
        }
        else if (id == R.id.action_list_viewed_birds) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBirdFamilyClicked(int position) {
        Intent intent = new Intent(this, BirdListActivity.class);
        startActivity(intent);
    }

    public void setActionBarTitle(String s) {
        getSupportActionBar().setTitle(s);
    }
}
