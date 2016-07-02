package com.woodpeckers.tadoba;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private BirdFamilyInterface mCallback;

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

        DummyBirdFactory dummyBirdFactory = new DummyBirdFactory();
        BirdFamilyRecyclerViewAdapter rcAdapter = new BirdFamilyRecyclerViewAdapter(getActivity(), dummyBirdFactory.getBirdFamilies(), mCallback);
        rView.setAdapter(rcAdapter);

        return view;
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
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public interface BirdFamilyInterface {
        public void onBirdFamilyClicked(int position);
    }
}
