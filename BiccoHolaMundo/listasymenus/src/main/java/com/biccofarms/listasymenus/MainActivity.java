package com.biccofarms.listasymenus;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    EditText et;
    ListView flowersLv;
    List<String> flowersNames;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.text_field);
        flowersLv = (ListView) findViewById(R.id.flowers_list);
        flowersNames = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                flowersNames);
        flowersLv.setAdapter(adapter);

        registerForContextMenu(flowersLv);
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

        switch (id){
            case R.id.action_clear:
                adapter.clear();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addFlower(View view) {
        String flowerName = et.getText().toString();

        if(flowerName.isEmpty()){
            Toast.makeText(this,
                    getResources().getString(R.string.empty_name),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        flowersNames.add(flowerName);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater im = getMenuInflater();
        im.inflate(R.menu.item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_item:
                // Borramos el elemento
                AdapterView.AdapterContextMenuInfo mi =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int index = mi.position;
                flowersNames.remove(index);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
