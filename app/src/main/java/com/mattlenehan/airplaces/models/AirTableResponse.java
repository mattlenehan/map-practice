package com.mattlenehan.airplaces.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mattlenehan on 2/25/18.
 */

public class AirTableResponse {

  @SerializedName("records")
  public List<AirTableRecord> records;
}
