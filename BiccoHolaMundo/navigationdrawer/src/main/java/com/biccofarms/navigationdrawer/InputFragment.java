package com.biccofarms.navigationdrawer;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {

    Activity myParentActivity;
    FlowersInterface myParentInterface;

    EditText flowerET;

    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myParentActivity = activity;
        try {
            myParentInterface = (FlowersInterface) activity;
        } catch(ClassCastException e){
            Log.e("bicco", "La actividad no implementa la interfaz");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);
        flowerET = (EditText)
                rootView.findViewById(R.id.flower_name);
        return rootView;
    }


    public void addButtonClicked() {
        String flowerName = flowerET.getText().toString();
        if(!flowerName.isEmpty()){
            myParentInterface.newFlower(flowerName);
        }
    }
}
