package com.kelldavis.comickindle.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.evernote.android.state.State;
import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.fragment.IssueDetailFragment;
import com.kelldavis.comickindle.fragment.IssueDetailFragmentBuilder;
import com.kelldavis.comickindle.utils.FragmentUtils;

import butterknife.BindView;


@SuppressWarnings("WeakerAccess")
public class IssueDetailsActivity extends BaseActivity {

  private static final String EXTRA_ISSUE_ID_ARG = "current_issue_id";

  @State
  long chosenIssueId;

  @BindView(R.id.issue_details_toolbar)
  Toolbar toolbar;

  public static Intent prepareIntent(Context context, long issueId) {
    Intent intent = new Intent(context, IssueDetailsActivity.class);
    intent.putExtra(EXTRA_ISSUE_ID_ARG, issueId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_issue_details);

    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    Bundle extras = getIntent().getExtras();
    chosenIssueId = getIdFromExtras(extras);

    IssueDetailFragment fragment =
        (IssueDetailFragment) getSupportFragmentManager()
            .findFragmentById(R.id.issue_details_container);

    if (fragment == null) {
      fragment = new IssueDetailFragmentBuilder(chosenIssueId).build();
      FragmentUtils.addFragmentTo(getSupportFragmentManager(), fragment,
          R.id.issue_details_container);
    }
  }

  private long getIdFromExtras(Bundle extras) {
    if (extras != null && extras.containsKey(EXTRA_ISSUE_ID_ARG)) {
      return extras.getLong(EXTRA_ISSUE_ID_ARG);
    }
    return 1L;
  }
}
