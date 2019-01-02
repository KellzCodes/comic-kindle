package com.kelldavis.comickindle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.kelldavis.comickindle.utils.ComicAuthenticator;

public class ComicAuthenticatorService extends Service {

  private ComicAuthenticator authenticator;

  @Override
  public void onCreate() {
    authenticator = new ComicAuthenticator(this);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return authenticator.getIBinder();
  }
}
