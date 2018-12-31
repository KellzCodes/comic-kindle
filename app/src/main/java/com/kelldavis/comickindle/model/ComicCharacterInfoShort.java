package com.kelldavis.comickindle.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ComicCharacterInfoShort {
    public abstract long id();
    @Nullable
    public abstract String name();

    public static TypeAdapter<ComicCharacterInfoShort> typeAdapter(Gson gson) {
        return new AutoValue_ComicCharacterInfoShort.GsonTypeAdapter(gson);
    }
}

