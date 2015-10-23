package com.biccofarms.segundaclase;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by julian on 10/16/15.
 */
public class ResponseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        Intent i = getIntent();
        String userText = i.getStringExtra("text");
        int color = i.getIntExtra("color",0);

        // Toast.makeText(this,"Llegó T:"+userText+" y C:"+color, Toast.LENGTH_SHORT).show();

        TextView tv = (TextView) findViewById(R.id.responseText);
        tv.setText(userText);
        tv.setTextColor(color);
    }

}
