package com.kelldavis.comickindle.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.kelldavis.comickindle.contract.ComicContract;
import com.kelldavis.comickindle.model.Image;
import com.kelldavis.comickindle.model.IssueInfo;
import com.kelldavis.comickindle.model.IssueInfoList;
import com.kelldavis.comickindle.model.VolumeInfo;
import com.kelldavis.comickindle.model.VolumeInfoList;
import com.kelldavis.comickindle.model.Volume;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContentUtils {

  public static IssueInfoList shortenIssueInfo(IssueInfo issue) {
    return IssueInfoList.builder()
        .id(issue.id())
        .image(issue.image())
        .issue_number(issue.issue_number())
        .name(issue.name())
        .store_date(issue.store_date())
        .cover_date(issue.cover_date())
        .volume(issue.volume())
        .build();
  }

  public static VolumeInfoList shortenVolumeInfo(VolumeInfo volume) {
    return VolumeInfoList.builder()
        .id(volume.id())
        .count_of_issues(volume.count_of_issues())
        .image(volume.image())
        .name(volume.name())
        .publisher(volume.publisher())
        .start_year(volume.start_year())
        .build();
  }

  public static ContentValues issueInfoToContentValues(@NonNull IssueInfoList issue) {

    ContentValues values = new ContentValues();
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_ID, issue.id());
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_NUMBER, issue.issue_number());
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_NAME, issue.name());
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_STORE_DATE, issue.store_date());
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_COVER_DATE, issue.cover_date());
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_SMALL_IMAGE, issue.image().small_url());
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_MEDIUM_IMAGE, issue.image().medium_url());
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_HD_IMAGE, issue.image().super_url());
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_VOLUME_ID, issue.volume().id());
    values.put(ComicContract.IssueEntry.COLUMN_ISSUE_VOLUME_NAME, issue.volume().name());

    return values;
  }

  public static Set<Long> getIdsFromCursor(Cursor cursor) {

    Set<Long> result = new HashSet<>(cursor.getCount());

    cursor.moveToPosition(-1);

    while(cursor.moveToNext()) {
      long id = cursor.getLong(0);
      result.add(id);
    }

    return result;
  }


  public static List<IssueInfoList> issueInfoFromCursor(Cursor cursor) {

    List<IssueInfoList> issues = new ArrayList<>();

    if (cursor.getCount() > 0) {
      cursor.moveToPosition(-1);

      while (cursor.moveToNext()) {

        long id = cursor
            .getLong(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_ID));
        int number = cursor
            .getInt(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_NUMBER));
        String name = cursor
            .getString(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_NAME));
        String storeDate = cursor
            .getString(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_STORE_DATE));
        String coverDate = cursor
            .getString(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_COVER_DATE));
        String small = cursor
            .getString(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_SMALL_IMAGE));
        String medium = cursor
            .getString(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_MEDIUM_IMAGE));
        String hd = cursor
            .getString(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_HD_IMAGE));
        long volumeId = cursor
            .getLong(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_VOLUME_ID));
        String volumeName = cursor
            .getString(cursor.getColumnIndexOrThrow(ComicContract.IssueEntry.COLUMN_ISSUE_VOLUME_NAME));

        IssueInfoList issue = IssueInfoList.builder()
            .id(id)
            .image(
                Image.builder()
                    .icon_url("")
                    .medium_url(medium)
                    .screen_url("")
                    .small_url(small)
                    .super_url(hd)
                    .thumb_url("")
                    .tiny_url("")
                    .build())
            .issue_number(number)
            .name(name)
            .store_date(storeDate)
            .cover_date(coverDate)
            .volume(
                Volume.builder()
                    .id(volumeId)
                    .name(volumeName)
                    .build())
            .build();

        issues.add(issue);
      }
    }

    return issues;
  }

  public static ContentValues volumeInfoToContentValues(@NonNull VolumeInfoList volume) {

    ContentValues values = new ContentValues();

    values.put(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_ID, volume.id());
    values.put(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_NAME, volume.name());
    values.put(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_ISSUES_COUNT, volume.count_of_issues());
    values.put(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_PUBLISHER_NAME, volume.publisher().name());
    values.put(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_START_YEAR, volume.start_year());
    values.put(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_SMALL_IMAGE, volume.image().small_url());
    values.put(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_MEDIUM_IMAGE, volume.image().medium_url());
    values.put(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_HD_IMAGE, volume.image().super_url());

    return values;
  }


  public static Uri buildDetailsUri(Uri baseUri, long recordId) {
    return baseUri.buildUpon()
        .appendPath(String.valueOf(recordId))
        .build();
  }
}
