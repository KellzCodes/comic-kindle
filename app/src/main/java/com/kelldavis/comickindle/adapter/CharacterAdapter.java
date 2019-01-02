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
import com.kelldavis.comickindle.model.CharacterInfoList;
import com.kelldavis.comickindle.model.Image;
import com.kelldavis.comickindle.model.Publisher;
import com.kelldavis.comickindle.utils.ImageUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("WeakerAccess")
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>{

  private List<CharacterInfoList> mCharacterInfoList;
  final OnVolumeClickListener mOnVolumeClickListener;

  public CharacterAdapter(OnVolumeClickListener onVolumeClickListener) {
    mCharacterInfoList = new ArrayList<>(0);
    mOnVolumeClickListener = onVolumeClickListener;
  }

  @Override
  public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_characters_list_item, parent, false);

    return new CharacterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CharacterViewHolder holder, int position) {
    holder.bindTo(mCharacterInfoList.get(position));
  }

  @Override
  public int getItemCount() {
    return mCharacterInfoList == null ? 0 : mCharacterInfoList.size();
  }

  public List<CharacterInfoList> getmCharacterInfoList() {
    return mCharacterInfoList;
  }

  public void setCharacterInfoList(List<CharacterInfoList> characterInfoListList) {
    mCharacterInfoList = characterInfoListList;
  }

  public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private long currentCharacterId;

    @BindView(R.id.character_image_layout)
    FrameLayout frameLayout;
    @BindView(R.id.character_poster)
    ImageView characterPoster;
    @BindView(R.id.character_poster_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.character_name)
    TextView characterName;
    @BindView(R.id.character_publisher)
    TextView characterPublisher;
    @BindString(R.string.volumes_publisher_text)
    String publisherText;

    CharacterViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      itemView.setOnClickListener(this);
    }

    void bindTo(CharacterInfoList character) {

      currentCharacterId = character.id();

      Image image = character.image();
      if (image != null) {
        String url = image.small_url();
        ImageUtils.loadImageWithProgress(characterPoster, url, progressBar);
      } else {
        frameLayout.setVisibility(View.GONE);
      }

      characterName.setText(character.name());

      Publisher publisher = character.publisher();

      if (publisher != null) {
        String publisherName = String.format(Locale.US, publisherText, publisher.name());
        characterPublisher.setText(publisherName);
      } else {
        characterPublisher.setVisibility(View.GONE);
      }
    }

    @Override
    public void onClick(View v) {
      mOnVolumeClickListener.volumeClicked(currentCharacterId);
    }
  }

  public interface OnVolumeClickListener {

    void volumeClicked(long characterId);
  }
}
