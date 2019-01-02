package com.kelldavis.comickindle.presenter;

import android.support.annotation.NonNull;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.kelldavis.comickindle.helper.ComicRemoteDataHelper;
import com.kelldavis.comickindle.model.CharacterInfo;
import com.kelldavis.comickindle.view.CharacterDetailView;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;

public class CharacterDetailPresenter extends MvpBasePresenter<CharacterDetailView> {

  private final ComicRemoteDataHelper remoteDataHelper;

  @Inject
  CharacterDetailPresenter(ComicRemoteDataHelper remoteDataHelper) {
    this.remoteDataHelper = remoteDataHelper;
  }

  public void loadCharacterDetails(long characterId) {
    remoteDataHelper
        .getCharacterDetailsById(characterId)
        .subscribe(getCharacterDetailsObserver());
  }

  private SingleObserver<CharacterInfo> getCharacterDetailsObserver() {
    return new SingleObserver<CharacterInfo>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Timber.d("Character details loading started...");
        getView().showLoading(true);
      }

      @Override
      public void onSuccess(@NonNull CharacterInfo characterInfo) {
        Timber.d("Character details loading completed.");
        if (isViewAttached()) {
          getView().showLoading(false);
          getView().setData(characterInfo);
        }
      }

      @Override
      public void onError(@NonNull Throwable e) {
        Timber.d("Character details loading error: " + e.getMessage());
        getView().showError(e, false);
      }
    };
  }
}
