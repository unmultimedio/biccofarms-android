package com.biccofarms.dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by julian on 10/28/15.
 */
public class MyAlert extends DialogFragment {

    public static String TITLE_KEY = MyAlert.class.getCanonicalName()+".title";
    public static String MESSAGE_KEY = MyAlert.class.getCanonicalName()+".message";

    Activity myParentActivity;
    Communicator myParentInterface;

    public MyAlert() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myParentActivity = activity;
        try {
            myParentInterface = (Communicator) activity;
        }catch(ClassCastException e){
            Log.e("bicco", "La actividad "+
            myParentActivity.getClass().getCanonicalName()+
            " no implementa la interfaz Communicator");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myParentActivity);

        builder.setTitle(getArguments().getString(TITLE_KEY));
        builder.setMessage(getArguments().getString(MESSAGE_KEY));

        builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(myParentActivity, "Le diste click a Positivo",
                        Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(myParentActivity, "Le diste click a Negativo",
                        Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton(R.string.neutral_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(myParentActivity, "Le diste click a Neutral",
                        Toast.LENGTH_SHORT).show();
            }
        });



        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }
}
