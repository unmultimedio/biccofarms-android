package com.biccofarms.miprimerfragmento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by julian on 10/21/15.
 */
public class MyOwnAdapter extends ArrayAdapter {

    Context context;
    int resource;
    List<User> users;

    public MyOwnAdapter(Context context, int resource, List users) {
        super(context, resource, users);

        this.context = context;
        this.resource = resource;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View thisUserRow = li.inflate(resource, parent, false);

        TextView userName = (TextView) thisUserRow.findViewById(R.id.user_name);
        TextView userDrink = (TextView) thisUserRow.findViewById(R.id.user_drink);
        TextView userSport = (TextView) thisUserRow.findViewById(R.id.user_sport);

        userName.setText(users.get(position).getName());
        userDrink.setText(users.get(position).getDrink());
        userSport.setText(users.get(position).getSport());

        return thisUserRow;
    }
}
