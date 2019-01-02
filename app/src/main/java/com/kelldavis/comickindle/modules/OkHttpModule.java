package com.kelldavis.comickindle.modules;

import android.support.annotation.NonNull;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.kelldavis.comickindle.scope.RemoteDataScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@SuppressWarnings("WeakerAccess")
@Module
public class OkHttpModule {

  @Provides
  @NonNull
  @RemoteDataScope
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
        .addNetworkInterceptor(new StethoInterceptor())
        .build();
  }
}
