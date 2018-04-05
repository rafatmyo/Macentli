package com.macentli.rafat.macentli;


import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// https://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {

    MapView mapView;
    private GoogleMap googleMap;
    private final int REQUEST_LOCATION_CODE = 99;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map



                LatLng amalur = new LatLng(18.933748, -99.213990);
                googleMap.addMarker(new MarkerOptions().position(amalur).title("Amalur Tienda Organica").snippet("Amalur Tienda Organica"));

                LatLng shaya = new LatLng(18.950165,-99.231121);
                googleMap.addMarker(new MarkerOptions().position(shaya).title("Centro Naturista Shaya Michan").snippet("Centro Naturista Shaya Michan"));

                LatLng itzel = new LatLng(18.953065,-99.231346);
                googleMap.addMarker(new MarkerOptions().position(itzel).title("Farmacia Naturista Itzel").snippet("Farmacia Naturista Itzel"));

                LatLng galo = new LatLng(18.941885,-99.232548);
                googleMap.addMarker(new MarkerOptions().position(galo).title("Fruteria Los Galo").snippet("Fruteria Los Galo"));

                LatLng santa = new LatLng(18.952878, -99.219693);
                googleMap.addMarker(new MarkerOptions().position(santa).title("Santa Marina").snippet("Interior del Mercado de Lomas de Cortez"));

                LatLng plazac = new LatLng( 18.934231, -99.228822);
                googleMap.addMarker(new MarkerOptions().position(plazac).title("Plaza Cuernavaca").snippet("Local E7 Pasillo Arbolado"));

                LatLng gaia = new LatLng(18.961466,-99.246376);
                googleMap.addMarker(new MarkerOptions().position(gaia).title("Gaia").snippet("Junto a Farm de Similares"));

                LatLng mercadito = new LatLng(18.977204,-99.253416);
                googleMap.addMarker(new MarkerOptions().position(mercadito).title("Mercadito Organico").snippet("Mercadito Organico"));

                LatLng huerta = new LatLng(18.975128,-99.207410);
                googleMap.addMarker(new MarkerOptions().position(huerta).title("Huerta de Lucia").snippet("Huerta de Lucia"));


                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(shaya).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;

    }

    public boolean checkLocationPermission() {

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        } else {
            return true;
        }
    }

}
