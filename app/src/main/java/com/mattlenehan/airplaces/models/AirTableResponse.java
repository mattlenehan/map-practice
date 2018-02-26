package com.mattlenehan.airplaces.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirTableResponse {

  @SerializedName("records")
  public List<AirTableRecord> records;
}
