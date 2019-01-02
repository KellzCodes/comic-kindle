package com.kelldavis.comickindle.presenter;

import android.support.annotation.NonNull;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.kelldavis.comickindle.helper.ComicLocalDataHelper;
import com.kelldavis.comickindle.helper.ComicRemoteDataHelper;
import com.kelldavis.comickindle.model.VolumeInfo;
import com.kelldavis.comickindle.utils.ContentUtils;
import com.kelldavis.comickindle.view.VolumeDetailView;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;

public class VolumeDetailPresenter extends MvpBasePresenter<VolumeDetailView> {

  private final ComicLocalDataHelper localDataHelper;
  private final ComicRemoteDataHelper remoteDataHelper;

  @Inject
  VolumeDetailPresenter(
      ComicLocalDataHelper localDataHelper,
      ComicRemoteDataHelper remoteDataHelper) {
    this.localDataHelper = localDataHelper;
    this.remoteDataHelper = remoteDataHelper;
  }

  public boolean ifTargetIssueOwned(long issueId) {
    return localDataHelper.isIssueBookmarked(issueId);
  }

  public void setUpTrackIconState(long volumeId) {
    if (isViewAttached()) {
      if (isCurrentVolumeTracked(volumeId)) {
        getView().markAsTracked();
      } else {
        getView().unmarkAsTracked();
      }
    }
  }

  public boolean isCurrentVolumeTracked(long volumeId) {
    return localDataHelper.isVolumeTracked(volumeId);
  }

  public void trackVolume(VolumeInfo volume) {
    localDataHelper.saveTrackedVolumeToDb(ContentUtils.shortenVolumeInfo(volume));
  }

  public void removeTracking(long volumeId) {
    localDataHelper.removeTrackedVolumeFromDb(volumeId);
  }

  public void loadVolumeDetails(long volumeId) {
    remoteDataHelper
        .getVolumeDetailsById(volumeId)
        .subscribe(getVolumeDetailsObserver());
  }

  private SingleObserver<VolumeInfo> getVolumeDetailsObserver() {
    return new SingleObserver<VolumeInfo>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Timber.d("Volume details loading started...");
        getView().showLoading(true);
      }

      @Override
      public void onSuccess(@NonNull VolumeInfo volumeInfo) {
        Timber.d("Volume details loading completed.");
        if (isViewAttached()) {
          getView().showLoading(false);
          getView().setData(volumeInfo);
        }
      }

      @Override
      public void onError(@NonNull Throwable e) {
        Timber.d("Volume details loading error: " + e.getMessage());
        getView().showError(e, false);
      }
    };
  }
}
