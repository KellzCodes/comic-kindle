package com.kelldavis.comickindle.adapter;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;

import com.kelldavis.comickindle.app.ComicKindleApp;
import com.kelldavis.comickindle.helper.ComicLocalDataHelper;
import com.kelldavis.comickindle.helper.ComicPreferenceHelper;
import com.kelldavis.comickindle.helper.ComicRemoteDataHelper;
import com.kelldavis.comickindle.modules.ComicLocalDataModule;
import com.kelldavis.comickindle.modules.ComicRemoteDataModule;
import com.kelldavis.comickindle.utils.DateTextUtils;
import com.kelldavis.comickindle.utils.NotificationUtils;

import java.util.Set;
import javax.inject.Inject;
import timber.log.Timber;

@SuppressWarnings("WeakerAccess")
public class ComicSyncAdapter extends AbstractThreadedSyncAdapter {

  public static final String ACTION_DATA_UPDATED =
      "com.kelldavis.comickindle.ACTION_DATA_UPDATED";

  @Inject
  ComicLocalDataHelper localDataHelper;

  @Inject
  ComicPreferenceHelper preferenceHelper;

  @Inject
  ComicRemoteDataHelper remoteDataHelper;

  public ComicSyncAdapter(Context context) {
    super(context, true);

    ComicKindleApp
        .getAppComponent()
        .plusRemoteComponent(new ComicRemoteDataModule())
        .plusLocalComponent(new ComicLocalDataModule())
        .inject(this);
  }

  @SuppressLint("CheckResult")
  @Override
  public void onPerformSync(Account account, Bundle extras, String authority,
      ContentProviderClient provider, SyncResult syncResult) {

    Timber.d("Scheduled sync started...");

    String date = DateTextUtils.getTodayDateString();

    remoteDataHelper
        .getIssuesListByDate(date)
        .subscribe(
            // onSuccess
            list -> {
              localDataHelper.removeAllTodayIssuesFromDb();
              localDataHelper.saveTodayIssuesToDb(list);
              preferenceHelper.setLastSyncDate(date);
              preferenceHelper.clearDisplayedVolumesIdList();
              sendDataUpdatedBroadcast();
              checkForTrackedVolumesUpdates();
              Timber.d("Scheduled sync completed.");
            },
            // onError
            throwable ->
                Timber.d("Scheduled sync error!")
        );
  }

  private void checkForTrackedVolumesUpdates() {

    Set<Long> trackedVolumes = localDataHelper.getTrackedVolumesIdsFromDb();
    Set<Long> todayVolumes = localDataHelper.getTodayVolumesIdsFromDb();
    Set<String> alreadyDisplayedVolumes = preferenceHelper.getDisplayedVolumesIdList();

    StringBuilder notificationText = new StringBuilder();

    for (Long trackedVolumeId : trackedVolumes) {
      // Tracked volume detected
      if (todayVolumes.contains(trackedVolumeId)) {
        // Volume notification was already displayed, continue
        if (alreadyDisplayedVolumes.contains(String.valueOf(trackedVolumeId))) {
          continue;
        }

        // Add volume name to notification
        String volumeName = localDataHelper.getTrackedVolumeNameById(trackedVolumeId);
        notificationText.append(volumeName);
        notificationText.append(", ");

        // Mark volume as displayed
        preferenceHelper.saveDisplayedVolumeId(trackedVolumeId);
      }
    }

    // If notification text is not empty, display it
    if (notificationText.length() > 0) {
      NotificationUtils.notifyUserAboutUpdate(
          getContext(),
          notificationText.deleteCharAt(notificationText.length() - 2).toString());
    }
  }

  private void sendDataUpdatedBroadcast() {
    Context context = getContext();
    Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED).setPackage(context.getPackageName());
    context.sendBroadcast(dataUpdatedIntent);
  }
}
