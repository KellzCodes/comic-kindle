package com.kelldavis.comickindle.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ComicCharacterInfoList {
    public abstract long id();
    @Nullable public abstract String name();
    @Nullable public abstract ComicImages image();
    @Nullable
    public abstract ComicPublisherInfo publisher();

    public static TypeAdapter<ComicCharacterInfoList> typeAdapter(Gson gson) {
        return new AutoValue_ComicCharacterInfoList.GsonTypeAdapter(gson);
    }
}
