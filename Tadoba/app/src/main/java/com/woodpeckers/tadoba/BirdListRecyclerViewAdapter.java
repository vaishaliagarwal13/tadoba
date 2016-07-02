package com.woodpeckers.tadoba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woodpeckers.tadoba.BirdListFragment.BirdListFragmentInterface;
import com.woodpeckers.tadoba.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link BirdListFragmentInterface}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BirdListRecyclerViewAdapter extends RecyclerView.Adapter<BirdListRecyclerViewAdapter.BirdListViewHolder> {

    private final List<Bird> birdList;
    private final BirdListFragmentInterface mListener;
    private Context context;
    private BirdItemListner birdItemClickListener;


    public interface BirdItemListner {
        public void onBirdItemViewClicked(View view);
    }

    public BirdListRecyclerViewAdapter(Context context, List<Bird> itemList, BirdListFragmentInterface listener, BirdItemListner birdItemClickListener) {
        this.birdList = itemList;
        this.mListener = listener;
        this.context = context;
        this.birdItemClickListener = birdItemClickListener;
    }

    @Override
    public BirdListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bird_family_list_item, parent, false);
        return new BirdListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BirdListViewHolder holder, int position) {

        Bird bird = birdList.get(position);
        holder.mCommonNameView.setText(bird.getCommonName());
        holder.mLatinNameView.setText(bird.getLatinName());

        Picasso.with(context)
                .load("file:///android_asset/" + bird.getImage() + ".jpg")
                .into(holder.mBirdImageView);



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != birdItemClickListener) {
                    birdItemClickListener.onBirdItemViewClicked(v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return birdList.size();
    }

    public class BirdListViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCommonNameView;
        public final TextView mLatinNameView;
        public final ImageView mBirdImageView;
        public final CheckBox mViewedCheckBox;

        public BirdListViewHolder (View view) {
            super(view);
            mView = view;
            mBirdImageView = (ImageView) view.findViewById(R.id.bird_circle_image);
            mCommonNameView = (TextView) view.findViewById(R.id.bird_common_name);
            mLatinNameView = (TextView) view.findViewById(R.id.bird_latin_name);
            mViewedCheckBox = (CheckBox) view.findViewById(R.id.bird_viewed_chkbox);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCommonNameView.getText() + "'";
        }
    }
}
