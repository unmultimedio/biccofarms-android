package com.biccofarms.navigationdrawer;

import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;


public class MainActivity extends ActionBarActivity
        implements AdapterView.OnItemClickListener, FlowersInterface, Serializable {

    DrawerLayout drawerLayout;
    ListView navigationDrawer;
    String[] optionsInDrawer;
    MyFragment fragment;

    InputFragment fragmentInput;
    FlowersFragment fragmentFlowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        optionsInDrawer = getResources().getStringArray(R.array.options_nav_drawer);

        fragmentInput = new InputFragment();
        fragmentFlowers = new FlowersFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("context", this);
        fragmentFlowers.setArguments(bundle);
        fragmentFlowers.setUpFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragmentInput)
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
        selectItem(position);
    }

    private void selectItem(int position) {

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        switch (position){
            case 0:
                fm.beginTransaction()
                        .replace(R.id.container, fragmentInput)
                        .commit();
                break;
            case 1:
                fm.beginTransaction()
                        .replace(R.id.container, fragmentFlowers)
                        .commit();
                break;
        }

        setTitle(optionsInDrawer[position]);
        drawerLayout.closeDrawer(navigationDrawer);
    }

    @Override
    public void addFlower(View view) {
        fragmentInput.addButtonClicked();
    }

    @Override
    public void newFlower(String flowerName) {
        selectItem(1);
        fragmentFlowers.addFlower(flowerName);
    }
}
