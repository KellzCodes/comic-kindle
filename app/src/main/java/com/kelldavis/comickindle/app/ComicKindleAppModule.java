package com.kelldavis.comickindle.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicKindleAppModule {

    @NonNull
    private final ComicKindleApp comicKindleApp;

    ComicKindleAppModule(@NonNull ComicKindleApp comicKindleApp) {
        this.comicKindleApp = comicKindleApp;
    }

    @Provides
    @NonNull
    @Singleton
    Context provideContext() {
        return comicKindleApp;
    }

    @Provides
    @NonNull
    @Singleton
    FirebaseAnalytics provideFirebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }
}

