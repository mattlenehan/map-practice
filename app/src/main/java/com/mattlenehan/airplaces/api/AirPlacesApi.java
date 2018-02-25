package com.mattlenehan.airplaces.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mattlenehan.airplaces.models.AirTableResponse;
import com.mattlenehan.airplaces.models.Place;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by mattlenehan on 2/25/18.
 */
@Singleton
public class AirPlacesApi {

  private OkHttpClient mClient;
  private Gson mGson;

  @Inject
  public AirPlacesApi(OkHttpClient client, Gson gson) {
    mClient = client;
    mGson = gson;
  }

  public Single<AirTableResponse> getPlaces() {
    Request request = new Request.Builder()
        .get()
        .url(getBaseUrl().newBuilder()
            .encodedPath("/v0/appwNLbaejQ44oIiH/Places")
            .addEncodedQueryParameter("api_key", "keysGULlSX5oPYIUK")
            .build()
            .url())
        .build();

    Log.e("MATT","[url] " + mGson.toJson(request.url()));
    return getResponse(mClient.newCall(request))
        .map(response -> {
//          Log.e("MATT","[response] " + response.body().string());
          try {
            AirTableResponse airTableResponse = mGson.fromJson(response.body().string(), AirTableResponse.class);
            Log.e("MATT","here");
            return airTableResponse;
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  private HttpUrl getBaseUrl() {
    return HttpUrl.parse("http://api.airtable.com");
  }

  private Single<Response> getResponse(Call call) {
    return Single.create(subscriber -> {
      call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
//          subscriber.onError(new RequestException(call.request(), e));
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
          if (!response.isSuccessful()) {
            try {

            } catch (Exception e) {
            }
            return;
          }

          subscriber.onSuccess(response);
        }
      });
    });
  }
}
