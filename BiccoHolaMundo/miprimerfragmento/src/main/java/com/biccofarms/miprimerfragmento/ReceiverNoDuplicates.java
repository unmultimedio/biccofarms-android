package com.biccofarms.miprimerfragmento;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by julian on 10/21/15.
 */
public class ReceiverNoDuplicates extends ReceiverFragment {

    @Override
    public void receiveUser(User newUser) {
        if(!usersList.contains(newUser)){
            usersList.add(newUser);
            adapter.notifyDataSetChanged();
        }
    }
}
