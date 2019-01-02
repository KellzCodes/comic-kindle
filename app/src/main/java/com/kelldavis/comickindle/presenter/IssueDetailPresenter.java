package com.kelldavis.comickindle.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.kelldavis.comickindle.helper.ComicLocalDataHelper;
import com.kelldavis.comickindle.helper.ComicRemoteDataHelper;
import com.kelldavis.comickindle.model.IssueInfo;
import com.kelldavis.comickindle.utils.ContentUtils;
import com.kelldavis.comickindle.view.IssueDetailView;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;

@SuppressWarnings("WeakerAccess")
public class IssueDetailPresenter extends MvpBasePresenter<IssueDetailView> {

  final ComicLocalDataHelper localDataHelper;
  final ComicRemoteDataHelper remoteDataHelper;

  @Inject
  IssueDetailPresenter(
      ComicLocalDataHelper localDataHelper,
      ComicRemoteDataHelper remoteDataHelper) {
    this.localDataHelper = localDataHelper;
    this.remoteDataHelper = remoteDataHelper;
  }

  public void setUpBookmarkIconState(long issueId) {
    if (isViewAttached()) {
      if (isCurrentIssueBookmarked(issueId)) {
        getView().markAsBookmarked();
      } else {
        getView().unmarkAsBookmarked();
      }
    }
  }

  public boolean isCurrentIssueBookmarked(long issueId) {
    return localDataHelper.isIssueBookmarked(issueId);
  }

  public void bookmarkIssue(IssueInfo issue) {
    localDataHelper.saveOwnedIssueToDb(ContentUtils.shortenIssueInfo(issue));
  }

  public void removeBookmark(long issueId) {
    localDataHelper.removeOwnedIssueFromDb(issueId);
  }

  public void loadIssueDetails(long issueId) {
    remoteDataHelper
        .getIssueDetailsById(issueId)
        .subscribe(getIssueDetailsObserver());
  }

  private SingleObserver<IssueInfo> getIssueDetailsObserver() {
    return new SingleObserver<IssueInfo>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Timber.d("Issue details loading started...");
        getView().showLoading(true);
      }

      @Override
      public void onSuccess(@NonNull IssueInfo issueInfo) {
        Timber.d("Issue details loading completed.");
        if (isViewAttached()) {
          getView().showLoading(false);
          getView().setData(issueInfo);
        }
      }

      @Override
      public void onError(@NonNull Throwable e) {
        Timber.d("Issue details loading error: " + e.getMessage());
        getView().showError(e, false);
      }
    };
  }
}
