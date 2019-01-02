package com.kelldavis.comickindle.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.contract.ComicContract.TrackedVolumeEntry;
import com.kelldavis.comickindle.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import timber.log.Timber;

@SuppressWarnings("WeakerAccess")
public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.VolumeViewHolder> {

  final OnVolumeClickListener mOnVolumeClickListener;
  Cursor mCursor;

  public WatchListAdapter(OnVolumeClickListener onVolumeClickListener) {
    mOnVolumeClickListener = onVolumeClickListener;
  }

  @Override
  public VolumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_volumes_tracker_item, parent, false);

    return new VolumeViewHolder(view);
  }

  @Override
  public void onBindViewHolder(VolumeViewHolder holder, int position) {

    holder.bindTo(position);
}

  @Override
  public int getItemCount() {
    return (mCursor != null) ? mCursor.getCount() : 0;
  }

  @SuppressWarnings("UnusedReturnValue")
  public Cursor swapCursor(Cursor data) {

    Timber.d("Swapping mCursor...");

    if (data != null) {
      Timber.d("Cursor size: " + data.getCount());
    } else {
      Timber.d("Cursor is null.");
    }


    if (mCursor == data) {
      return null;
    }

    Cursor temp = mCursor;
    mCursor = data;

    if (data != null) {
      notifyDataSetChanged();
    }
    return temp;
  }

  class VolumeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private long currentId;

    @BindView(R.id.volume_cover)
    ImageView volumeCover;
    @BindView(R.id.volume_cover_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.volume_name)
    TextView volumeName;

    VolumeViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(this);
    }

    public void bindTo(int position) {

      int idIndex = mCursor.getColumnIndexOrThrow(TrackedVolumeEntry.COLUMN_VOLUME_ID);
      int coverIndex = mCursor.getColumnIndexOrThrow(TrackedVolumeEntry.COLUMN_VOLUME_SMALL_IMAGE);
      int nameIndex = mCursor.getColumnIndexOrThrow(TrackedVolumeEntry.COLUMN_VOLUME_NAME);

      Timber.d("Cursor size is " + mCursor.getCount());
      Timber.d("Cursor: id index is " + idIndex);
      Timber.d("Cursor: cover index is " + coverIndex);
      Timber.d("Cursor: name index is " + nameIndex);

      mCursor.moveToPosition(position);

      Timber.d("Cursor current position" + mCursor.getPosition());

      Timber.d("Trying to get id");
      currentId = mCursor.getLong(idIndex);
      Timber.d("Trying to get cover");
      String coverUrl = mCursor.getString(coverIndex);
      Timber.d("Trying to get name");
      String name = mCursor.getString(nameIndex);

      ImageUtils.loadImageWithProgress(volumeCover, coverUrl, progressBar);
      volumeName.setText(name);
    }

    @Override
    public void onClick(View v) {
      mOnVolumeClickListener.volumeClicked(currentId);
    }
  }

  public interface OnVolumeClickListener {

    void volumeClicked(long volumeId);
  }
}
