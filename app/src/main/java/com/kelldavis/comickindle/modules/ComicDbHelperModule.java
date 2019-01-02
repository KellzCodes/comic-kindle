package com.kelldavis.comickindle.modules;

import android.content.Context;

import com.kelldavis.comickindle.helper.ComicDbHelper;
import com.kelldavis.comickindle.scope.LocalDataScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicDbHelperModule {

  @Provides
  @LocalDataScope
  ComicDbHelper provideComicDbHelper(Context context) {
    return new ComicDbHelper(context);
  }
}
