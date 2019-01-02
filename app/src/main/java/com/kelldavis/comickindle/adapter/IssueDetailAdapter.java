package com.kelldavis.comickindle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kelldavis.comickindle.R;
import com.kelldavis.comickindle.model.Character;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

public class IssueDetailAdapter extends BaseAdapter {

  private List<Character> mCharacterList;

  public IssueDetailAdapter(List<Character> characterList) {
    mCharacterList = characterList;
  }

  public void replaceCharacters(List<Character> characterList) {
    mCharacterList = characterList;
    notifyDataSetChanged();
  }

  @Override

  public View getView(int position, View convertView, ViewGroup parent) {

    CharacterViewHolder holder;

    if (convertView != null) {
      holder = (CharacterViewHolder) convertView.getTag();
    } else {
      convertView = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.fragment_issue_details_character_item, parent, false);
      holder = new CharacterViewHolder(convertView);
      convertView.setTag(holder);
    }

    holder.bindTo(mCharacterList.get(position));

    return convertView;
  }

  @Override
  public int getCount() {
    return mCharacterList.size();
  }

  @Override
  public Character getItem(int position) {
    return mCharacterList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return mCharacterList.get(position).id();
  }


  class CharacterViewHolder {

    @BindView(R.id.issue_details_character_name)
    TextView characterName;

    long characterId;

    CharacterViewHolder(View view) {
      ButterKnife.bind(this, view);
    }

    void bindTo(Character character) {
      String name = character.name();
      characterId = character.id();

      if (name != null) {
        characterName.setText(name);
      }
    }
  }
}
