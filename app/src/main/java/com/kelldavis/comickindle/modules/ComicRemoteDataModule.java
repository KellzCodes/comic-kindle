package com.kelldavis.comickindle.modules;

import com.kelldavis.comickindle.helper.ComicRemoteDataHelper;
import com.kelldavis.comickindle.scope.RemoteDataScope;
import com.kelldavis.comickindle.service.ComicVineService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {GsonModule.class, OkHttpModule.class, RetrofitModule.class})
public class ComicRemoteDataModule {

  @Provides
  @RemoteDataScope
  ComicVineService provideComicVineService(Retrofit retrofit) {
    return retrofit.create(ComicVineService.class);
  }

  @Provides
  @RemoteDataScope
  ComicRemoteDataHelper provideComicRemoteDataHelper(ComicVineService service) {
    return new ComicRemoteDataHelper(service);
  }
}
