package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.fragment.CharacterDetailFragment;
import com.kelldavis.comickindle.presenter.CharacterDetailPresenter;
import com.kelldavis.comickindle.scope.CharacterDetailScope;

import dagger.Subcomponent;

@CharacterDetailScope
@Subcomponent
public interface CharacterDetailComponent {

  CharacterDetailPresenter presenter();

  void inject(CharacterDetailFragment fragment);
}
