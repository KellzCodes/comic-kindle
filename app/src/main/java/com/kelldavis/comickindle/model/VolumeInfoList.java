package com.kelldavis.comickindle.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class VolumeInfoList {
    public abstract int count_of_issues();
    public abstract long id();
    @Nullable
    public abstract Image image();
    @Nullable public abstract String name();
    @Nullable public abstract Publisher publisher();
    public abstract int start_year();

    public static TypeAdapter<VolumeInfoList> typeAdapter(Gson gson) {
        return new AutoValue_VolumeInfoList.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_VolumeInfoList.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder count_of_issues(int count_of_issues);
        public abstract Builder id(long id);
        public abstract Builder image(Image image);
        public abstract Builder name(String name);
        public abstract Builder publisher(Publisher publisher);
        public abstract Builder start_year(int start_year);

        public abstract VolumeInfoList build();
    }
}

