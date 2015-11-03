package com.biccofarms.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by julian on 10/29/15.
 */
public class JSONParser extends WeatherParser {

    public JSONParser(ParserInterface i) {
        super(i);
        myMode = JSON_MODE;
    }

    @Override
    public void returnWebResponse(Object webResponse) {
        try {
            JSONObject json = new JSONObject(webResponse.toString());

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
}
