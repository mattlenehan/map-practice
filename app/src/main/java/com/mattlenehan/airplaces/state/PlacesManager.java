package com.mattlenehan.airplaces.state;

import com.mattlenehan.airplaces.models.Place;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlacesManager {
  private List<Place> mPlaces = new ArrayList<>();

  @Inject
  public PlacesManager() {
  }

  public void setPlaces(List<Place> places) {
    mPlaces = places;
  }

  public List<Place> getPlaces() {
    return mPlaces;
  }

  public boolean hasPlace(long placeId) {
    for (Place p : mPlaces) {
//      if (p. == placeId) {
//        return true;
//      }
    }
    return false;
  }
}
