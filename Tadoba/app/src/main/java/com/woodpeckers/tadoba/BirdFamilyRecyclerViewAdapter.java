package com.woodpeckers.tadoba;

import java.util.List;
import android.content.Context;
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
public class BirdFamilyRecyclerViewAdapter extends RecyclerView.Adapter <BirdFamilyRecyclerViewAdapter.BirdFamilyViewHolder>{
    private List<BirdFamily> birdFamilyList;
    private Context context;
    private HomeActivityFragment.BirdFamilyInterface mCallback;

    public BirdFamilyRecyclerViewAdapter(Context context, List<BirdFamily> itemList, HomeActivityFragment.BirdFamilyInterface callback) {
        this.birdFamilyList = itemList;
        this.context = context;
        mCallback = callback;
    }

    @Override
    public BirdFamilyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        BirdFamilyViewHolder view = new BirdFamilyViewHolder(layoutView, mCallback);
        return view;
    }

    @Override
    public void onBindViewHolder(BirdFamilyViewHolder holder, int position) {
        BirdFamily birdsCategories = birdFamilyList.get(position);

        Picasso.with(context)
                .load("file:///android_asset/" + birdsCategories.getImage() + ".jpg")
                .into(holder.familyImage);
        holder.familyName.setText(birdsCategories.getName());
    }

    @Override
    public int getItemCount() {
        return this.birdFamilyList.size();
    }

    public static class BirdFamilyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView familyName;
        public ImageView familyImage;
        private HomeActivityFragment.BirdFamilyInterface mCallback;

        public BirdFamilyViewHolder(View itemView, HomeActivityFragment.BirdFamilyInterface callback) {
            super(itemView);
            itemView.setOnClickListener(this);
            familyName = (TextView)itemView.findViewById(R.id.familyName);
            familyImage = (ImageView)itemView.findViewById(R.id.familyPic);
            mCallback = callback;
        }

        @Override
        public void onClick(View view) {
            if (mCallback != null) {
                mCallback.onBirdFamilyClicked(getPosition());
            }
 //           Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
