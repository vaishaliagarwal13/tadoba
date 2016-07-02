package com.woodpeckers.tadoba;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;


public class BirdDetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bird_detail);

        Intent intent = getIntent();
        String commonName = intent.getStringExtra(IntentParamConstants.PARAM_COMMON_NAME);
        String latinName = intent.getStringExtra(IntentParamConstants.PARAM_LATIN_NAME);
        String image = intent.getStringExtra(IntentParamConstants.PARAM_IMAGE_NAME);
        String familyName = intent.getStringExtra(IntentParamConstants.PARAM_FAMILY_NAME);

        setTitle(commonName);

        BirdDetailPictureFragment fragment = BirdDetailPictureFragment.newInstance(commonName, image);
        BirdDetailsInfoFragment dtlsFragment = BirdDetailsInfoFragment.newInstance(commonName, latinName, familyName, getString(R.string.hello_blank_fragment));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container_pic, fragment);
        transaction.add(R.id.fragment_container_info, dtlsFragment);
        transaction.commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setSelected(!fab.isSelected());
                if (fab.isSelected()) {
                    fab.setImageResource(android.R.drawable.checkbox_off_background);
                    Toast.makeText(view.getContext(), "Not Viewed", Toast.LENGTH_SHORT).show();
                }
                else {
                    fab.setImageResource(android.R.drawable.checkbox_on_background);
                    Toast.makeText(view.getContext(), "Viewed", Toast.LENGTH_SHORT).show();
                }

               /* Drawable drawable = fab.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }*/
            }
        });
    }
}
