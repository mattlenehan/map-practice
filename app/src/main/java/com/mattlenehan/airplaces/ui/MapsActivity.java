package com.mattlenehan.airplaces.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mattlenehan.airplaces.AirPlacesApplication;
import com.mattlenehan.airplaces.R;

import javax.inject.Inject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  @Inject
  Gson mGson;

  private GoogleMap mMap;
  private String mPlaceName;
  private String mPlaceAddress;
  private String mPlaceCoordinates;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((AirPlacesApplication) getApplication()).getComponent().inject(this);
    setContentView(R.layout.activity_maps);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    mPlaceName = getIntent().getStringExtra("placeName");
    mPlaceAddress = getIntent().getStringExtra("placeAddress");
    mPlaceCoordinates = getIntent().getStringExtra("placeCoordinates");
  }


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
    String[] coordinates = mPlaceCoordinates.split(",");
    if (coordinates.length != 2) {
      return;
    }
    LatLng location = new LatLng(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    mMap.addMarker(new MarkerOptions().position(location).title(String.format("Marker in %s", mPlaceName)));

    CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(location)
        .zoom(17)
        .tilt(70)
        .build();
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
  }

  @Override
  public void onBackPressed(){
    super.onBackPressed();
    overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
  }
}
