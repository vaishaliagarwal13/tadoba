package com.woodpeckers.tadoba;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link BirdDetailsInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BirdDetailsInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_COMMON_NAME = "param1";
    private static final String ARG_PARAM_LATIN_NAME = "param2";
    private static final String ARG_PARAM_FAMILY_NAME = "param3";
    private static final String ARG_PARAM_DESCRIPTION = "param4";


    // TODO: Rename and change types of parameters
    private String mParamCommonName;
    private String mParamLatinName;
    private String mParamFamilyName;
    private String mDescription;


    public BirdDetailsInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param commonName Parameter 1.
     * @param latinName Parameter 2.
     * @param familyName Parameter 3.
     * @param description Parameter 4.
     * @return A new instance of fragment BirdDetailsInfoFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static BirdDetailsInfoFragment newInstance(String commonName, String latinName, String familyName, String description) {
        BirdDetailsInfoFragment fragment = new BirdDetailsInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_COMMON_NAME, commonName);
        args.putString(ARG_PARAM_LATIN_NAME, latinName);
        args.putString(ARG_PARAM_FAMILY_NAME, familyName);
        args.putString(ARG_PARAM_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamCommonName = getArguments().getString(ARG_PARAM_COMMON_NAME);
            mParamLatinName = getArguments().getString(ARG_PARAM_LATIN_NAME);
            mParamFamilyName = getArguments().getString(ARG_PARAM_FAMILY_NAME);
            mDescription = getArguments().getString(ARG_PARAM_DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_bird_details_info, container, false);
        View view = inflater.inflate(R.layout.fragment_bird_details_info, container, false);

        if (getArguments() != null) {
            mParamCommonName = getArguments().getString(ARG_PARAM_COMMON_NAME);
            mParamLatinName = getArguments().getString(ARG_PARAM_LATIN_NAME);
            mParamFamilyName = getArguments().getString(ARG_PARAM_FAMILY_NAME);
            mDescription = getArguments().getString(ARG_PARAM_DESCRIPTION);
        }

        TextView commonName = (TextView) view.findViewById(R.id.commonName);
        TextView latinName = (TextView) view.findViewById(R.id.latinName);
        TextView familyName = (TextView) view.findViewById(R.id.familyName);
        TextView description = (TextView) view.findViewById(R.id.description);

        commonName.setText("Common Name : " + mParamCommonName);
        latinName.setText("Latin Name : " + mParamLatinName);
        familyName.setText("Family Name : " + mParamFamilyName);
       // description.setText(mDescription);

        return view;
    }

}
