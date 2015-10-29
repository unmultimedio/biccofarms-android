package com.biccofarms.misegundomapa;

import android.location.Location;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by julian on 10/23/15.
 */
public interface Communicator {
    void startLocationUpdates(View view);

    void newMarkerCreated(Marker marker);

    void dragEnd();

    void shareMyLocation(Location location);
}
