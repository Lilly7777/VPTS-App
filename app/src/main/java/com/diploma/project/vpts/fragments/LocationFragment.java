package com.diploma.project.vpts.fragments;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.diploma.project.vpts.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends Fragment implements LocationListener {

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    protected SupportMapFragment supportMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.location_fragment);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        supportMapFragment.getMapAsync(googleMap -> googleMap.setOnMapClickListener(latLng -> {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Custom marker");
            markerOptions.title(latLng.latitude + " : " + latLng.longitude);
            //googleMap.clear();
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

            googleMap.addMarker(markerOptions);
        }));
        return view;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(supportMapFragment != null){
            supportMapFragment.getMapAsync(googleMap -> {
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng currentCoords = new LatLng(location.getLatitude(), location.getLongitude());
                markerOptions.position(currentCoords);
                markerOptions.icon(getMarkerIcon("#222666"));
                markerOptions.title(currentCoords.latitude + " : " + currentCoords.longitude);
                //googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoords, 10));
                googleMap.addMarker(markerOptions);
            });
        }
    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}