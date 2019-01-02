package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.fragment.VolumeFragment;
import com.kelldavis.comickindle.presenter.VolumePresenter;
import com.kelldavis.comickindle.scope.VolumeScope;

import dagger.Subcomponent;

@VolumeScope
@Subcomponent
public interface VolumeComponent {

  VolumePresenter presenter();

  void inject(VolumeFragment fragment);
}
