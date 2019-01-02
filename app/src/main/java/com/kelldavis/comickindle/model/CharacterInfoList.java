package com.kelldavis.comickindle.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class CharacterInfoList {
    public abstract long id();
    @Nullable
    public abstract String name();
    @Nullable public abstract Image image();
    @Nullable public abstract Publisher publisher();

    public static TypeAdapter<CharacterInfoList> typeAdapter(Gson gson) {
        return new AutoValue_CharacterInfoList.GsonTypeAdapter(gson);
    }
}
