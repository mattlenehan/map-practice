package com.mattlenehan.airplaces.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattlenehan on 2/25/18.
 */

public class AirTableRecord {

  @SerializedName("id")
  public String id;

  @SerializedName("fields")
  public Place field;

  @SerializedName("createdTime")
  public String createdTime;
}
