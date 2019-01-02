package com.kelldavis.comickindle.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class CharacterInfo {
    @Nullable
    public abstract String aliases();
    @Nullable public abstract String birth();
    public abstract int count_of_issue_appearances();
    @Nullable public abstract String description();
    public abstract int gender();
    public abstract long id();
    @Nullable public abstract Image image();
    @Nullable public abstract String name();
    @Nullable public abstract ChararcterOrigin origin();
    @Nullable public abstract String real_name();

    public static TypeAdapter<CharacterInfo> typeAdapter(Gson gson) {
        return new AutoValue_CharacterInfo.GsonTypeAdapter(gson);
    }
}

