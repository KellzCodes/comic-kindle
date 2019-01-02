package com.kelldavis.comickindle.modules;

import android.content.ContentResolver;
import android.content.Context;
import com.kelldavis.comickindle.helper.ComicLocalDataHelper;
import com.kelldavis.comickindle.scope.LocalDataScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicLocalDataModule {

  @Provides
  @LocalDataScope
  ComicLocalDataHelper provideComicLocalDataHelper(ContentResolver resolver) {
    return new ComicLocalDataHelper(resolver);
  }

  @Provides
  @LocalDataScope
  ContentResolver provideContentResolver(Context context) {
    return context.getContentResolver();
  }
}
