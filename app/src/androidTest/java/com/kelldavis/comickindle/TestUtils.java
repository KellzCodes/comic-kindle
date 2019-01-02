package com.kelldavis.comickindle;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import android.content.ContentValues;
import android.database.Cursor;

import com.kelldavis.comickindle.model.Image;
import com.kelldavis.comickindle.model.IssueInfoList;
import com.kelldavis.comickindle.model.Publisher;
import com.kelldavis.comickindle.model.Volume;
import com.kelldavis.comickindle.model.VolumeInfoList;

import java.util.Map.Entry;
import java.util.Set;

class TestUtils {

  static IssueInfoList getDummyIssueInfo() {
    return IssueInfoList.builder()
        .id(123)
        .issue_number(321)
        .name("name")
        .store_date("store_date")
        .cover_date("cover_date")
        .image(
            Image.builder()
                .icon_url("icon_url")
                .medium_url("medium_url")
                .screen_url("screen_url")
                .small_url("small_url")
                .super_url("super_url")
                .thumb_url("thumb_url")
                .tiny_url("tiny_url")
                .build())
        .volume(
            Volume.builder()
                .id(123)
                .name("name")
                .build()
        )
        .build();
  }

  static VolumeInfoList getDummyVolumeInfo() {
    return VolumeInfoList.builder()
        .count_of_issues(123)
        .id(321)
        .image(
            Image.builder()
                .icon_url("icon_url")
                .medium_url("medium_url")
                .screen_url("screen_url")
                .small_url("small_url")
                .super_url("super_url")
                .thumb_url("thumb_url")
                .tiny_url("tiny_url")
                .build())
        .name("Tets issue")
        .publisher(Publisher.builder()
            .name("DC Comics")
            .id(11111)
            .build())
        .start_year(2003)
        .build();
  }

  static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
    assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
    validateCurrentRecord(error, valueCursor, expectedValues);
    valueCursor.close();
  }

  static void validateCurrentRecord(String error, Cursor valueCursor,
      ContentValues expectedValues) {
    Set<Entry<String, Object>> valueSet = expectedValues.valueSet();
    for (Entry<String, Object> entry : valueSet) {
      String columnName = entry.getKey();
      int idx = valueCursor.getColumnIndex(columnName);
      assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
      String expectedValue = entry.getValue().toString();
      assertEquals("Value '" + entry.getValue().toString() +
          "' did not match the expected value '" +
          expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
    }
  }
}
