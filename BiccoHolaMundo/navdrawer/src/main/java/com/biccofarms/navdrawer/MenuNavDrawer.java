package com.biccofarms.navdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by julian on 11/2/15.
 */
public class MenuNavDrawer extends ListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] options = getResources().getStringArray(R.array.options_nav_drawer);
        getListView().setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                options));
        getListView().setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
