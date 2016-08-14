package com.woodpeckers.tadoba;

import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1;
    private BirdFamilyInterface mCallback;
    private BirdFamilyRecyclerViewCursorAdapter birdFamilyAdapter;

    public HomeActivityFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeActivityFragment newInstance() {
        HomeActivityFragment fragment = new HomeActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        int columns = (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 3:2;


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.title_bird_families));

        RecyclerView rView = (RecyclerView)view.findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(layoutManager);
        birdFamilyAdapter = new BirdFamilyRecyclerViewCursorAdapter(null, mCallback);
        rView.setAdapter(birdFamilyAdapter);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        return view;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(
                getActivity(),
                BirdContract.BirdFamilyEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        birdFamilyAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        birdFamilyAdapter.swapCursor(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_bird_families);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof BirdFamilyInterface) {
            mCallback = (BirdFamilyInterface)activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement BirdFamilyInterface");
        }
    }

    public interface BirdFamilyInterface {
        public void onBirdFamilyClicked(String familyId);
    }
}
