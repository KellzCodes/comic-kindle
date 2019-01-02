package com.kelldavis.comickindle.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class VolumeInfo {
    @Nullable
    public abstract List<Character> characters();
    public abstract int count_of_issues();
    @Nullable public abstract String description();
    public abstract long id();
    @Nullable public abstract Image image();
    @Nullable public abstract List<Issue> issues();
    @Nullable public abstract String name();
    @Nullable public abstract Publisher publisher();
    public abstract int start_year();

    public static TypeAdapter<VolumeInfo> typeAdapter(Gson gson) {
        return new AutoValue_VolumeInfo.GsonTypeAdapter(gson);
    }
}

