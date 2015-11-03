package com.biccofarms.api;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by julian on 10/29/15.
 */
public class APIReader extends AsyncTask<String, String, Object> {

    APIReaderInterface myParentInterface;

    public APIReader(APIReaderInterface i){
        myParentInterface = i;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(String... strings) {
        String urlString = strings[0];

        try {
            URL url = new URL(urlString);
            publishProgress("URL detectada...");

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(8000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.connect();
            publishProgress("Estamos conectados a la URL...");

            InputStream inputStream = connection.getInputStream();
            if(myParentInterface.getMode().equals(WeatherParser.XML_MODE)){
                return inputStream;
            }

            Scanner scanner = new Scanner(inputStream);
            publishProgress("Tenemos el stream, leyendo datos...");

            String webResponse = scanner.useDelimiter("\\A").next();
            publishProgress("Respuesta leída, entregando...");

            return webResponse;

        } catch (MalformedURLException e) {
            Log.e("bicco", "La URL está mal formada");
        } catch (IOException e) {
            Log.e("bicco", "Problemas de conexión");
            publishProgress("Tenemos problemas de conexión");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object webResponse) {
        super.onPostExecute(webResponse);
        if(webResponse != null) {
            try {
                myParentInterface.returnWebResponse(webResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d("bicco-log", values[0]);
        myParentInterface.sendToLog(values[0]);
    }

    public interface APIReaderInterface {
        void returnWebResponse(Object webResponse) throws Exception;
        void sendToLog(String value);
        String getMode();
    }
}
