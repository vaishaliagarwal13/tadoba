package com.woodpeckers.tadoba;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class DetailScreenViewActivity extends Activity {

	private DetailScreenImageAdapter adapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity_full_screen_view);

        Intent intent = getIntent();
        final String paramBirdID = intent.getStringExtra(IntentParamConstants.PARAM_BIRD_ID);
        String paramCommonName = intent.getStringExtra(IntentParamConstants.PARAM_COMMON_NAME);
        String paramLatinName = intent.getStringExtra(IntentParamConstants.PARAM_LATIN_NAME);
        String paramImage1 = intent.getStringExtra(IntentParamConstants.PARAM_IMAGE_NAME1);
        String paramImage2 = intent.getStringExtra(IntentParamConstants.PARAM_IMAGE_NAME2);
        String paramFamilyName = intent.getStringExtra(IntentParamConstants.PARAM_FAMILY_NAME);
        boolean paramIsBirdViewed = intent.getBooleanExtra(IntentParamConstants.PARAM_BIRD_VIEWED,false);

		viewPager = (ViewPager) findViewById(R.id.pager);



        TextView commonName = (TextView) findViewById(R.id.commonName);
        TextView latinName = (TextView) findViewById(R.id.latinName);
        TextView familyName = (TextView) findViewById(R.id.familyName);

        commonName.setText("Common Name : " + paramCommonName);
        latinName.setText("Latin Name : " + paramLatinName);
        familyName.setText("Family Name : " + paramFamilyName);

        ArrayList<ImageList> imagePaths = new ArrayList();

        ImageList imgObj1 = new ImageList();
        imgObj1.setImage(paramImage1);
        imagePaths.add(imgObj1);
        if (paramImage2 != null) {
            ImageList imgObj2 = new ImageList();
            imgObj2.setImage(paramImage2);
            imagePaths.add(imgObj2);
        }

        Bird bird = new Bird();
        bird.setFamilyName(paramFamilyName);
        bird.setCommonName(paramCommonName);
        bird.setLatinName(paramLatinName);
        bird.setId(paramBirdID);
        bird.setViewed(paramIsBirdViewed);
        bird.setImageList(imagePaths);

        adapter = new DetailScreenImageAdapter(DetailScreenViewActivity.this, bird);

        final FloatingActionButton fabBirdView = (FloatingActionButton)findViewById(R.id.fabBirdView);
        setupBirdAsViewedButtonUI(fabBirdView, bird.isViewed());
        fabBirdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bird bird = adapter.getBird();
                markBirdAsViewed(bird, !bird.isViewed());
                setupBirdAsViewedButtonUI(fabBirdView, bird.isViewed());
            }
        });


        ImageButton btnClose = (ImageButton) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        viewPager.setAdapter(adapter);
        // displaying selected image first
        viewPager.setCurrentItem(0);
	}

    private void setupBirdAsViewedButtonUI(FloatingActionButton fabBirdView, boolean isViewed) {
        int resId = android.R.drawable.checkbox_off_background;
        if (isViewed) {
            resId = android.R.drawable.checkbox_on_background;
        } else {
            resId = android.R.drawable.checkbox_off_background;
        }
        fabBirdView.setImageDrawable(ContextCompat.getDrawable(DetailScreenViewActivity.this, resId));
    }

    private void markBirdAsViewed(Bird bird, boolean bViewed) {
        ContentValues birdContentValues = new ContentValues();
        long viewedDate = System.currentTimeMillis();
        birdContentValues.put(BirdContract.BirdEntry.COLUMN_IS_VIEWED, bViewed);
        if (!bViewed) {
            viewedDate = 0;
        }
        birdContentValues.put(BirdContract.BirdEntry.COLUMN_VIEWED_DATE_TIME, viewedDate);
        birdContentValues.put(BirdContract.BirdEntry.COLUMN_ID, bird.getId());

        String where = BirdContract.BirdEntry.TABLE_NAME+
                "." + BirdContract.BirdEntry.COLUMN_ID + " = ? ";
        String[] selectionArgs = new String[]{bird.getId()};
        getContentResolver().update(BirdContract.BirdEntry.CONTENT_URI, birdContentValues, where, selectionArgs);

        bird.setViewed(bViewed);
        bird.setViewedDateTime(viewedDate);

    }

}
