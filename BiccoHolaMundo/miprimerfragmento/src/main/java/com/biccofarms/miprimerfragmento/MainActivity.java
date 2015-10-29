package com.biccofarms.miprimerfragmento;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements Communicator {

    String fragmentContextMenuTag;
    ReceiverFragment receiver;
    ReceiverNoDuplicates receiverND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void insertUser(View view) {
        SenderFragment sender = (SenderFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment1);
        sender.createUser();
    }

    @Override
    public void userIsReady(User newUser) {
        receiver = (ReceiverFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment2);
        receiver.receiveUser(newUser);
        //*
        receiverND = (ReceiverNoDuplicates)
                getSupportFragmentManager().findFragmentById(R.id.fragment3);
        receiverND.receiveUser(newUser);
        // */
    }

    @Override
    public void setFragmentContextMenu(String tag) {
        fragmentContextMenuTag = tag;
    }

    @Override
    public String getFragmentContextMenu() {
        return fragmentContextMenuTag;
    }
}
