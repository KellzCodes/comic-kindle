package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.modules.ComicDbHelperModule;
import com.kelldavis.comickindle.provider.ComicProvider;
import com.kelldavis.comickindle.scope.LocalDataScope;

import dagger.Subcomponent;

@LocalDataScope
@Subcomponent(modules = {ComicDbHelperModule.class})
public interface ComicDbHelperComponent {
    void inject(ComicProvider provider);
}

