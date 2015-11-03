package com.biccofarms.api;

/**
 * Created by julian on 11/3/15.
 */
public abstract class WeatherParser implements APIReader.APIReaderInterface  {

    public interface ParserInterface {
        void shareValue(MyFragment.DataTypes dataType, Object value);
        void sendToLog(String value);
    }

    static final String JSON_MODE = "json";
    static final String XML_MODE = "xml";
    static final String URL_BASE = "http://api.openweathermap.org/data/2.5/weather?";
    static final String API_KEY = "261db4f329734dfce4709303a4a0574e";

    ParserInterface myParentInterface;
    String query;
    String myMode;

    public WeatherParser(ParserInterface i) {
        myParentInterface = i;
        myMode = JSON_MODE;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void startQuery(){
        APIReader reader = new APIReader(this);
        reader.execute(getUrl());
    }

    @Override
    public void returnWebResponse(Object webResponse) throws Exception {
        throw new Exception("El método returnWebResponse() debe implementarse en el Parser hijo.");
    }

    public String getUrl() {
        return URL_BASE + "q=" + query + "&mode=" + myMode + "&appid=" + API_KEY;
    }

    @Override
    public void sendToLog(String value) {
        myParentInterface.sendToLog(value);
    }

    @Override
    public String getMode() {
        return myMode;
    }
}
