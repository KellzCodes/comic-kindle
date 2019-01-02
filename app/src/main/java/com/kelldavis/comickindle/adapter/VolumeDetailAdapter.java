package com.kelldavis.comickindle.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.model.Issue;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("WeakerAccess")
public class VolumeDetailAdapter extends
    RecyclerView.Adapter<VolumeDetailAdapter.IssueViewHolder> {

  private List<Issue> mIssueList;
  final IssuesAdapterCallbacks mListener;

  public VolumeDetailAdapter(IssuesAdapterCallbacks issuesAdapterCallbacks) {
    mIssueList = new ArrayList<>();
    mListener = issuesAdapterCallbacks;
  }

  @Override
  public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_volume_details_issue_item, parent, false);

    return new IssueViewHolder(view);
  }

  @Override
  public void onBindViewHolder(IssueViewHolder holder, int position) {
    holder.bindTo(mIssueList.get(position));
  }

  @Override
  public int getItemCount() {
    return mIssueList == null ? 0 : mIssueList.size();
  }

  @Override
  public long getItemId(int position) {
    return mIssueList.get(position).id();
  }

  public List<Issue> getIssueList() {
    return mIssueList;
  }

  public void setIssueList(List<Issue> issueList) {
    mIssueList = issueList;
  }

  class IssueViewHolder extends RecyclerView.ViewHolder {

    private long currentIssueId;

    @BindView(R.id.issue_number)
    TextView issueNumber;
    @BindString(R.string.volume_details_issue_number)
    String issueNumberFormat;
    @BindView(R.id.issue_name)
    TextView issueName;
    @BindView(R.id.issue_bookmarked_icon)
    ImageView bookmarkIcon;


    IssueViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(v -> mListener.issueClicked(currentIssueId));
    }

    void bindTo(Issue issue) {

      currentIssueId = issue.id();
      int number = issue.issue_number();
      issueNumber.setText(String.format(Locale.US, issueNumberFormat, number));

      String issueNameText = issue.name();

      if (issueNameText != null) {
        issueName.setText(issueNameText);
      } else {
        issueName.setVisibility(View.GONE);
      }

      if (mListener.isIssueTracked(currentIssueId)) {
        bookmarkIcon.setImageResource(R.drawable.ic_bookmark_black_24dp);
      } else {
        bookmarkIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
      }
    }
  }

  public interface IssuesAdapterCallbacks {

    void issueClicked(long issueId);

    boolean isIssueTracked(long issueId);
  }
}