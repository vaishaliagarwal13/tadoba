package com.woodpeckers.tadoba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.woodpeckers.tadoba.BirdListFragment.BirdListFragmentInterface;
import com.woodpeckers.tadoba.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link BirdListFragmentInterface}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BirdListRecyclerViewAdapter extends RecyclerViewCursorAdapter<BirdListRecyclerViewAdapter.BirdListViewHolder> {

    private final BirdListFragmentInterface mListener;
    private Context context;
    private BirdItemListener birdItemClickListener;


    public interface BirdItemListener {
        public void onBirdItemViewClicked(View view);
    }

    public BirdListRecyclerViewAdapter(BirdListFragmentInterface listener, BirdItemListener birdItemClickListener) {
        this(null, listener, birdItemClickListener);
    }

    public BirdListRecyclerViewAdapter(Cursor cursor, BirdListFragmentInterface listener, BirdItemListener birdItemClickListener) {
        super(cursor);
        mListener = listener;
        this.birdItemClickListener = birdItemClickListener;
    }

    @Override
    public BirdListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bird_family_list_item, parent, false);
        return new BirdListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        final BirdListViewHolder birdViewHolder = (BirdListViewHolder)holder;
        final String birdId;

        final Bird bird = Bird.fromCursor(cursor);

        birdId = bird.getId();

        birdViewHolder.mCommonNameView.setText(bird.getCommonName());
        birdViewHolder.mLatinNameView.setText(bird.getLatinName());

        if (bird.isViewed()) {
            birdViewHolder.mViewedCheckBox.setSelected(true);
            birdViewHolder.mViewedCheckBox.setChecked(true);
            birdViewHolder.mViewedCheckBox.setEnabled(false);
        }
        else {
            birdViewHolder.mViewedCheckBox.setSelected(false);
            birdViewHolder.mViewedCheckBox.setChecked(false);
            birdViewHolder.mViewedCheckBox.setEnabled(true);
        }

        Picasso.with(context)
                .load("file:///android_asset/" + bird.getImageList().get(0).getImage())
                .resize(300, 0)
                .into(birdViewHolder.mBirdImageView);

        birdViewHolder.mViewedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = birdViewHolder.mViewedCheckBox.isChecked();
                if (checked) {
                    markBirdAsViewed(birdId, true);
                    birdViewHolder.mViewedCheckBox.setEnabled(false);
                    bird.setViewed(true);
                }
            }
        });

        birdViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != birdItemClickListener) {
                    birdItemClickListener.onBirdItemViewClicked(v);
                }
            }
        });
    }

    private void markBirdAsViewed(String paramBirdID, boolean bViewed) {
        ContentValues birdContentValues = new ContentValues();
        birdContentValues.put(BirdContract.BirdEntry.COLUMN_IS_VIEWED, bViewed);
        birdContentValues.put(BirdContract.BirdEntry.COLUMN_VIEWED_DATE_TIME, System.currentTimeMillis());
        birdContentValues.put(BirdContract.BirdEntry.COLUMN_ID, paramBirdID);

        String where = BirdContract.BirdEntry.TABLE_NAME+
                "." + BirdContract.BirdEntry.COLUMN_ID + " = ? ";
        String[] selectionArgs = new String[]{paramBirdID};
        context.getContentResolver().update(BirdContract.BirdEntry.CONTENT_URI, birdContentValues, where, selectionArgs);
    }

    public Bird getBirdAtPosition(int position) {
        Cursor cursor = getCursor();
        cursor.moveToFirst();
        cursor.move(position);
        Bird bird = Bird.fromCursor(cursor);
        return bird;
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
