package com.kelldavis.comickindle.modules;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kelldavis.comickindle.factory.ComicGsonAdapterFactory;
import com.kelldavis.comickindle.scope.RemoteDataScope;
import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

  @Provides
  @NonNull
  @RemoteDataScope
  Gson provideGson() {
    return new GsonBuilder()
        .registerTypeAdapterFactory(ComicGsonAdapterFactory.create())
        .create();
  }
}
