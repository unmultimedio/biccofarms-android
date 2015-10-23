package com.biccofarms.misegundomapa;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public MarkersList() {
        // Required empty public constructor
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
        return rootView;
    }


    public void newMarkerInList(Marker newMarker) {
        markers.add(newMarker);
        adapter.notifyDataSetChanged();
    }

    public void update() {
        adapter.notifyDataSetChanged();
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
