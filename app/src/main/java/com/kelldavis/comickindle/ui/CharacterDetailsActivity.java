package com.kelldavis.comickindle.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.evernote.android.state.State;
import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.fragment.CharacterDetailFragment;
import com.kelldavis.comickindle.fragment.CharacterDetailFragmentBuilder;
import com.kelldavis.comickindle.utils.FragmentUtils;

import butterknife.BindView;


@SuppressWarnings("WeakerAccess")
public class CharacterDetailsActivity extends BaseActivity {

  private static final String EXTRA_CHARACTER_ID_ARG = "current_character_id";

  @State
  long chosenCharacterId;

  @BindView(R.id.character_details_toolbar)
  Toolbar toolbar;

  public static Intent prepareIntent(Context context, long characterId) {
    Intent intent = new Intent(context, CharacterDetailsActivity.class);
    intent.putExtra(EXTRA_CHARACTER_ID_ARG, characterId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_character_details);

    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    Bundle extras = getIntent().getExtras();
    chosenCharacterId = getIdFromExtras(extras);

    CharacterDetailFragment fragment =
        (CharacterDetailFragment) getSupportFragmentManager()
            .findFragmentById(R.id.character_details_container);

    if (fragment == null) {
      fragment = new CharacterDetailFragmentBuilder(chosenCharacterId).build();
      FragmentUtils.addFragmentTo(getSupportFragmentManager(), fragment,
          R.id.character_details_container);
    }
  }

  private long getIdFromExtras(Bundle extras) {
    if (extras != null && extras.containsKey(EXTRA_CHARACTER_ID_ARG)) {
      return extras.getLong(EXTRA_CHARACTER_ID_ARG);
    }
    return 1L;
  }
}
