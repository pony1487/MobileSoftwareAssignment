package com.example.ronan.assignment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class EventMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Intent MyTournamentsActivityIntent;
    private String eventAddress = "DEFAULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Get the event name when user clicks view location
        MyTournamentsActivityIntent = getIntent();
        eventAddress = MyTournamentsActivityIntent.getStringExtra("event_location");
    }//end onCreate


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        double latitude = 0;
        double longitude = 0;

        try {
            addresses = geocoder.getFromLocationName(eventAddress, 1);
            latitude= addresses.get(0).getLatitude();
            longitude= addresses.get(0).getLongitude();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        // Add a marker in Sydney and move the camera
        LatLng eventLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(eventLocation).title("Your Event"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLocation));
    }//end onMapReady

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }
}//end Class
