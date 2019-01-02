package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.fragment.IssueDetailFragment;
import com.kelldavis.comickindle.presenter.IssueDetailPresenter;
import com.kelldavis.comickindle.scope.IssueDetailScope;

import dagger.Subcomponent;

@IssueDetailScope
@Subcomponent
public interface IssueDetailComponent {

  IssueDetailPresenter presenter();

  void inject(IssueDetailFragment fragment);
}
