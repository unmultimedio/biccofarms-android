package com.biccofarms.misegundomapa;


import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarkersList extends Fragment {

    List<Marker> markers;
    MyOwnAdapter adapter;
    ListView positionsList;

    Activity myParentActivity;
    Communicator myParentInterface;

    TextView coordinatesText;

    public MarkersList() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myParentActivity = activity;
        try {
            myParentInterface = (Communicator) activity;
        } catch (ClassCastException e) {
            Log.e("bicco", "La actividad " +
                    activity.getClass().getCanonicalName() +
                    " no implementa la interfaz " +
                    Communicator.class.getCanonicalName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_markers_list, container, false);
        positionsList = (ListView) rootView.findViewById(R.id.markers_list);
        markers = new ArrayList<>();
        adapter = new MyOwnAdapter(getActivity(),
                R.layout.row_marker,
                markers);
        positionsList.setAdapter(adapter);
        coordinatesText = (TextView) rootView.findViewById(R.id.coordinates_text);
        return rootView;
    }


    public void newMarkerInList(Marker newMarker) {
        markers.add(newMarker);
        adapter.notifyDataSetChanged();
    }

    public void update() {
        adapter.notifyDataSetChanged();
    }

    public void startLocationUpdates() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager)
                myParentActivity.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        //*
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        /*/
        Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setCostAllowed(true);

        locationManager.requestLocationUpdates(0, 0, criteria, locationListener, null);
        // */

    }

    private void makeUseOfNewLocation(Location location) {
        coordinatesText.setText(location.toString());
        myParentInterface.shareMyLocation(location);
    }

    public class MyOwnAdapter extends ArrayAdapter {

        Context context;
        int resource;
        List<Marker> markers;

        public MyOwnAdapter(Context context, int resource, List markers) {
            super(context, resource, markers);
            this.context = context;
            this.resource = resource;
            this.markers = markers;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowMarker = li.inflate(resource, parent, false);
            TextView titleTV = (TextView) rowMarker.findViewById(R.id.title);
            TextView positionTV = (TextView) rowMarker.findViewById(R.id.position);
            titleTV.setText(markers.get(position).getTitle());
            positionTV.setText(markers.get(position).getPosition().toString());
            return rowMarker;
        }
    }
}
