package com.biccofarms.api;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyFragment extends Fragment implements
        JSONParser.ParserInterface {

    private Activity myParentActivity;

    static enum DataTypes {
        CITY_NAME, WEATHER, TEMPERATURE, PRESSURE, HUMIDITY
    }

    JSONParser jsonParser;
    XMLParser xmlParser;
    EditText queryET;
    TextView cityTV, weatherTV, temperatureTV, pressureTV, humidityTV, loggerTV;

    public MyFragment() {
        jsonParser = new JSONParser(this);
        xmlParser = new XMLParser(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myParentActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        queryET = (EditText) rootView.findViewById(R.id.query);
        cityTV = (TextView) rootView.findViewById(R.id.city_value);
        weatherTV = (TextView) rootView.findViewById(R.id.weather_value);
        temperatureTV = (TextView) rootView.findViewById(R.id.temperature_value);
        pressureTV = (TextView) rootView.findViewById(R.id.pressure_value);
        humidityTV = (TextView) rootView.findViewById(R.id.humidity_value);
        loggerTV = (TextView) rootView.findViewById(R.id.logger_tv);
        return rootView;
    }

    public void startQuery() {
        String query = queryET.getText().toString();
        if (query.isEmpty()) return;
        query.trim();
        query = Uri.encode(query);
        RadioGroup rg = (RadioGroup) myParentActivity.findViewById(R.id.mode_selection);
        switch (rg.getCheckedRadioButtonId()){
            case R.id.json_mode_radio:
                jsonParser.setQuery(query);
                jsonParser.startQuery();
                break;
            case R.id.xml_mode_radio:
                xmlParser.setQuery(query);
                xmlParser.startQuery();
                break;
        }
    }

    @Override
    public void shareValue(DataTypes dataType, Object value) {
        switch (dataType){
            case CITY_NAME:
                cityTV.setText(value.toString());
                break;
            case WEATHER:
                weatherTV.setText(value.toString());
                break;
            case TEMPERATURE:
                temperatureTV.setText(value.toString());
                break;
            case PRESSURE:
                pressureTV.setText(value.toString());
                break;
            case HUMIDITY:
                humidityTV.setText(value.toString());
                break;
        }
    }

    @Override
    public void sendToLog(String value) {
        loggerTV.setText(loggerTV.getText().toString() + value + "\n");
    }
}
