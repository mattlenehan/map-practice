package com.mattlenehan.airplaces.models;

import com.google.gson.annotations.SerializedName;

public class AirTableRecord {

  @SerializedName("id")
  public String id;

  @SerializedName("fields")
  public Place field;

  @SerializedName("createdTime")
  public String createdTime;
}
