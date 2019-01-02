package com.kelldavis.comickindle.factory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.kelldavis.comickindle.app.AppNavigation;
import com.kelldavis.comickindle.fragment.CharacterFragment;
import com.kelldavis.comickindle.fragment.CharacterFragmentBuilder;
import com.kelldavis.comickindle.fragment.ComicCollectionFragment;
import com.kelldavis.comickindle.fragment.ComicCollectionFragmentBuilder;
import com.kelldavis.comickindle.fragment.ComicPreferenceFragment;
import com.kelldavis.comickindle.fragment.ComicPreferenceFragmentBuilder;
import com.kelldavis.comickindle.fragment.IssueFragment;
import com.kelldavis.comickindle.fragment.IssueFragmentBuilder;
import com.kelldavis.comickindle.fragment.VolumeFragment;
import com.kelldavis.comickindle.fragment.VolumeFragmentBuilder;
import com.kelldavis.comickindle.fragment.WatchListFragment;
import com.kelldavis.comickindle.fragment.WatchListFragmentBuilder;


public class MainFragmentFactory {

  public static Fragment getFragment(FragmentManager manager, @AppNavigation.Section int type) {

    Fragment fragment = manager.findFragmentByTag(getFragmentTag(type));

    if (fragment != null) {
      return fragment;
    }

    switch (type) {
      case AppNavigation.ISSUES:
        return new IssueFragmentBuilder().build();
      case AppNavigation.VOLUMES:
        return new VolumeFragmentBuilder().build();
      case AppNavigation.CHARACTERS:
        return new CharacterFragmentBuilder().build();
      case AppNavigation.COLLECTION:
        return new ComicCollectionFragmentBuilder().build();
      case AppNavigation.WATCHLIST:
        return new WatchListFragmentBuilder().build();
      case AppNavigation.SETTINGS:
        return new ComicPreferenceFragmentBuilder().build();
      default:
        return null;
    }
  }

  public static String getFragmentTag(@AppNavigation.Section int type) {
    switch (type) {
      case AppNavigation.ISSUES:
        return IssueFragment.class.getSimpleName();
      case AppNavigation.VOLUMES:
        return VolumeFragment.class.getSimpleName();
      case AppNavigation.CHARACTERS:
        return CharacterFragment.class.getSimpleName();
      case AppNavigation.COLLECTION:
        return ComicCollectionFragment.class.getSimpleName();
      case AppNavigation.WATCHLIST:
        return WatchListFragment.class.getSimpleName();
      case AppNavigation.SETTINGS:
        return ComicPreferenceFragment.class.getSimpleName();
      default:
        return "";
    }
  }
}
