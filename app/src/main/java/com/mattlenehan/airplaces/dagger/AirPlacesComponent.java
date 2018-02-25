package com.mattlenehan.airplaces.dagger;

import com.mattlenehan.airplaces.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AirPlacesModule.class)
public interface AirPlacesComponent {
  void inject(MainActivity activity);
}
