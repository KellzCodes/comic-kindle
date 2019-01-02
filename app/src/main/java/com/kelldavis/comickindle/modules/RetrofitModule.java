package com.kelldavis.comickindle.modules;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.kelldavis.comickindle.scope.RemoteDataScope;
import com.kelldavis.comickindle.service.ComicVineService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("WeakerAccess")
@Module
public class RetrofitModule {

  @Provides
  @NonNull
  @RemoteDataScope
  Retrofit provideRetrofit(@NonNull OkHttpClient client, @NonNull Gson gson) {

    return new Retrofit.Builder()
        .baseUrl(ComicVineService.ENDPOINT)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
  }
}
