package com.biccofarms.api;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by julian on 11/3/15.
 */
public class XMLParser extends WeatherParser {

    public XMLParser(ParserInterface i) {
        super(i);
        myMode = XML_MODE;
    }

    @Override
    public void returnWebResponse(final Object webResponse) throws Exception {

        final int amountOfValues = MyFragment.DataTypes.values().length;

        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                int readValues = 0;
                try {
                    InputStream inputStream = (InputStream) webResponse;

                    XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();

                    xmlPullParser.setInput(inputStream, "UTF-8");

                    while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT && readValues < amountOfValues){
                        //myParentInterface.sendToLog("Leyendo: "+xmlPullParser.getName());
                        if(xmlPullParser.getEventType() == XmlPullParser.START_TAG){
                            switch (xmlPullParser.getName()){
                                case "city":
                                    String cityValue = xmlPullParser.getAttributeValue(null, "name");
                                    myParentInterface.shareValue(
                                            MyFragment.DataTypes.CITY_NAME,
                                            cityValue);
                                    readValues++;
                                    break;
                                case "temperature":
                                    String temperatureValue = xmlPullParser.getAttributeValue(null, "value");
                                    myParentInterface.shareValue(
                                            MyFragment.DataTypes.TEMPERATURE,
                                            temperatureValue);
                                    readValues++;
                                    break;
                                case "humidity":
                                    String humidityValue = xmlPullParser.getAttributeValue(null, "value");
                                    myParentInterface.shareValue(
                                            MyFragment.DataTypes.HUMIDITY,
                                            humidityValue);
                                    readValues++;
                                    break;
                                case "pressure":
                                    String pressureValue = xmlPullParser.getAttributeValue(null, "value");
                                    myParentInterface.shareValue(
                                            MyFragment.DataTypes.PRESSURE,
                                            pressureValue);
                                    readValues++;
                                    break;
                                case "weather":
                                    String weatherValue = xmlPullParser.getAttributeValue(null, "value");
                                    myParentInterface.shareValue(
                                            MyFragment.DataTypes.WEATHER,
                                            weatherValue);
                                    readValues++;
                                    break;
                            }
                        }

                        try {
                            xmlPullParser.next();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }


            }
        });

        hilo.start();




    }
}
