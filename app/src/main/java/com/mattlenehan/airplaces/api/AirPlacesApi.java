package com.mattlenehan.airplaces.api;

import com.google.gson.Gson;
import com.mattlenehan.airplaces.models.AirTableResponse;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    return getResponse(mClient.newCall(request))
        .map(response -> {
          try {
            AirTableResponse airTableResponse = mGson.fromJson(response.body().string(), AirTableResponse.class);
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
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
          subscriber.onSuccess(response);
        }
      });
    });
  }
}
