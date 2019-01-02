package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.adapter.ComicSyncAdapter;
import com.kelldavis.comickindle.modules.ComicLocalDataModule;
import com.kelldavis.comickindle.scope.LocalDataScope;

import dagger.Subcomponent;

@LocalDataScope
@Subcomponent(modules = {ComicLocalDataModule.class})
public interface ComicLocalDataComponent {

    IssueComponent plusIssuesComponent();

    IssueDetailComponent plusIssueDetailsComponent();

    VolumeDetailComponent plusVolumeDetailsComponent();

    ComicCollectionComponent plusOwnedIssuesComponent();

    void inject(ComicSyncAdapter adapter);
}
