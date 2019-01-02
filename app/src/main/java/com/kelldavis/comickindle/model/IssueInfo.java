package com.kelldavis.comickindle.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class IssueInfo {
    public abstract List<Character> character_credits();
    @Nullable
    public abstract String cover_date();
    @Nullable public abstract String description();
    public abstract long id();
    @Nullable public abstract Image image();
    public abstract int issue_number();
    @Nullable public abstract String name();
    @Nullable public abstract String store_date();
    public abstract Volume volume();

    public static TypeAdapter<IssueInfo> typeAdapter(Gson gson) {
        return new AutoValue_IssueInfo.GsonTypeAdapter(gson);
    }
}

