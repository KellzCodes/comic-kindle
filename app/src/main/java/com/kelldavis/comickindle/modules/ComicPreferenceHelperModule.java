package com.kelldavis.comickindle.modules;

import android.content.Context;

import com.kelldavis.comickindle.helper.ComicPreferenceHelper;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ComicPreferenceHelperModule {

  @Provides
  @Singleton
  ComicPreferenceHelper providePreferencesHelper(Context context) {
    return new ComicPreferenceHelper(context);
  }
}
