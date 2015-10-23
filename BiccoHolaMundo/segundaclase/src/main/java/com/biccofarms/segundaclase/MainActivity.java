package com.biccofarms.segundaclase;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

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

    public void sendInfo(View view){
        EditText textField = (EditText) findViewById(R.id.userText);
        String userText = textField.getText().toString();
        if(userText.isEmpty()){
            Toast.makeText(this,
                    getResources().getString(R.string.empty_text_alert),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int color;
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        switch (rg.getCheckedRadioButtonId()){
            case -1:
                Toast.makeText(this,
                        getResources().getString(R.string.empty_color_alert),
                        Toast.LENGTH_SHORT).show();
                return;
            case R.id.yellowOption:
                color = getResources().getColor(R.color.yellow);
                break;
            case R.id.blueOption:
                color = getResources().getColor(R.color.blue);
                break;
            case R.id.redOption:
                color = getResources().getColor(R.color.red);
                break;
            default:
                color = getResources().getColor(R.color.black);
        }

        Intent call = new Intent(this, ResponseActivity.class);
        call.putExtra("text", userText);
        call.putExtra("color", color);
        startActivity(call);
    }
}
