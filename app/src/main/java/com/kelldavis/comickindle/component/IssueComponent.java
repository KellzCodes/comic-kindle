package com.kelldavis.comickindle.component;

import com.kelldavis.comickindle.fragment.IssueFragment;
import com.kelldavis.comickindle.presenter.IssuePresenter;
import com.kelldavis.comickindle.scope.IssueScope;

import dagger.Subcomponent;

@IssueScope
@Subcomponent
public interface IssueComponent {

  IssuePresenter presenter();

  void inject(IssueFragment fragment);
}
