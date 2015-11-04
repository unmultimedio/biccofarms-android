package com.biccofarms.navigationdrawer;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FlowersFragment extends Fragment {

    Activity myParentActivity;

    List<String> flowersNames;
    ArrayAdapter<String> flowersAdapter;
    ListView flowersList;

    public FlowersFragment() {
        // Required empty public constructor
    }


    public void setUpFragment(){
        flowersNames = new ArrayList<>();
        Context context = (Context) getArguments().getSerializable("context");
        flowersAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1,
                flowersNames);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myParentActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_flowers, container, false);
        flowersList = (ListView) rootView.findViewById(R.id.flowers_list);
        flowersList.setAdapter(flowersAdapter);
        return rootView;
    }

    public void addFlower(String flowerName) {
        flowersNames.add(flowerName);
        flowersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
