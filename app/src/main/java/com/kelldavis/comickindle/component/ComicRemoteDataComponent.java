package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.modules.ComicLocalDataModule;
import com.kelldavis.comickindle.modules.ComicRemoteDataModule;
import com.kelldavis.comickindle.scope.RemoteDataScope;

import dagger.Subcomponent;

/**
 * Remote data component. Provides remote data helper injection, depends on App component.
 */

@RemoteDataScope
@Subcomponent(modules = {ComicRemoteDataModule.class})
public interface ComicRemoteDataComponent {

  ComicLocalDataComponent plusLocalComponent(ComicLocalDataModule module);

  VolumeComponent plusVolumesComponent();

  CharacterComponent plusCharactersComponent();

  CharacterDetailComponent plusCharacterDetailsComponent();
}
