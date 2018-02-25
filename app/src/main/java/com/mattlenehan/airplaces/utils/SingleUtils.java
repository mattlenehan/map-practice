package com.mattlenehan.airplaces.utils;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.exceptions.Exceptions;


public class SingleUtils {
  public static <T> Single<T> fromCallable(Callable<T> callable) {
    return Single.create(subscriber -> {
      try {
        subscriber.onSuccess(callable.call());
      } catch (Throwable e) {
        Exceptions.throwIfFatal(e);
        subscriber.onError(e);
      }
    });
  }

  public static Single<Void> fromRunnable(Runnable runnable) {
    return Single.create(subscriber -> {
      try {
        runnable.run();
        subscriber.onSuccess(null);
      } catch (Throwable e) {
        Exceptions.throwIfFatal(e);
        subscriber.onError(e);
      }
    });
  }
}