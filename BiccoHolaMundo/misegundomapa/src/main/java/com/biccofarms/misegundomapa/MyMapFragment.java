package com.biccofarms.misegundomapa;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by julian on 10/23/15.
 */
public class MyMapFragment extends SupportMapFragment implements
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    int markerCount = 0;
    Activity myParentActivity;
    Communicator myParentInterface;
    GoogleMap myMap;
    Marker myPosition;
    LatLng biccoLocation = new LatLng(4.81442576, -74.32188749);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myParentActivity = activity;
        try {
            myParentInterface = (Communicator) activity;
        } catch (ClassCastException e) {
            Log.e("mapas-bicco", "La clase papá no implementa el Comunicador");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        myMap = this.getMap();

        myMap.setOnMapLongClickListener(this);
        myMap.setOnMarkerDragListener(this);
        //myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        return rootView;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Marker newMarker = myMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Marker " + (markerCount++))
                .snippet(latLng.toString()));
        myParentInterface.newMarkerCreated(newMarker);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        myParentInterface.dragEnd();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        myParentInterface.dragEnd();
    }

    public void updateMyLocation(Location location) {
        if (myPosition == null) {
            myPosition = myMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .title("YO"));
        } else{
            myPosition.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }
        if(myPosition.isVisible()) myPosition.showInfoWindow();
    }
}
