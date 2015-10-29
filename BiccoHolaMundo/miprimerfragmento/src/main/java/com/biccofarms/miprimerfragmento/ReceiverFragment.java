package com.biccofarms.miprimerfragmento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 10/21/15.
 */
public class ReceiverFragment extends Fragment implements DialogIsPresent, Serializable {

    public static String USERS_KEY = ReceiverFragment.class.getCanonicalName()+".users";

    Activity myParentActivity;
    Communicator myParentInterface;
    ListView usersListView;
    List<User> usersList;
    MyOwnAdapter adapter;

    public ReceiverFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myParentActivity = activity;
        try {
            myParentInterface = (Communicator) activity;
        } catch (ClassCastException e){
            Log.e("bicco", "No implementa comunicador");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null){
            usersList = (List<User>) savedInstanceState.getSerializable(USERS_KEY);
        }else{
            usersList = new ArrayList<>();
        }
        View rootView = inflater.inflate(R.layout.fragment_receiver, container, false);
        usersListView = (ListView) rootView.findViewById(R.id.users_list);
        adapter = new MyOwnAdapter(myParentActivity, R.layout.user_row, usersList);
        usersListView.setAdapter(adapter);
        registerForContextMenu(usersListView);
        usersListView.setOnCreateContextMenuListener(this);
        return rootView;
    }



    public void receiveUser(User newUser) {
        usersList.add(newUser);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(USERS_KEY, (java.io.Serializable) usersList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater mi = myParentActivity.getMenuInflater();
        mi.inflate(R.menu.user_menu, menu);
        myParentInterface.setFragmentContextMenu(getTag());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(!myParentInterface.getFragmentContextMenu().equals(getTag())) return false;
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int userPosition = menuInfo.position;
        if(getUserVisibleHint()){
            switch (item.getItemId()){
                case R.id.action_user_edit:
                    EditUserDialog ed = new EditUserDialog();
                    Bundle args = new Bundle();
                    args.putSerializable(EditUserDialog.USER_KEY,
                            usersList.get(userPosition));
                    args.putSerializable(EditUserDialog.FRAGMENT_KEY,
                            this);
                    ed.setArguments(args);
                    ed.show(myParentActivity.getFragmentManager(), "edit-user");
                    return true;
                case R.id.action_user_duplicate:
                    AlertDialog.Builder dbd = new AlertDialog.Builder(myParentActivity);
                    dbd.setTitle("Duplicando " + usersList.get(userPosition).getName());
                    dbd.setMessage("¿Está seguro que desea duplicar al usuario " +
                            "\"" + usersList.get(userPosition).getName() + "\"" +
                            " de la lista?");
                    dbd.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            duplicateUser(userPosition);
                        }
                    });
                    dbd.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(myParentActivity, "No se duplicó",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    dbd.create().show();
                    return true;
                case R.id.action_user_delete:
                    AlertDialog.Builder db = new AlertDialog.Builder(myParentActivity);
                    db.setTitle("Eliminando "+usersList.get(userPosition).getName());
                    db.setMessage("¿Está seguro que desea eliminar al usuario " +
                            "\"" + usersList.get(userPosition).getName() + "\"" +
                            " de la lista?");
                    db.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteUser(userPosition);
                        }
                    });
                    db.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(myParentActivity, "No se borró",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    db.create().show();
                    return true;
            }
        }

        return false;
    }

    void deleteUser(int userPosition){
        usersList.remove(userPosition);
        adapter.notifyDataSetChanged();
    }

    void duplicateUser(int userPosition){
        User newUser = usersList.get(userPosition).makeACopy();
        usersList.add(userPosition, newUser);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void userUpdated() {
        adapter.notifyDataSetChanged();
    }
}
