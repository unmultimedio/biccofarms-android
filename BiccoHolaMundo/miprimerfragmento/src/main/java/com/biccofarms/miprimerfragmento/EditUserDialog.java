package com.biccofarms.miprimerfragmento;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by julian on 10/28/15.
 */
public class EditUserDialog extends DialogFragment implements
        DialogInterface.OnClickListener{

    public static final String FRAGMENT_KEY = EditUserDialog.class.getCanonicalName()+".fragment";
    public static final String USER_KEY = EditUserDialog.class.getCanonicalName()+".userData";

    User u;
    EditText nameET, drinkET, sportET;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        u = (User) getArguments().getSerializable(USER_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Editar \""+ u.getName() +"\"");

        builder.setView(getMyView());

        builder.setPositiveButton(R.string.ok_button, this);
        builder.setNegativeButton(R.string.cancel_button, this);

        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }

    public View getMyView(){
        LayoutInflater li = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.edit_user_layot, null);

        nameET = (EditText)view.findViewById(R.id.name_value);
        drinkET = (EditText)view.findViewById(R.id.drink_value);
        sportET = (EditText)view.findViewById(R.id.sport_value);

        nameET.setText(u.getName());
        drinkET.setText(u.getDrink());
        sportET.setText(u.getSport());

        return view;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case DialogInterface.BUTTON_POSITIVE:
                u.setName(nameET.getText().toString());
                u.setDrink(drinkET.getText().toString());
                u.setSport(sportET.getText().toString());
                DialogIsPresent myInterface = (DialogIsPresent)
                        getArguments().getSerializable(FRAGMENT_KEY);
                myInterface.userUpdated();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }
}
