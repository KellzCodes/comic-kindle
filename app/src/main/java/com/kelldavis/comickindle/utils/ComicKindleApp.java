package com.kelldavis.comickindle.utils;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class ComicKindleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }

        LeakCanary.install(this);

        if(BuildConfig.DEBUG){
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());

            Stetho.initializeWithDefaults(this);
        }
    }
}
