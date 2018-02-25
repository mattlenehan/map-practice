package com.mattlenehan.airplaces;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.mattlenehan.airplaces.dagger.AirPlacesComponent;
import com.mattlenehan.airplaces.dagger.AirPlacesModule;
import com.mattlenehan.airplaces.dagger.DaggerAirPlacesComponent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mattlenehan on 2/24/18.
 */

public class AirPlacesApplication extends Application {

  private static AirPlacesApplication mApp;
  private Gson mGson;

  private static final String TAG = AirPlacesApplication.class.getSimpleName();

  private AirPlacesComponent mComponent = DaggerAirPlacesComponent.builder()
      .airPlacesModule(new AirPlacesModule(this))
      .build();

  @Override
  public void onCreate() {
    super.onCreate();

    mApp = this;
  }

  public static AirPlacesApplication get() {
    return mApp;
  }

  public AirPlacesComponent getComponent() {
    return mComponent;
  }

  /**
   * @return Application's version code from the {@code PackageManager}.
   */
  public int getAppVersion() {
    try {
      PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      return packageInfo.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      // should never happen
      throw new RuntimeException("Could not get package name: " + e);
    }
  }

  public final String getAppVersionName() {
    try {
      PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      return pInfo.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return "";
    }
  }

  private Gson getGson() {
    if (mGson == null) {
      mGson = new Gson();
    }

    return mGson;
  }
}
