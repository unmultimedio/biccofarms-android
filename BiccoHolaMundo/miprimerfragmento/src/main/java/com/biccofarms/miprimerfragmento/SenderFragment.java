package com.biccofarms.miprimerfragmento;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class SenderFragment extends Fragment {

    Activity parentActivity;
    Communicator parentInterface;

    public SenderFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.parentActivity = activity;
            parentInterface = (Communicator) this.parentActivity;
            // La actividad papa si implementa comunicador
        }catch (ClassCastException e){
            // La actividad papa no implementa comunicador
            Log.e("class-cast","Hey viejito implemente el Comunicador");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sender, container, false);
        return rootView;
    }

    public void createUser(){
        EditText nameET = (EditText) parentActivity.findViewById(R.id.name);
        EditText drinkET = (EditText) parentActivity.findViewById(R.id.drink);
        EditText sportET = (EditText) parentActivity.findViewById(R.id.sport);
        String name = nameET.getText().toString();
        String drink = drinkET.getText().toString();
        String sport = sportET.getText().toString();

        if(!name.isEmpty() && !drink.isEmpty() && !sport.isEmpty()){

            User newUser = new User(name, drink, sport);
            parentInterface.userIsReady(newUser);

            nameET.setText("");
            drinkET.setText("");
            sportET.setText("");

        }else{
            Toast.makeText(parentActivity, getResources().getString(R.string.empty_data),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
