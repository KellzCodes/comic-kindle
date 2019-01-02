package com.kelldavis.comickindle.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.evernote.android.state.State;
import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.fragment.VolumeDetailFragment;
import com.kelldavis.comickindle.fragment.VolumeDetailFragmentBuilder;
import com.kelldavis.comickindle.utils.FragmentUtils;

import butterknife.BindView;


@SuppressWarnings("WeakerAccess")
public class VolumeDetailActivity extends BaseActivity {

  private static final String EXTRA_VOLUME_ID_ARG = "current_volume_id";

  @State
  long chosenVolumeId;

  @BindView(R.id.volume_details_toolbar)
  Toolbar toolbar;

  public static Intent prepareIntent(Context context, long volumeId) {
    Intent intent = new Intent(context, VolumeDetailActivity.class);
    intent.putExtra(EXTRA_VOLUME_ID_ARG, volumeId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_volume_details);

    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    Bundle extras = getIntent().getExtras();
    chosenVolumeId = getIdFromExtras(extras);

    VolumeDetailFragment fragment =
        (VolumeDetailFragment) getSupportFragmentManager()
            .findFragmentById(R.id.volume_details_container);

    if (fragment == null) {
      fragment = new VolumeDetailFragmentBuilder(chosenVolumeId).build();
      FragmentUtils.addFragmentTo(getSupportFragmentManager(), fragment,
          R.id.volume_details_container);
    }
  }

  private long getIdFromExtras(Bundle extras) {
    if (extras != null && extras.containsKey(EXTRA_VOLUME_ID_ARG)) {
      return extras.getLong(EXTRA_VOLUME_ID_ARG);
    }
    return 1L;
  }
}
