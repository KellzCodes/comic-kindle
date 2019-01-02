package com.kelldavis.comickindle.app;

import com.kelldavis.comickindle.component.ComicDbHelperComponent;

import com.kelldavis.comickindle.component.ComicRemoteDataComponent;
import com.kelldavis.comickindle.component.ComicWidgetComponent;
import com.kelldavis.comickindle.modules.ComicDbHelperModule;
import com.kelldavis.comickindle.modules.ComicPreferenceHelperModule;
import com.kelldavis.comickindle.modules.ComicRemoteDataModule;
import com.kelldavis.comickindle.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ComicKindleAppModule.class, ComicPreferenceHelperModule.class})
public interface ComicKindleAppComponent {

    ComicDbHelperComponent plusDbHelperComponent(ComicDbHelperModule module);

    ComicRemoteDataComponent plusRemoteComponent(ComicRemoteDataModule module);

    ComicWidgetComponent plusWidgetComponent();

    void inject(MainActivity activity);
}
