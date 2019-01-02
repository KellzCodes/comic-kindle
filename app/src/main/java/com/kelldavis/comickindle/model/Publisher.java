package com.kelldavis.comickindle.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Publisher {
    public abstract long id();
    @Nullable
    public abstract String name();

    public static TypeAdapter<Publisher> typeAdapter(Gson gson) {
        return new AutoValue_Publisher.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_Publisher.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(long id);
        public abstract Builder name(String name);

        public abstract Publisher build();
    }
}

