package com.biccofarms.basededatos;

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

    DBHelper helper;
    ListView usersList;
    ArrayAdapter adapter;
    List<User> users;
    List<String> usersNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);

        users = new ArrayList<>();
        usersNames = new ArrayList<>();
        usersList = (ListView) findViewById(R.id.users_list);

        registerForContextMenu(usersList);

        loadList();
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

    public void insertUser(View view) {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String drink = ((EditText) findViewById(R.id.drink)).getText().toString();
        String sport = ((EditText) findViewById(R.id.sport)).getText().toString();

        if(!name.isEmpty() && !drink.isEmpty() && !sport.isEmpty()){

            long l = helper.insertUser(new User(name, drink, sport));
            if (l != -1){
                Toast.makeText(this,
                        getResources().getString(R.string.inserted_user)+l,
                        Toast.LENGTH_SHORT).show();
                loadList();
            }else{
                Toast.makeText(this,
                        getResources().getString(R.string.user_not_inserted),
                        Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, getResources().getString(R.string.empty_data),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_user, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_user){
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int positionInList = menuInfo.position;
            int rows = helper.deleteUser(users.get(positionInList).getId());
            if(rows > 0){
                Toast.makeText(this,
                        getResources().getString(R.string.deleted_user) + rows,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        getResources().getString(R.string.user_not_deleted),
                        Toast.LENGTH_SHORT).show();
            }
            loadList();
        }
        return super.onContextItemSelected(item);
    }

    public List<String> toStringList(List<User> users){
        List<String> names = new ArrayList<>();
        for(User u : users){
            names.add(u.getName());
        }
        return names;
    }

    public void loadList(){
        users = helper.queryUsers();
        usersNames = toStringList(users);

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                usersNames);
        usersList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
