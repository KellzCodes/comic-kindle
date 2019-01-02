package com.kelldavis.comickindle.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.kelldavis.comickindle.factory.ComicWidgetFactory;

public class ComicWidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new ComicWidgetFactory(getApplicationContext());
  }
}
