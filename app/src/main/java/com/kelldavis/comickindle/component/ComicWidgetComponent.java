package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.factory.ComicWidgetFactory;
import com.kelldavis.comickindle.modules.ComicLocalDataModule;
import com.kelldavis.comickindle.scope.LocalDataScope;

import dagger.Subcomponent;

@LocalDataScope
@Subcomponent(modules = {ComicLocalDataModule.class})
public interface ComicWidgetComponent {

    void inject(ComicWidgetFactory factory);
}
