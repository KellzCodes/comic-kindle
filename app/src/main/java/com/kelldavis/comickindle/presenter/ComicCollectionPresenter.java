package com.kelldavis.comickindle.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.kelldavis.comickindle.helper.ComicLocalDataHelper;
import com.kelldavis.comickindle.model.IssueInfoList;
import com.kelldavis.comickindle.view.ComicCollectionView;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class ComicCollectionPresenter extends MvpBasePresenter<ComicCollectionView> {

  private final ComicLocalDataHelper localDataHelper;

  @Inject
  ComicCollectionPresenter(ComicLocalDataHelper localDataHelper) {
    this.localDataHelper = localDataHelper;
  }

  public void loadOwnedIssues() {
    localDataHelper
        .getOwnedIssuesFromDb()
        .subscribe(getObserver());
  }

  public void loadOwnedIssuesFilteredByName(String name) {
    localDataHelper
        .getOwnedIssuesFromDb()
        .flatMapObservable(Observable::fromIterable)
        .filter(issue -> issue.volume() != null)
        .filter(issue -> issue.volume().name().contains(name))
        .toList()
        .subscribe(getObserver());
  }

  private SingleObserver<List<IssueInfoList>> getObserver() {

    return new SingleObserver<List<IssueInfoList>>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        getView().showEmptyView(false);
        getView().showLoading(true);
      }

      @Override
      public void onSuccess(@NonNull List<IssueInfoList> list) {

        if (isViewAttached()) {
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
