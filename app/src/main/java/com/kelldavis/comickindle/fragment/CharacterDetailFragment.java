package com.kelldavis.comickindle.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;
import com.kelldavis.comickindle.app.ComicKindleApp;
import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.component.CharacterDetailComponent;
import com.kelldavis.comickindle.model.CharacterInfo;
import com.kelldavis.comickindle.model.ChararcterOrigin;
import com.kelldavis.comickindle.model.Image;
import com.kelldavis.comickindle.modules.ComicRemoteDataModule;
import com.kelldavis.comickindle.presenter.CharacterDetailPresenter;
import com.kelldavis.comickindle.utils.HtmlUtils;
import com.kelldavis.comickindle.utils.ImageUtils;
import com.kelldavis.comickindle.view.CharacterDetailView;


@SuppressWarnings("WeakerAccess")
@FragmentWithArgs
public class CharacterDetailFragment extends
    BaseLceFragment<CardView, CharacterInfo, CharacterDetailView, CharacterDetailPresenter>
    implements CharacterDetailView {

  @Arg
  long mCharacterId;

  @BindView(R.id.character_details_screen)
  ImageView mCharacterPoster;
  @BindView(R.id.character_details_name)
  TextView mCharacterName;
  @BindView(R.id.character_detail_real_name)
  TextView mCharacterRealName;
  @BindView(R.id.character_detail_aliases)
  TextView mCharacterAliases;
  @BindView(R.id.character_detail_birthdate)
  TextView mCharacterBirthdate;
  @BindView(R.id.character_detail_origin)
  TextView mCharacterOrigin;
  @BindView(R.id.character_detail_gender)
  TextView mCharacterGender;
  @BindView(R.id.character_details_description)
  TextView mCharacterDescription;

  private CharacterDetailComponent mCharacterDetailComponent;
  private CharacterInfo mCurrentCharacterInfo;

  // --- FRAGMENT LIFECYCLE ---

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setRetainInstance(true);

    if (savedInstanceState != null) {
      loadData(false);
    }
  }

  // --- BASE LCE FRAGMENT ---

  @Override
  protected int getLayoutRes() {
    return R.layout.fragment_character_details;
  }

  @NonNull
  @Override
  public CharacterDetailPresenter createPresenter() {
    return mCharacterDetailComponent.presenter();
  }

  @Override
  protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
    return e.getMessage();
  }

  @Override
  protected void injectDependencies() {
    mCharacterDetailComponent = ComicKindleApp.getAppComponent()
        .plusRemoteComponent(new ComicRemoteDataModule())
        .plusCharacterDetailsComponent();

    mCharacterDetailComponent.inject(this);
  }

  // --- MVP VIEW STATE ---

  @Override
  public CharacterInfo getData() {
    return mCurrentCharacterInfo;
  }

  @NonNull
  @Override
  public LceViewState<CharacterInfo, CharacterDetailView> createViewState() {
    return new RetainingLceViewState<>();
  }

  // --- MVP VIEW ---


  @Override
  public void showContent() {
    contentView.setVisibility(View.VISIBLE);
    errorView.setVisibility(View.GONE);
    loadingView.setVisibility(View.GONE);
  }

  @Override
  public void showError(Throwable e, boolean pullToRefresh) {
    errorView.setText(R.string.error_issue_not_available);
    contentView.setVisibility(View.GONE);
    loadingView.setVisibility(View.GONE);
    errorView.setVisibility(View.VISIBLE);
  }

  @Override
  public void showLoading(boolean pullToRefresh) {
    if (pullToRefresh) {
      contentView.setVisibility(View.GONE);
      errorView.setVisibility(View.GONE);
      loadingView.setVisibility(View.VISIBLE);
    } else {
      contentView.setVisibility(View.VISIBLE);
      errorView.setVisibility(View.GONE);
      loadingView.setVisibility(View.GONE);
    }
  }

  @Override
  public void setData(CharacterInfo data) {
    mCurrentCharacterInfo = data;
    bindVolumeToUi(mCurrentCharacterInfo);
  }

  @Override
  public void loadData(boolean pullToRefresh) {
    presenter.loadCharacterDetails(mCharacterId);
  }

  // --- UI BINDING UTILS ---

  private void bindVolumeToUi(CharacterInfo character) {
    loadHeaderImage(mCharacterPoster, character.image());
    setUsualText(mCharacterName, character.name());
    setUsualText(mCharacterRealName, character.real_name());
    setUsualText(mCharacterAliases, character.aliases());
    setUsualText(mCharacterBirthdate, character.birth());
    setOrigin(mCharacterOrigin, character.origin());
    setGender(mCharacterGender, character.gender());
    setDescription(mCharacterDescription, character.description());
  }

  private void loadHeaderImage(ImageView header, Image image) {
    if (image != null) {
      String imageUrl = image.small_url();
      ImageUtils.loadImageWithTopCrop(header, imageUrl);
    } else {
      header.setVisibility(View.GONE);
    }
  }

  private void setUsualText(TextView textView, String text) {
    if (text != null) {
      textView.setText(text);
    } else {
      textView.setText("-");
    }
  }

  private void setOrigin(TextView textView, ChararcterOrigin origin) {
    if (origin != null) {
      textView.setText(origin.name());
    } else {
      textView.setText("-");
    }
  }

  private void setGender(TextView textView, int gender) {
    if (gender == 1 ) {
      textView.setText(R.string.character_details_gender_male);
    } else if (gender == 2){
      textView.setText(R.string.character_details_gender_female);
    } else {
      textView.setText(R.string.character_details_gender_unknown);
    }
  }

  private void setDescription(TextView textView, String description) {
    if (description != null) {
      textView.setText(HtmlUtils.parseHtmlText(description));
    } else {
      textView.setVisibility(View.GONE);
    }
  }
}
