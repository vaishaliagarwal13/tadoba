package com.woodpeckers.tadoba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link BirdListFragmentInterface}
 * interface.
 */
public class BirdListFragment extends Fragment implements BirdListRecyclerViewAdapter.BirdItemListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String BIRD_FAMILY_ID = "bird_family_id";
    private RecyclerView recyclerView;
    private BirdListRecyclerViewAdapter adapter;

    @Override
    public void onBirdItemViewClicked(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        Bird bird = adapter.getBirdAtPosition(position);

        Intent intent = new Intent(getActivity(), DetailScreenViewActivity.class);
        intent.putExtra(IntentParamConstants.PARAM_BIRD_ID, bird.getId());
        intent.putExtra(IntentParamConstants.PARAM_COMMON_NAME, bird.getCommonName());
        intent.putExtra(IntentParamConstants.PARAM_LATIN_NAME, bird.getLatinName());
        intent.putExtra(IntentParamConstants.PARAM_IMAGE_NAME1, bird.getImageList().get(0).getImage());
        if(bird.getImageList().size() > 1) {
            intent.putExtra(IntentParamConstants.PARAM_IMAGE_NAME2, bird.getImageList().get(1).getImage());
        }

        intent.putExtra(IntentParamConstants.PARAM_FAMILY_NAME, bird.getFamilyName());
        intent.putExtra(IntentParamConstants.PARAM_BIRD_VIEWED, bird.isViewed());
        startActivity(intent);

    }

    private BirdListFragmentInterface mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BirdListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BirdListFragment newInstance(String familyId) {
        BirdListFragment fragment = new BirdListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BIRD_FAMILY_ID, familyId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bird_list, container, false);
        Bundle bundle = getArguments();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new BirdListRecyclerViewAdapter(null, mListener, this);
            recyclerView.setAdapter(adapter);

            getLoaderManager().initLoader(1, bundle, this);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof BirdListFragmentInterface) {
            mListener = (BirdListFragmentInterface)activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement BirdListFragmentInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String familyId = args.getString(BIRD_FAMILY_ID);
        String [] selectionArgs = new String[]{familyId};

        Uri birdsForFamilyUri = BirdContract.BirdEntry.buildBirdUriForFamily(familyId);

        CursorLoader loader = new CursorLoader(
                getActivity(),
                birdsForFamilyUri,
                null,
                null,
                selectionArgs,
                null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface BirdListFragmentInterface {
        void onBirdListItemClicked(Bird bird);
    }
}
