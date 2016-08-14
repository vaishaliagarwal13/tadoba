package com.woodpeckers.tadoba;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BirdListActivity extends AppCompatActivity implements BirdListFragment.BirdListFragmentInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_list);
        setTitle(R.string.title_birds);
        Intent intent = getIntent();
        String birdFamilyId = intent.getStringExtra(HomeActivity.BIRD_FAMILY_ID);

        BirdListFragment fragment = BirdListFragment.newInstance(birdFamilyId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBirdListItemClicked(Bird bird) {
    }
}
