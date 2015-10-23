package com.biccofarms.miprimerfragmento;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 10/21/15.
 */
public class ReceiverFragment extends Fragment {

    Activity parentActivity;
    ListView usersListView;
    List<User> usersList;
    MyOwnAdapter adapter;

    public ReceiverFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
        usersList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null){
            usersList = (List<User>) savedInstanceState.getSerializable("users");
        }
        View rootView = inflater.inflate(R.layout.fragment_receiver, container, false);
        usersListView = (ListView) rootView.findViewById(R.id.users_list);
        adapter = new MyOwnAdapter(parentActivity, R.layout.user_row, usersList);
        usersListView.setAdapter(adapter);
        return rootView;
    }

    public void receiveUser(User newUser) {
        usersList.add(newUser);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("users", (java.io.Serializable) usersList);
    }
}
