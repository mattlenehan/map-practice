package com.mattlenehan.airplaces.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Place implements Parcelable {

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

  public Place(Parcel parcel) {
    this.name = parcel.readString();
    this.address = parcel.readString();
    this.coordinates = parcel.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(name);
    parcel.writeString(address);
    parcel.writeString(coordinates);
  }

  public static final Parcelable.Creator<Place> CREATOR =
      new Parcelable.Creator<Place>() {

        @Override
        public Place createFromParcel(Parcel parcel) {
          return new Place(parcel);
        }

        @Override
        public Place[] newArray(int i) {
          return new Place[i];
        }
      };
}
