package com.kelldavis.comickindle.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.model.IssueInfoList;
import com.kelldavis.comickindle.utils.ImageUtils;
import com.kelldavis.comickindle.utils.IssueTextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ComicCollectionAdapter extends RecyclerView.Adapter<ComicCollectionAdapter.ComicCollectionViewHolder> {

  private List<IssueInfoList> mIssueInfoList;
  final OnIssueClickListener mOnIssueClickListener;

  public ComicCollectionAdapter(OnIssueClickListener onIssueClickListener) {
    mIssueInfoList = new ArrayList<>();
    mOnIssueClickListener = onIssueClickListener;
  }

  @Override
  public ComicCollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_watch_list_item, parent, false);

    return new ComicCollectionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ComicCollectionViewHolder holder, int position) {
    holder.bindTo(mIssueInfoList.get(position));
  }

  @Override
  public int getItemCount() {
    return mIssueInfoList == null ? 0 : mIssueInfoList.size();
  }

  @Override
  public long getItemId(int position) {
    return mIssueInfoList.get(position).id();
  }

  public List<IssueInfoList> getIssueInfoList() {
    return mIssueInfoList;
  }

  public void setIssueInfoList(List<IssueInfoList> issueInfoList) {
    mIssueInfoList = issueInfoList;
  }

  class ComicCollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private long currentIssueId;

    @BindView(R.id.issue_cover)
    ImageView issueCover;
    @BindView(R.id.issue_name)
    TextView issueName;
    @BindView(R.id.issue_cover_progressbar)
    ProgressBar progressBar;

    ComicCollectionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      mOnIssueClickListener.issueClicked(currentIssueId);
    }

    void bindTo(IssueInfoList issue) {

      currentIssueId = issue.id();

      String coverUrl = issue.image().small_url();
      String issueNameText = issue.name();
      String volumeNameText = issue.volume().name();
      int number = issue.issue_number();

      String name = IssueTextUtils.getFormattedIssueName(issueNameText, volumeNameText, number);
      issueName.setText(name);

      if (coverUrl != null) {
        ImageUtils.loadImageWithProgress(issueCover, coverUrl, progressBar);
      }
    }
  }

  public interface OnIssueClickListener {

    void issueClicked(long issueId);
  }
}