package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.fragment.CharacterFragment;
import com.kelldavis.comickindle.presenter.CharacterPresenter;
import com.kelldavis.comickindle.scope.CharacterScope;

import dagger.Subcomponent;

@CharacterScope
@Subcomponent
public interface CharacterComponent {

  CharacterPresenter presenter();

  void inject(CharacterFragment fragment);
}
