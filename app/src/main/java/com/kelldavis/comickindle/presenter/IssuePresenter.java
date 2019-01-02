package com.kelldavis.comickindle.presenter;

import android.content.Context;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.helper.ComicLocalDataHelper;
import com.kelldavis.comickindle.helper.ComicPreferenceHelper;
import com.kelldavis.comickindle.helper.ComicRemoteDataHelper;
import com.kelldavis.comickindle.manager.ComicSyncManager;
import com.kelldavis.comickindle.model.IssueInfoList;
import com.kelldavis.comickindle.utils.DateTextUtils;
import com.kelldavis.comickindle.utils.NetworkUtils;
import com.kelldavis.comickindle.view.IssueView;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

@SuppressWarnings("WeakerAccess")
public class IssuePresenter extends MvpBasePresenter<IssueView> {

  final ComicPreferenceHelper comicPreferenceHelper;
  final ComicLocalDataHelper localDataHelper;
  final ComicRemoteDataHelper remoteDataHelper;
  final Context context;

  @Inject
  public IssuePresenter(
      ComicPreferenceHelper comicPreferenceHelper,
      ComicLocalDataHelper localDataHelper,
      ComicRemoteDataHelper remoteDataHelper,
      Context context) {
    this.comicPreferenceHelper = comicPreferenceHelper;
    this.localDataHelper = localDataHelper;
    this.remoteDataHelper = remoteDataHelper;
    this.context = context;
  }

  public boolean shouldNotDisplayShowcases() {
    return comicPreferenceHelper.wasIssuesViewShowcased();
  }

  public void showcaseWasDisplayed() {
    comicPreferenceHelper.markIssuesViewAsShowcased();
  }

  public void loadTodayIssues(boolean pullToRefresh) {
    if (pullToRefresh) {
      // Sync data with apps sync manager
      Timber.d("Loading and sync started...");
      loadTodayIssuesFromServer();
    } else {
      // Load and display issues from db
      Timber.d("Loading issues from db started...");
      loadTodayIssuesFromDB();
    }
  }

  public void loadTodayIssuesFromServer() {

    if (NetworkUtils.isNetworkConnected(context)) {
      ComicSyncManager.syncImmediately();
    } else {
      Timber.d("Network is not available!");
      if (isViewAttached()) {
        getView().showLoading(false);
        getView().showContent();
        getView().showErrorToast(context.getString(R.string.error_data_not_available_short));
      }
    }
  }

  public void loadTodayIssuesFromDB() {
    localDataHelper
        .getTodayIssuesFromDb()
        .subscribe(getObserver());
  }

  public void loadIssuesByDate(String date) {
    Timber.d("Load issues by date: " + date);
    remoteDataHelper
        .getIssuesListByDate(date)
        .subscribe(getObserverFiltered(date, true));
  }

  public void loadIssuesByDateAndName(String date, String name) {
    Timber.d("Load issues by date: " + date + " and name: " + name);
    remoteDataHelper
        .getIssuesListByDate(date)
        .flatMapObservable(Observable::fromIterable)
        .filter(issue -> issue.volume() != null)
        .filter(issue -> issue.volume().name().contains(name))
        .toList()
        .subscribe(getObserverFiltered(name, false));
  }

  private SingleObserver<List<IssueInfoList>> getObserver() {
    return new SingleObserver<List<IssueInfoList>>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Timber.d("Data loading started...");
        if (isViewAttached()) {
          getView().showEmptyView(false);
          getView().showLoading(true);
        }
      }

      @Override
      public void onSuccess(@NonNull List<IssueInfoList> list) {

        if (isViewAttached()) {
          getView().setTitle(DateTextUtils.getFormattedDateToday());

          if (list.size() > 0) {
            // Display content
            Timber.d("Displaying content...");
            getView().setData(list);
            getView().showContent();
          } else {
            // Display empty view
            Timber.d("Displaying empty view...");
            getView().showLoading(false);
            getView().showEmptyView(true);
          }
        }
      }

      @Override
      public void onError(@NonNull Throwable e) {
        Timber.d("Data loading error: " + e.getMessage());
        if (isViewAttached()) {
          Timber.d("Displaying error view...");
          getView().showError(e, false);
        }
      }
    };
  }

  private SingleObserver<List<IssueInfoList>> getObserverFiltered(String str, boolean isDate) {
    return new SingleObserver<List<IssueInfoList>>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Timber.d("Issues data loading started...");
        if (isViewAttached()) {
          getView().showEmptyView(false);
          getView().showLoading(true);
        }
      }

      @Override
      public void onSuccess(@NonNull List<IssueInfoList> list) {

        if (isViewAttached()) {
          String title = isDate ? DateTextUtils.getFormattedDate(str, "MMM d, yyyy") : str;
          getView().setTitle(title);

          if (list.size() > 0) {
            // Display content
            Timber.d("Displaying content...");
            getView().setData(list);
            getView().showContent();
          } else {
            // Display empty view
            Timber.d("Displaying empty view...");
            getView().showLoading(false);
            getView().showEmptyView(true);
          }
        }
      }

      @Override
      public void onError(@NonNull Throwable e) {
        Timber.d("Data loading error: " + e.getMessage());
        if (isViewAttached()) {
          Timber.d("Displaying error view...");
          getView().showError(e, false);
        }
      }
    };
  }
}
