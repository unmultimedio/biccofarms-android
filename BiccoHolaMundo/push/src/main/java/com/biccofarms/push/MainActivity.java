package com.biccofarms.push;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String KEY_REGID = MainActivity.class.getCanonicalName()+".regId";
    private static String SENDER_ID, SERVER_URL;

    EditText nameET, emailET, regIdET;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getPreferences(MODE_PRIVATE);
        SENDER_ID = getResources().getString(R.string.sender_id);
        SERVER_URL = getResources().getString(R.string.url_register);

        nameET = (EditText) findViewById(R.id.name_value);
        emailET = (EditText) findViewById(R.id.email_value);
        regIdET = (EditText) findViewById(R.id.regId_value);

        if(storedInPreferences()){
            fillRegId();
        }
    }

    private void fillRegId() {
        regIdET.setText(preferences.getString(KEY_REGID,
                getResources().getString(R.string.no_data)));
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

    public void registerForGCM(View view) {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();

        if(!name.isEmpty() && !email.isEmpty()){
            if(!storedInPreferences()){
                askToGCM(name, email);
            } else {
                fillRegId();
            }
        }
    }

    private void askToGCM(String nameStr, String emailStr) {
        AsyncTask<String, String, Void> gcmWorker = new AsyncTask<String, String, Void>() {

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                Log.d("bicco", values[0]);
                Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Void doInBackground(String... params) {

                GoogleCloudMessaging gcm = GoogleCloudMessaging
                        .getInstance(getApplicationContext());

                try {
                    String regId = gcm.register(SENDER_ID);
                    publishProgress("Reg Id recibido: \"" + regId + "\"");

                    // Guardar el regId en preferencias
                    if(saveInPreferences(regId)){
                        publishProgress("Preferencias guardadas exitosamente en local.");
                    }else{
                        publishProgress("Preferencias no pudieron ser guardadas.");
                    }

                    registerInMyServer(regId, params[0], params[1]);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                fillRegId();
            }

            private boolean registerInMyServer(String regId, String name, String email) {
                HttpClient client = new DefaultHttpClient();
                HttpPost postObj = new HttpPost(SERVER_URL);

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("name", name));
                params.add(new BasicNameValuePair("email", email));
                params.add(new BasicNameValuePair("regId", regId));

                try {
                    postObj.setEntity(new UrlEncodedFormEntity(params));

                    HttpResponse answer =  client.execute(postObj);
                    publishProgress("Conexión exitosa con el backend, cod(" +
                            answer.getStatusLine().getStatusCode() + ")");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    publishProgress("Error con los parámetros post");
                } catch (IOException e) {
                    e.printStackTrace();
                    publishProgress("Error con la conexión");
                }

                return false;
            }

        };

        gcmWorker.execute(nameStr, emailStr);
    }

    private boolean storedInPreferences() {
        return preferences.contains(KEY_REGID);
    }

    private boolean saveInPreferences(String regId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_REGID, regId);
        return editor.commit();
    }

    public void clearRegId(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_REGID);
        editor.commit();
        fillRegId();
    }
}
