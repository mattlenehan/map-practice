package com.mattlenehan.airplaces.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mattlenehan.airplaces.AirPlacesApplication;
import com.mattlenehan.airplaces.R;
import com.mattlenehan.airplaces.api.AirPlacesApi;
import com.mattlenehan.airplaces.models.AirTableRecord;
import com.mattlenehan.airplaces.models.Place;
import com.mattlenehan.airplaces.state.PlacesManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private LinearLayoutManager mLayoutManager;
  private AirPlacesListAdapter mAdapter;
  private boolean mScrollable;
  private List<Place> mPlaces = new ArrayList<>();
  protected Context mContext;

  @Inject
  PlacesManager mPlacesManager;
  @BindView(R.id.places)
  RecyclerView mPlacesView;

  @BindView(R.id.place_list_pull_to_refresh_container)
  SwipeRefreshLayout mP2RefreshContainer;
  @BindView(R.id.progress)
  ProgressBar mProgress;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @Inject
  AirPlacesApi mAirPlacesApi;

  @Inject
  Gson mGson;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((AirPlacesApplication) getApplication()).getComponent().inject(this);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mContext = this;
    mLayoutManager = new LinearLayoutManager(this);
    mAdapter = new AirPlacesListAdapter();

    mPlacesView.setLayoutManager(mLayoutManager);
    mPlacesView.setAdapter(mAdapter);
    mPlacesView.setVisibility(View.GONE);

    mScrollable = false;

    configureActionBar();

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });


    mP2RefreshContainer.setOnRefreshListener(() -> {
      mP2RefreshContainer.setRefreshing(true);
      try {
        getPlaces();
      } catch (Exception e) {

      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();
    getPlaces();
  }

  public void configureActionBar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  private void getPlaces() {
    showLoader();

    mAirPlacesApi.getPlaces()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(placesResponse -> {
          hideLoader();
          mP2RefreshContainer.setRefreshing(false);

          mPlaces.clear();
          for (AirTableRecord record : placesResponse.records) {
            mPlaces.add(record.field);
          }
          mPlacesManager.setPlaces(mPlaces);

          mPlacesView.setVisibility(View.VISIBLE);

          mAdapter.notifyDataSetChanged();
        }, error -> {
          mP2RefreshContainer.setRefreshing(false);
          hideLoader();
        });
  }

  private void showLoader() {
    mProgress.setVisibility(View.VISIBLE);
  }

  private void hideLoader() {
    mProgress.setVisibility(View.GONE);
  }

  private class AirPlacesListAdapter extends RecyclerView.Adapter<AirPlacesViewHolder> {

    @Override
    public int getItemViewType(int position) {
      return R.layout.place_item;
    }

    @Override
    public AirPlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      View view = inflater.inflate(R.layout.place_item, parent, false);
      return new AirPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AirPlacesViewHolder viewHolder, int position) {
      Place place = mPlacesManager.getPlaces().get(position);
      AirPlacesViewHolder placeViewHolder = viewHolder;
      placeViewHolder.mPlaceName.setText(place.name);
      placeViewHolder.mPlaceAddress.setText(place.address);
    }

    @Override
    public int getItemCount() {
      return mPlacesManager.getPlaces().size();
    }
  }

  static class AirPlacesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.place_name)
    TextView mPlaceName;

    @BindView(R.id.place_address)
    TextView mPlaceAddress;

    public AirPlacesViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
