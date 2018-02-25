package com.mattlenehan.airplaces.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mattlenehan.airplaces.AirPlacesApplication;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class AirPlacesModule {
  private AirPlacesApplication mApplication;
  private OkHttpClient mClient = createClient();
  private Gson mGson= createGson();

  public AirPlacesModule(AirPlacesApplication application) {
    mApplication = application;
  }

  @Provides
  public Context getContext() {
    return mApplication;
  }

  @Provides
  public OkHttpClient getClient() {
    return mClient;
  }

  @Provides
  public Gson getGson() {
    return mGson;
  }

  @Provides
  public SharedPreferences getSharedPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(mApplication);
  }

  private static OkHttpClient createClient() {
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
        .followRedirects(true)
        .followSslRedirects(true)
        .retryOnConnectionFailure(true)
        .cache(null)
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS);
    OkHttpClient client = clientBuilder.build();
    return client;
  }

  private static Gson createGson() {
    return new GsonBuilder().create();
  }
}
