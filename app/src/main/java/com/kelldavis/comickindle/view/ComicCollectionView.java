package com.kelldavis.comickindle.view;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.kelldavis.comickindle.model.IssueInfoList;
import java.util.List;

public interface ComicCollectionView extends MvpLceView<List<IssueInfoList>> {

  /*
   * These methods are already defined in Mosby:
   ********************************************************************
   * void showLoading (boolean pullToRefresh);
   * Display a loading animation while loading data in background by
   * invoking the corresponding presenter method.
   ********************************************************************
   * void showContent();
   * After the content has been loaded the presenter calls {@link
   * #setData(Object)} to fill the view with data. Afterwards,
   * the presenter calls {@link #showContent()} to display the data
   * ******************************************************************
   * void showError(Throwable e, boolean pullToRefresh);
   * Display a error view (a TextView) on the screen if an error has
   * occurred while loading data. You can distinguish between a
   * pull-to-refresh error by checking the boolean parameter and display
   * the error message in another, more suitable way like a Toast
   ********************************************************************
   * void setData(List<IssueInfoList> data);
   * Load the data. Typically invokes the presenter method to load the
   * desired data. Should not be called from presenter to prevent
   * infinity loops. The method is declared in the views interface to
   * add support for view state easily.
   * *******************************************************************
   * void loadData(boolean pullToRefresh);
   * */

  void setTitle(String title);

  void showEmptyView(boolean show);

  void loadDataFiltered(String filter);
}
