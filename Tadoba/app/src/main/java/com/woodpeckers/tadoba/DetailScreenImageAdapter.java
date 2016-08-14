package com.woodpeckers.tadoba;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailScreenImageAdapter extends PagerAdapter {

    private Activity activity;
    private ArrayList<ImageList> birdImageList;
    private LayoutInflater layoutInflater;
    private Bird bird;

    // constructor
    public DetailScreenImageAdapter(Activity activity, Bird bird) {
        this.activity = activity;
        this.birdImageList = bird.getImageList();
        this.bird = bird;
    }

    @Override
    public int getCount() {
        return this.birdImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CoordinatorLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;

        layoutInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = layoutInflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);

        Picasso.with(activity)
                .load("file:///android_asset/" + birdImageList.get(position).getImage())
                .noFade()
                .into(imgDisplay);


        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    public Bird getBird() {
        return bird;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}