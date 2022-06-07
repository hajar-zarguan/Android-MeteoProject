package com.example.meteo;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivityHome extends AppCompatActivity implements OnMapReadyCallback {

        private GoogleMap mMap;
        String value ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mapshome);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            // Add a marker in Sydney and move the camera
            //LatLng sydney = new LatLng(-34, 151);






            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                final GoogleMap gmap=mMap;
                @Override
                public void onMapClick(LatLng latLng) {
                    // LatLng loc = new LatLng(lat, lon);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in "+ value));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    gmap.addMarker(new MarkerOptions().position(latLng));
                    Toast.makeText(MapsActivityHome.this, latLng.latitude+"-"
                            +latLng.longitude, Toast.LENGTH_SHORT).show();


                    Geocoder gcd = new Geocoder(MapsActivityHome.this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(latLng.longitude, latLng.latitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses.size() > 0) {
                        System.out.println(addresses.get(0).getLocality());
                    }
                    else {
                        // do your stuff
                    }

                    Intent myIntent = new Intent(MapsActivityHome.this, MainActivityHome.class);

                    String longitude = String.valueOf(latLng.longitude);
                    String latitude = String.valueOf(latLng.latitude);
                    System.out.println(latLng.longitude);
                    System.out.println(latLng.latitude);
                    myIntent.putExtra("longitude",  longitude);
                    myIntent.putExtra("latitude",  latitude);
                    MapsActivityHome.this.startActivity(myIntent);




                }
            });

        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.map_options, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Change the map type based on the user's selection.
            switch (item.getItemId()) {
                case R.id.normal_map:
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    return true;
                case R.id.hybrid_map:
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    return true;
                case R.id.satellite_map:
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    return true;
                case R.id.terrain_map:
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }
