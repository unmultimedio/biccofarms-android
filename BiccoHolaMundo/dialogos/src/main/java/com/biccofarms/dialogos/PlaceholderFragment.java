package com.biccofarms.dialogos;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    DialogFragment myDialog;
    EditText titleET, messageET;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        titleET = (EditText) rootView.findViewById(R.id.alert_title);
        messageET = (EditText) rootView.findViewById(R.id.alert_content);
        return rootView;
    }

    public void doAlert() {
        String title = titleET.getText().toString();
        String message = messageET.getText().toString();
        myDialog = new MyAlert();

        Bundle args = new Bundle();
        args.putString(MyAlert.TITLE_KEY, title);
        args.putString(MyAlert.MESSAGE_KEY, message);
        myDialog.setArguments(args);

        myDialog.show(getActivity().getFragmentManager(), "bicco-alert");
    }
}
