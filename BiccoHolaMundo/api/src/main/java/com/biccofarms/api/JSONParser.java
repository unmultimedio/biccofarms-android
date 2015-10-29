package com.biccofarms.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by julian on 10/29/15.
 */
public class JSONParser implements APIReader.APIReaderInterface {

    public interface ParserInterface {
        void shareValue(MyFragment.DataTypes dataType, Object value);
        void sendToLog(String value);
    }

    static final String URL_BASE = "http://api.openweathermap.org/data/2.5/weather?";
    static final String API_KEY = "261db4f329734dfce4709303a4a0574e";

    ParserInterface myParentInterface;
    String query;

    public JSONParser(ParserInterface i) {
        myParentInterface = i;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String getUrl(){
        return URL_BASE + "q=" + query + "&appid=" + API_KEY;
    }

    public void startQuery(){
        APIReader reader = new APIReader(this);
        reader.execute(getUrl());
    }

    @Override
    public void returnWebResponse(String webResponse) {
        try {
            JSONObject json = new JSONObject(webResponse);

            String name = json.getString("name");
            sendToLog("Leyendo el nombre de la ciudad...");
            String weather = json.getJSONArray("weather").getJSONObject(0).getString("main");
            sendToLog("Leyendo el clima de la ciudad...");
            JSONObject main = json.getJSONObject("main");
            Double temperature = main.getDouble("temp");
            sendToLog("Leyendo la temperatura de la ciudad...");
            Double pressure = main.getDouble("pressure");
            sendToLog("Leyendo la presión atmosférica de la ciudad...");
            Double humidity = main.getDouble("humidity");
            sendToLog("Leyendo la humedad de la ciudad...");

            if(name != null && !name.isEmpty())
                myParentInterface.shareValue(
                        MyFragment.DataTypes.CITY_NAME,
                        name);

            if(weather != null && !name.isEmpty())
                myParentInterface.shareValue(
                        MyFragment.DataTypes.WEATHER,
                        weather);

            if(temperature != null && !temperature.isNaN())
                myParentInterface.shareValue(
                        MyFragment.DataTypes.TEMPERATURE,
                        temperature);

            if(pressure != null && !pressure.isNaN())
                myParentInterface.shareValue(
                        MyFragment.DataTypes.PRESSURE,
                        pressure);

            if(humidity != null && !humidity.isNaN())
                myParentInterface.shareValue(
                        MyFragment.DataTypes.HUMIDITY,
                        humidity);

        } catch (JSONException e) {
            Log.e("bicco", "El String retornado no es un Objeto JSON");
        }
    }

    @Override
    public void sendToLog(String value) {
        myParentInterface.sendToLog(value);
    }
}
