package com.woodpeckers.tadoba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woodpeckers.tadoba.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link BirdListFragmentInterface}
 * interface.
 */
public class BirdListFragment extends Fragment implements BirdListRecyclerViewAdapter.BirdItemListner {

    private RecyclerView recyclerView;
    private ArrayList<Bird> birdFamily;

    @Override
    public void onBirdItemViewClicked(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        Bird bird = birdFamily.get(position);

        Intent intent = new Intent(getActivity(), BirdDetailActivity.class);
        intent.putExtra(IntentParamConstants.PARAM_COMMON_NAME, bird.getCommonName());
        intent.putExtra(IntentParamConstants.PARAM_LATIN_NAME, bird.getLatinName());
        intent.putExtra(IntentParamConstants.PARAM_IMAGE_NAME, bird.getImage());
        intent.putExtra(IntentParamConstants.PARAM_FAMILY_NAME, bird.getFamily());
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
    public static BirdListFragment newInstance() {
        BirdListFragment fragment = new BirdListFragment();
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

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            DummyBirdFactory dummyBirdFactory = new DummyBirdFactory();
            birdFamily = dummyBirdFactory.getAllBirdsInFamily("Cuculidae");
            recyclerView.setAdapter(new BirdListRecyclerViewAdapter(getActivity(), birdFamily, mListener, this));
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
