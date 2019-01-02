package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.fragment.VolumeDetailFragment;
import com.kelldavis.comickindle.presenter.VolumeDetailPresenter;
import com.kelldavis.comickindle.scope.VolumeDetailScope;

import dagger.Subcomponent;

@VolumeDetailScope
@Subcomponent
public interface VolumeDetailComponent {

  VolumeDetailPresenter presenter();

  void inject(VolumeDetailFragment fragment);
}
