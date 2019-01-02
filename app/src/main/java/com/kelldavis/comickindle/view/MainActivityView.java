package com.kelldavis.comickindle.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface MainActivityView extends MvpView {

  void handleChosenNavigationMenuItem(int chosenMenuItem);

  void navigateToCurrentSection();
}
