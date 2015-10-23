package com.biccofarms.misegundomapa;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by julian on 10/23/15.
 */
public interface Communicator {
    void newMarkerCreated(Marker marker);

    void dragEnd();
}
