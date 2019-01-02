package com.kelldavis.comickindle.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.model.Image;
import com.kelldavis.comickindle.model.Publisher;
import com.kelldavis.comickindle.model.VolumeInfoList;
import com.kelldavis.comickindle.utils.ImageUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("WeakerAccess")
public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.VolumeViewHolder> {

  private List<VolumeInfoList> mVolumeInfoList;
  final OnVolumeClickListener mOnVolumeClickListener;

  public VolumeAdapter(OnVolumeClickListener onVolumeClickListener) {
    mVolumeInfoList = new ArrayList<>(0);
    mOnVolumeClickListener = onVolumeClickListener;
  }

  @Override
  public VolumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_volumes_list_item, parent, false);

    return new VolumeViewHolder(view);
  }

  @Override
  public void onBindViewHolder(VolumeViewHolder holder, int position) {
    holder.bindTo(mVolumeInfoList.get(position));
  }

  @Override
  public int getItemCount() {
    return mVolumeInfoList == null ? 0 : mVolumeInfoList.size();
  }

  public List<VolumeInfoList> getVolumeInfoList() {
    return mVolumeInfoList;
  }

  public void setVolumeInfoList(List<VolumeInfoList> volumeInfoList) {
    mVolumeInfoList = volumeInfoList;
  }

  class VolumeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private long currentVolumeId;

    @BindView(R.id.volume_image_layout)
    FrameLayout imageLayout;
    @BindView(R.id.volume_cover)
    ImageView volumeCover;
    @BindView(R.id.volume_cover_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.volume_name)
    TextView volumeName;
    @BindView(R.id.volume_publisher)
    TextView volumePublisher;
    @BindString(R.string.volumes_publisher_text)
    String publisherFormat;
    @BindView(R.id.volume_issues_count)
    TextView issuesCount;
    @BindString(R.string.volumes_count_text)
    String issuesCountFormat;

    VolumeViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(this);
    }

    void bindTo(VolumeInfoList volume) {

      currentVolumeId = volume.id();

      Image image = volume.image();
      if (image != null) {
        String url = image.small_url();
        ImageUtils.loadImageWithProgress(volumeCover, url, progressBar);
      } else {
        imageLayout.setVisibility(View.GONE);
      }

      volumeName.setText(volume.name());

      Publisher publisher = volume.publisher();

      if (publisher != null) {
        String publisherName = String.format(Locale.US, publisherFormat, publisher.name());
        volumePublisher.setText(publisherName);
      } else {
        volumePublisher.setVisibility(View.GONE);
      }

      String yearCount = String.format(Locale.US, issuesCountFormat, volume.count_of_issues());
      issuesCount.setText(yearCount);
    }

    @Override
    public void onClick(View v) {
      mOnVolumeClickListener.volumeClicked(currentVolumeId);
    }
  }

  public interface OnVolumeClickListener {

    void volumeClicked(long volumeId);
  }
}
