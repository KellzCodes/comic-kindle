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
import com.kelldavis.comickindle.adapter.CharacterAdapter;
import com.kelldavis.comickindle.app.ComicKindleApp;
import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.component.CharacterComponent;
import com.kelldavis.comickindle.model.CharacterInfoList;
import com.kelldavis.comickindle.modules.ComicRemoteDataModule;
import com.kelldavis.comickindle.presenter.CharacterPresenter;
import com.kelldavis.comickindle.ui.CharacterDetailsActivity;
import com.kelldavis.comickindle.ui.MainActivity;
import com.kelldavis.comickindle.utils.FragmentUtils;
import com.kelldavis.comickindle.utils.ViewUtils;
import com.kelldavis.comickindle.view.CharacterView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

@SuppressWarnings("WeakerAccess")
@FragmentWithArgs
public class CharacterFragment extends
    BaseLceFragment<RecyclerView, List<CharacterInfoList>, CharacterView, CharacterPresenter>
    implements CharacterView {

  @BindInt(R.integer.grid_columns_count)
  int mGridColumnsCount;
  @BindString(R.string.characters_fragment_title)
  String mFragmentTitle;
  @BindString(R.string.msg_no_characters_found)
  String mEmptyViewText;
  @BindString(R.string.msg_characters_start)
  String mInitialViewText;
  @BindBool(R.bool.is_tablet_layout)
  boolean mTwoPaneMode;

  @BindView(R.id.initialView)
  TextView mInitialView;
  @BindView(R.id.emptyView)
  TextView mEmptyView;

  @State
  String mChosenName;

  @State
  String title;

  private CharacterComponent mCharacterComponent;
  private CharacterAdapter mCharacterAdapter;
  private boolean isPendingStartupAnimation;

  // --- FRAGMENTS LIFECYCLE ---

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (savedInstanceState == null) {
      isPendingStartupAnimation = true;
    }

    mCharacterAdapter = new CharacterAdapter(characterId -> {
      if (mTwoPaneMode) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment fragment = new CharacterDetailFragmentBuilder(characterId).build();

        FragmentUtils.replaceFragmentIn(
            manager, fragment, R.id.content_frame, "CharacterDetailFragment", true);
      } else {
        startActivity(CharacterDetailsActivity.prepareIntent(getContext(), characterId));
      }
    });

    mCharacterAdapter.setHasStableIds(true);

    StaggeredGridLayoutManager manager =
        new StaggeredGridLayoutManager(mGridColumnsCount, StaggeredGridLayoutManager.VERTICAL);

    contentView.setLayoutManager(manager);
    contentView.setHasFixedSize(true);
    contentView.setAdapter(mCharacterAdapter);

    setHasOptionsMenu(true);

    if (mChosenName != null && mChosenName.length() > 0) {
      loadDataByName(mChosenName);
      setTitle(mChosenName);
    } else {
      setTitle(mFragmentTitle);
      showInitialView(true);
    }
  }

  // --- OPTIONS MENU ---

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.fragment_character_list, menu);

    ViewUtils.tintMenuIcon(getContext(), menu, R.id.action_search, R.color.material_color_white);

    setUpSearchItem(menu);

    if (isPendingStartupAnimation) {
      hideToolbar();
      isPendingStartupAnimation = false;
      startToolbarAnimation();
    }

    super.onCreateOptionsMenu(menu, inflater);
  }

  // --- BASE LCE FRAGMENT ---

  @Override
  protected int getLayoutRes() {
    return R.layout.fragment_characters;
  }

  @Override
  protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
    return e.getMessage();
  }

  @NonNull
  @Override
  public CharacterPresenter createPresenter() {
    return mCharacterComponent.presenter();
  }

  @Override
  protected void injectDependencies() {

    mCharacterComponent = ComicKindleApp.getAppComponent()
        .plusRemoteComponent(new ComicRemoteDataModule())
        .plusCharactersComponent();
    mCharacterComponent.inject(this);
  }

  // --- MVP VIEW STATE ---

  @Override
  public List<CharacterInfoList> getData() {
    return mCharacterAdapter == null ? null : mCharacterAdapter.getmCharacterInfoList();
  }

  @NonNull
  @Override
  public LceViewState<List<CharacterInfoList>, CharacterView> createViewState() {
    return new RetainingLceViewState<>();
  }

  // --- MVP VIEW ---

  @Override
  public void setData(List<CharacterInfoList> data) {
    mCharacterAdapter.setCharacterInfoList(data);
    mCharacterAdapter.notifyDataSetChanged();
  }

  @Override
  public void loadData(boolean pullToRefresh) {
    showLoading(false);
    showInitialView(true);
  }

  @Override
  public void loadDataByName(String name) {
    presenter.loadCharactersData(name);
  }

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
        mChosenName = query;

        if (mChosenName.length() > 0) {
          loadDataByName(mChosenName);
          setTitle(mChosenName);
          presenter.logCharacterSearchEvent(mChosenName);
        }
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        //Do some magic
        return false;
      }
    });
  }

  @Override
  public void showContent() {
    showInitialView(false);
    showEmptyView(false);
    super.showContent();
  }

  @Override
  public void showLoading(boolean pullToRefresh) {
    if (pullToRefresh) {
      errorView.setVisibility(View.GONE);
      contentView.setVisibility(View.GONE);
      loadingView.setVisibility(View.VISIBLE);
    } else {
      loadingView.setVisibility(View.GONE);
    }
  }

  @Override
  public void showInitialView(boolean show) {
    if (show) {
      mInitialView.setText(mInitialViewText);
      mInitialView.setVisibility(View.VISIBLE);
      contentView.setVisibility(View.GONE);
      errorView.setVisibility(View.GONE);
    } else {
      mInitialView.setVisibility(View.GONE);
    }
  }

  @Override
  public void showEmptyView(boolean show) {
    if (show) {
      mEmptyView.setText(mEmptyViewText);
      mEmptyView.setVisibility(View.VISIBLE);
      contentView.setVisibility(View.GONE);
      errorView.setVisibility(View.GONE);
    } else {
      mEmptyView.setVisibility(View.GONE);
    }
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
    updateTitle();
  }

  private void updateTitle() {
    ActionBar supportActionBar = ((MainActivity) getActivity()).getSupportActionBar();

    if (supportActionBar != null) {
      supportActionBar.setTitle(title);
    }
  }

}
