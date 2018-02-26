package com.mattlenehan.airplaces.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mattlenehan.airplaces.AirPlacesApplication;
import com.mattlenehan.airplaces.R;
import com.mattlenehan.airplaces.models.Place;

import java.util.List;

import javax.inject.Inject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  @Inject
  Gson mGson;

  private GoogleMap mMap;
  private List<Place> mPlaces;
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

    if (getIntent().hasExtra("places")) {
      mPlaces = getIntent().getParcelableArrayListExtra("places");
    } else {
      mPlaceName = getIntent().getStringExtra("placeName");
      mPlaceAddress = getIntent().getStringExtra("placeAddress");
      mPlaceCoordinates = getIntent().getStringExtra("placeCoordinates");
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    if (mPlaces == null) {
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
    } else {
      LatLngBounds.Builder builder = new LatLngBounds.Builder();
      for (Place p : mPlaces) {
        String[] coordinates = p.coordinates.split(",");
        if (coordinates.length != 2) {
          continue;
        }
        LatLng location = new LatLng(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
        builder.include(location);
        mMap.addMarker(new MarkerOptions().position(location).title(String.format("Marker in %s", p.name)));
      }

      LatLngBounds bounds = builder.build();
      int width = getResources().getDisplayMetrics().widthPixels;
      int height = getResources().getDisplayMetrics().heightPixels;
      int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
      CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
      mMap.animateCamera(cu);
    }
  }

  @Override
  public void onBackPressed(){
    super.onBackPressed();
    overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
  }
}
