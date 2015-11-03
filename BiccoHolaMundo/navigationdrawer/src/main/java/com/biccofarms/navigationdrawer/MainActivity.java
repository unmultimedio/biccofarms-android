package com.biccofarms.navigationdrawer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
        implements AdapterView.OnItemClickListener {

    ListView navigationDrawer;
    String[] optionsInDrawer;
    MyFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionsInDrawer = getResources().getStringArray(R.array.options_nav_drawer);

        if (savedInstanceState == null) {
            fragment = new MyFragment();
            Bundle args = new Bundle();
            args.putString(MyFragment.NAME_FRAGMENT_KEY, optionsInDrawer[0]);
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

        navigationDrawer = (ListView) findViewById(R.id.navigation_drawer);

        navigationDrawer.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                optionsInDrawer
        ));

        navigationDrawer.setOnItemClickListener(this);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long idRowView) {
        Toast.makeText(this, "Click en "+optionsInDrawer[position], Toast.LENGTH_SHORT).show();
    }
}
