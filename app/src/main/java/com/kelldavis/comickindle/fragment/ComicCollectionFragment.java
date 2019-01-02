package com.kelldavis.comickindle.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindBool;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.evernote.android.state.State;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;
import com.kelldavis.comickindle.adapter.ComicCollectionAdapter;
import com.kelldavis.comickindle.app.ComicKindleApp;
import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.component.ComicCollectionComponent;
import com.kelldavis.comickindle.model.IssueInfoList;
import com.kelldavis.comickindle.modules.ComicLocalDataModule;
import com.kelldavis.comickindle.modules.ComicRemoteDataModule;
import com.kelldavis.comickindle.presenter.ComicCollectionPresenter;
import com.kelldavis.comickindle.ui.IssueDetailsActivity;
import com.kelldavis.comickindle.ui.MainActivity;
import com.kelldavis.comickindle.utils.FragmentUtils;
import com.kelldavis.comickindle.utils.ViewUtils;
import com.kelldavis.comickindle.view.ComicCollectionView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;
import java.util.Locale;

@SuppressWarnings("WeakerAccess")
@FragmentWithArgs
public class ComicCollectionFragment extends
    BaseLceFragment<RecyclerView, List<IssueInfoList>, ComicCollectionView, ComicCollectionPresenter>
    implements ComicCollectionView {


  @BindString(R.string.msg_no_issues_owned)
  String mEmptyViewText;
  @BindString(R.string.issues_title_format)
  String mTitleFormatString;
  @BindInt(R.integer.grid_columns_count)
  int mGridColumnsCount;
  @BindBool(R.bool.is_tablet_layout)
  boolean isTwoPaneMode;

  @BindView(R.id.emptyView)
  TextView mEmptyView;
  @BindView(R.id.contentView)
  RecyclerView mContentView;

  @State
  String title;

  @State
  String searchQuery;

  private ComicCollectionComponent mComicCollectionComponent;
  private ComicCollectionAdapter mAdapter;
  private Menu mCurrentMenu;
  private boolean isPendingStartupAnimation;

  // --- FRAGMENTS LIFECYCLE ---

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setRetainInstance(true);

    if (savedInstanceState == null) {
      isPendingStartupAnimation = true;
    }

    mAdapter = new ComicCollectionAdapter(issueId -> {
      if (isTwoPaneMode) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment fragment = new IssueDetailFragmentBuilder(issueId).build();

        FragmentUtils.replaceFragmentIn(
            manager, fragment, R.id.content_frame, "IssueDetailFragment", true);
      } else {
        startActivity(IssueDetailsActivity.prepareIntent(getContext(), issueId));
      }
    });

    StaggeredGridLayoutManager manager =
        new StaggeredGridLayoutManager(mGridColumnsCount, StaggeredGridLayoutManager.VERTICAL);

    mContentView.setLayoutManager(manager);
    mContentView.setHasFixedSize(true);
    mContentView.setAdapter(mAdapter);

    setHasOptionsMenu(true);

    if (isNotNullOrEmpty(searchQuery)) {
      loadDataFiltered(searchQuery);
    } else if (savedInstanceState != null) {
      loadData(false);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    loadData(false);
  }

  // --- OPTIONS MENU ---

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.fragment_watch_list, menu);

    mCurrentMenu = menu;

    ViewUtils.tintMenuIcon(getContext(), menu, R.id.action_search, R.color.material_color_white);
    ViewUtils.tintMenuIcon(getContext(), menu, R.id.action_clear_search_query,
        R.color.material_color_white);

    setUpSearchItem(menu);

    if (isNotNullOrEmpty(searchQuery)) {
      showClearQueryMenuItem(true);
    } else {
      showClearQueryMenuItem(false);
    }

    if (isPendingStartupAnimation) {
      hideToolbar();
      isPendingStartupAnimation = false;
      startToolbarAnimation();
    }

    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_clear_search_query:
        showClearQueryMenuItem(false);
        loadData(false);
        break;
    }
    return true;
  }

  // --- BASE LCE FRAGMENT ---

  @Override
  protected int getLayoutRes() {
    return R.layout.fragment_watch_list;
  }

  @Override
  protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
    return e.getMessage();
  }

  @NonNull
  @Override
  public ComicCollectionPresenter createPresenter() {
    return mComicCollectionComponent.presenter();
  }

  @Override
  protected void injectDependencies() {
    mComicCollectionComponent = ComicKindleApp.getAppComponent()
        .plusRemoteComponent(new ComicRemoteDataModule())
        .plusLocalComponent(new ComicLocalDataModule())
        .plusOwnedIssuesComponent();
    mComicCollectionComponent.inject(this);
  }

  // --- MVP VIEW STATE ---

  @Override
  public List<IssueInfoList> getData() {
    return mAdapter == null ? null : mAdapter.getIssueInfoList();
  }

  @NonNull
  @Override
  public LceViewState<List<IssueInfoList>, ComicCollectionView> createViewState() {
    return new RetainingLceViewState<>();
  }

  // --- MVP VIEW ---

  @Override
  public void setTitle(String date) {
    title = String.format(Locale.US, mTitleFormatString, date);
    updateTitle();
  }

  @Override
  public void showEmptyView(boolean show) {

    if (show) {
      mEmptyView.setText(mEmptyViewText);
      mEmptyView.setVisibility(View.VISIBLE);
      mContentView.setVisibility(View.GONE);
      errorView.setVisibility(View.GONE);
    } else {
      mEmptyView.setVisibility(View.GONE);
    }
  }


  @Override
  public void showError(Throwable e, boolean pullToRefresh) {
    super.showError(e, pullToRefresh);
    loadingView.setVisibility(View.GONE);
  }

  @Override
  public void showLoading(boolean pullToRefresh) {
    super.showLoading(pullToRefresh);

    if (!pullToRefresh) {
      loadingView.setVisibility(View.GONE);
    }
  }

  @Override
  public void setData(List<IssueInfoList> data) {
    mAdapter.setIssueInfoList(data);
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void loadData(boolean pullToRefresh) {
    setTitle("My collection");
    presenter.loadOwnedIssues();
  }

  @Override
  public void loadDataFiltered(String filter) {
    setTitle(filter);
    presenter.loadOwnedIssuesFilteredByName(filter);
  }

  // --- MISC UTILITY FUNCTIONS ---

  private void setUpSearchItem(Menu menu) {
    // Find items
    MaterialSearchView searchView = ButterKnife.findById(getActivity(), R.id.search_view);
    MenuItem menuItem = menu.findItem(R.id.action_search);

    // Tweaks
    searchView.setVoiceSearch(false);
    searchView.setMenuItem(menuItem);

    // Listeners
    searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {

        searchQuery = query;

        if (searchQuery.length() > 0) {
          showClearQueryMenuItem(true);
          loadDataFiltered(searchQuery);
        }
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
  }

  private void updateTitle() {
    ActionBar supportActionBar = ((MainActivity) getActivity()).getSupportActionBar();

    if (supportActionBar != null) {
      supportActionBar.setTitle(title);
    }
  }

  private boolean isNotNullOrEmpty(String str) {
    return str != null && !str.isEmpty();
  }

  void showClearQueryMenuItem(boolean show) {
    mCurrentMenu.findItem(R.id.action_search).setVisible(!show);
    mCurrentMenu.findItem(R.id.action_clear_search_query).setVisible(show);
  }
}
