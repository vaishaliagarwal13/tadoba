package com.woodpeckers.tadoba;

import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by vaishaliagarwal on 10/06/16.
 */
public class BirdFamilyRecyclerViewCursorAdapter extends RecyclerViewCursorAdapter<BirdFamilyRecyclerViewCursorAdapter.BirdFamilyViewHolder>  {
    private Context context;
    private HomeActivityFragment.BirdFamilyInterface mCallback;


    public BirdFamilyRecyclerViewCursorAdapter(HomeActivityFragment.BirdFamilyInterface callback) {
        this(null, callback);
    }

    public BirdFamilyRecyclerViewCursorAdapter(Cursor cursor, HomeActivityFragment.BirdFamilyInterface callback) {
        super(cursor);
        mCallback = callback;
    }

    @Override
    public BirdFamilyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        BirdFamilyViewHolder view = new BirdFamilyViewHolder(layoutView, mCallback);
        return view;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {

        BirdFamilyViewHolder birdFamilyViewHolder = (BirdFamilyViewHolder)holder;

        BirdFamily birdsCategories = BirdFamily.fromCursor(cursor);
        String familyId = birdsCategories.getFamilyID();
        Picasso.with(context)
                .load("file:///android_asset/" + birdsCategories.getFamilyImage())
                .into(birdFamilyViewHolder.familyImage);
        birdFamilyViewHolder.familyLatinName.setText(birdsCategories.getLatinName());
        birdFamilyViewHolder.familyCommonName.setText(birdsCategories.getCommonName());
        birdFamilyViewHolder.setFamilyId(familyId);
    }

    public static class BirdFamilyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView familyLatinName;
        public TextView familyCommonName;
        public ImageView familyImage;
        private HomeActivityFragment.BirdFamilyInterface mCallback;
        private String familyId;

        public BirdFamilyViewHolder(View itemView, HomeActivityFragment.BirdFamilyInterface callback) {
            super(itemView);
            itemView.setOnClickListener(this);
            familyLatinName = (TextView)itemView.findViewById(R.id.familyLatinName);
            familyCommonName = (TextView)itemView.findViewById(R.id.familyCommonName);
            familyImage = (ImageView)itemView.findViewById(R.id.familyPic);
            mCallback = callback;
        }

        public void setFamilyId(String familyId) {
            this.familyId = familyId;
        }

        @Override
        public void onClick(View view) {
            if (mCallback != null) {
                mCallback.onBirdFamilyClicked(familyId);
            }
        }
    }

}
