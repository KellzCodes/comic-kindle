package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.fragment.ComicCollectionFragment;
import com.kelldavis.comickindle.presenter.ComicCollectionPresenter;
import com.kelldavis.comickindle.scope.ComicCollectionScope;

import dagger.Subcomponent;

@ComicCollectionScope
@Subcomponent
public interface ComicCollectionComponent {

  ComicCollectionPresenter presenter();

  void inject(ComicCollectionFragment fragment);
}