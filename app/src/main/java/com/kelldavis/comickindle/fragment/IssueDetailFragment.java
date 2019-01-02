package com.kelldavis.comickindle.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;
import com.kelldavis.comickindle.adapter.IssueDetailAdapter;
import com.kelldavis.comickindle.app.ComicKindleApp;
import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.component.IssueDetailComponent;
import com.kelldavis.comickindle.model.Character;
import com.kelldavis.comickindle.model.Image;
import com.kelldavis.comickindle.model.IssueInfo;
import com.kelldavis.comickindle.modules.ComicLocalDataModule;
import com.kelldavis.comickindle.modules.ComicRemoteDataModule;
import com.kelldavis.comickindle.presenter.IssueDetailPresenter;
import com.kelldavis.comickindle.ui.CharacterDetailsActivity;
import com.kelldavis.comickindle.utils.FragmentUtils;
import com.kelldavis.comickindle.utils.HtmlUtils;
import com.kelldavis.comickindle.utils.ImageUtils;
import com.kelldavis.comickindle.utils.IssueTextUtils;
import com.kelldavis.comickindle.utils.ViewUtils;
import com.kelldavis.comickindle.view.IssueDetailView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
@FragmentWithArgs
public class IssueDetailFragment
    extends BaseLceFragment<LinearLayout, IssueInfo, IssueDetailView, IssueDetailPresenter>
    implements IssueDetailView {

  @Arg
  long issueId;

  @BindView(R.id.issue_details_screen)
  ImageView mIssueScreen;
  @BindView(R.id.issue_details_full_name)
  TextView mIssueFullTitleName;
  @BindView(R.id.issue_details_issue_name)
  TextView mIssueSeparateName;
  @BindView(R.id.issue_details_cover_date)
  TextView mIssueCoverDate;
  @BindView(R.id.issue_details_store_date)
  TextView mIssueStoreDate;
  @BindView(R.id.issue_details_description)
  TextView mIssueDescription;
  @BindView(R.id.issue_details_characters_card)
  CardView mCharactersView;
  @BindView(R.id.issue_details_characters_list)
  ListView mCharactersList;

  @BindString(R.string.msg_bookmarked)
  String mMessageBookmarked;
  @BindString(R.string.msg_bookmark_removed)
  String mMessageBookmarkRemoved;
  @BindBool(R.bool.is_tablet_layout)
  boolean isTwoPaneMode;

  private IssueDetailComponent mIssueDetailComponent;
  private IssueInfo mCurrentIssue;
  private IssueDetailAdapter mListAdapter;
  private Menu mCurrentMenu;

  // --- FRAGMENT LIFECYCLE ---

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setRetainInstance(true);

    setHasOptionsMenu(true);

    mListAdapter = new IssueDetailAdapter(new ArrayList<>(0));

    mCharactersList.setAdapter(mListAdapter);

    mCharactersList.setDivider(
        new ColorDrawable(ContextCompat.getColor(getContext(), R.color.colorAccentDark)));

    mCharactersList.setDividerHeight(1);

    mCharactersList.setOnItemClickListener((parent, view1, position, id) -> {
      if (isTwoPaneMode) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment fragment = new CharacterDetailFragmentBuilder(id).build();

        FragmentUtils.replaceFragmentIn(
            manager, fragment, R.id.content_frame, "CharacterDetailFragment", true);
      } else {
        startActivity(CharacterDetailsActivity.prepareIntent(getContext(), id));
      }
    });

    if (savedInstanceState != null) {
      loadData(false);
    }
  }

  // --- OPTIONS MENU ---

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.fragment_issue_details, menu);

    mCurrentMenu = menu;

    presenter.setUpBookmarkIconState(issueId);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_bookmark:
        onBookmarkClick();
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  // --- BASE LCE FRAGMENT ---

  @Override
  protected int getLayoutRes() {
    return R.layout.fragment_issue_details;
  }

  @Override
  protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
    return e.getMessage();
  }

  @NonNull
  @Override
  public IssueDetailPresenter createPresenter() {
    return mIssueDetailComponent.presenter();
  }

  @Override
  protected void injectDependencies() {
    mIssueDetailComponent = ComicKindleApp.getAppComponent()
        .plusRemoteComponent(new ComicRemoteDataModule())
        .plusLocalComponent(new ComicLocalDataModule())
        .plusIssueDetailsComponent();
    mIssueDetailComponent.inject(this);
  }
  // --- MVP VIEW STATE ---

  @Override
  public IssueInfo getData() {
    return mCurrentIssue;
  }

  @NonNull
  @Override
  public LceViewState<IssueInfo, IssueDetailView> createViewState() {
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
  public void setData(IssueInfo data) {
    mCurrentIssue = data;
    bindIssueDataToUi(mCurrentIssue);
  }

  @Override
  public void loadData(boolean pullToRefresh) {
    presenter.loadIssueDetails(issueId);
  }

  @Override
  public void markAsBookmarked() {
    mCurrentMenu.findItem(R.id.action_bookmark).setIcon(R.drawable.ic_bookmark_black_24dp);

    ViewUtils.tintMenuIcon(getContext(), mCurrentMenu, R.id.action_bookmark,
        R.color.material_color_white);
  }

  @Override
  public void unmarkAsBookmarked() {
    mCurrentMenu.findItem(R.id.action_bookmark).setIcon(R.drawable.ic_bookmark_border_black_24dp);

    ViewUtils.tintMenuIcon(getContext(), mCurrentMenu, R.id.action_bookmark,
        R.color.material_color_white);
  }

  @Override
  public void onBookmarkClick() {

    if (mCurrentIssue == null) {
      return;
    }

    String message;

    boolean isBookmarkedNow = presenter.isCurrentIssueBookmarked(issueId);

    if (isBookmarkedNow) {
      presenter.removeBookmark(issueId);
      message = mMessageBookmarkRemoved;
    } else {
      presenter.bookmarkIssue(mCurrentIssue);
      message = mMessageBookmarked;
    }

    presenter.setUpBookmarkIconState(issueId);

    int parentLayoutId = (isTwoPaneMode) ?
        R.id.main_coordinator_layout :
        R.id.issue_details_activity_layout;

    Snackbar.make(
        ButterKnife.findById(getActivity(), parentLayoutId),
        message,
        Snackbar.LENGTH_SHORT)
        .show();
  }

  // --- UI BINDING UTILS ---

  private void bindIssueDataToUi(IssueInfo issue) {

    loadHeaderImage(mIssueScreen, issue.image());

    String volumeName = issue.volume().name();
    int issueNumber = issue.issue_number();
    setUpHeaderText(mIssueFullTitleName, volumeName, issueNumber);
    setUpOtherText(mIssueSeparateName, issue.name());
    setUpDate(mIssueCoverDate, issue.cover_date());
    setUpDate(mIssueStoreDate, issue.store_date());
    setUpDescription(mIssueDescription, issue.description());
    setUpCharactersList(mCharactersView, mCharactersList, issue.character_credits());
  }

  private void loadHeaderImage(ImageView header, Image image) {
    if (image != null) {
      String imageUrl = image.small_url();
      ImageUtils.loadImageWithTopCrop(header, imageUrl);
    } else {
      header.setVisibility(View.GONE);
    }
  }

  private void setUpHeaderText(TextView textView, String volumeName, int number) {
    textView.setText(IssueTextUtils.getFormattedIssueTitle(volumeName, number));
  }

  private void setUpOtherText(TextView textView, String name) {
    if (name != null) {
      textView.setText(name);
    } else {
      textView.setVisibility(View.GONE);
    }
  }

  private void setUpDate(TextView textView, String date) {
    if (date != null) {
      textView.setText(date);
    } else {
      textView.setText("-");
    }
  }

  private void setUpDescription(TextView textView, String description) {
    if (description != null) {
      textView.setText(HtmlUtils.parseHtmlText(description));
    } else {
      textView.setVisibility(View.GONE);
    }
  }

  private void setUpCharactersList(CardView parent, ListView listView,
      List<Character> characters) {
    if (characters != null && !characters.isEmpty()) {
      mListAdapter.replaceCharacters(characters);
      ViewUtils.setListViewHeightBasedOnChildren(listView);
    } else {
      parent.setVisibility(View.GONE);
    }
  }
}