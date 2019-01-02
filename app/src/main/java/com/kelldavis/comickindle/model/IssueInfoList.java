package com.kelldavis.comickindle.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class IssueInfoList {
    public abstract long id();
    @Nullable
    public abstract Image image();
    public abstract int issue_number();
    @Nullable public abstract String name();
    @Nullable public abstract String store_date();
    @Nullable public abstract String cover_date();
    @Nullable public abstract Volume volume();

    public static TypeAdapter<IssueInfoList> typeAdapter(Gson gson) {
        return new AutoValue_IssueInfoList.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_IssueInfoList.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(long id);
        public abstract Builder image(Image image);
        public abstract Builder issue_number(int issue_number);
        public abstract Builder name(String name);
        public abstract Builder store_date(String store_date);
        public abstract Builder cover_date(String cover_date);
        public abstract Builder volume(Volume volume);

        public abstract IssueInfoList build();
    }
}

