package com.mattlenehan.airplaces.models;

import com.google.gson.annotations.SerializedName;


/**
 * Created by mattlenehan on 2/25/18.
 */

public class Place {

  @SerializedName("Name")
  public String name;

  @SerializedName("Address")
  public String address;

  @SerializedName("Coordinates")
  public String coordinates;


  public Place(String name, String address, String coordinates) {
    this.name = name;
    this.address = address;
    this.coordinates = coordinates;
  }
}
