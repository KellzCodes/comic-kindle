package com.kelldavis.comickindle;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static com.kelldavis.comickindle.TestUtils.getDummyIssueInfo;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import com.kelldavis.comickindle.contract.ComicContract;
import com.kelldavis.comickindle.helper.ComicDbHelper;
import com.kelldavis.comickindle.utils.ContentUtils;
import java.util.HashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ComicDbHelperTest {

  private ComicDbHelper comicDbHelper;
  private SQLiteDatabase database;

  @Before
  public void setUp() {
    getTargetContext().deleteDatabase(ComicDbHelper.DATABASE_NAME);
    comicDbHelper = new ComicDbHelper(getTargetContext());
    database = comicDbHelper.getWritableDatabase();
  }

  @Test
  public void testDatabaseTablesCreation() {

    final HashSet<String> tables = new HashSet<>();
    tables.add(ComicContract.IssueEntry.TABLE_NAME_TODAY_ISSUES);
    tables.add(ComicContract.IssueEntry.TABLE_NAME_OWNED_ISSUES);
    tables.add(ComicContract.TrackedVolumeEntry.TABLE_NAME_TRACKED_VOLUMES);

    SQLiteDatabase database = comicDbHelper.getReadableDatabase();
    assertEquals(true, database.isOpen());

    Cursor queryCursor = database
        .rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
    assertTrue("Error: This means that the database has not been created correctly",
        queryCursor.moveToFirst());

    do {
      tables.remove(queryCursor.getString(0));
    } while (queryCursor.moveToNext());

    assertTrue("Error: Your database was created without all necessary tables",
        tables.isEmpty());

    queryCursor.close();
  }

  @Test
  public void testTodayIssuesTableColumnsCreation() {

    Cursor queryCursor = database
        .rawQuery("PRAGMA table_info(" + ComicContract.IssueEntry.TABLE_NAME_TODAY_ISSUES + ")", null);
    assertTrue("Error: Unable to query the database for table information.",
        queryCursor.moveToFirst());

    final HashSet<String> todayIssuesTableColumns = new HashSet<>();
    todayIssuesTableColumns.add(ComicContract.IssueEntry._ID);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_ID);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_NAME);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_NUMBER);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_STORE_DATE);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_COVER_DATE);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_SMALL_IMAGE);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_MEDIUM_IMAGE);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_HD_IMAGE);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_VOLUME_ID);
    todayIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_VOLUME_NAME);

    int columnNameIndex = queryCursor.getColumnIndex("name");

    do {
      String columnName = queryCursor.getString(columnNameIndex);
      todayIssuesTableColumns.remove(columnName);
    } while (queryCursor.moveToNext());

    assertTrue("Error: The table doesn't contain all required columns",
        todayIssuesTableColumns.isEmpty());

    queryCursor.close();
  }

  @Test
  public void testOwnedIssuesTableColumnsCreation() {

    Cursor queryCursor = database
        .rawQuery("PRAGMA table_info(" + ComicContract.IssueEntry.TABLE_NAME_OWNED_ISSUES + ")", null);
    assertTrue("Error: Unable to query the database for table information.",
        queryCursor.moveToFirst());

    final HashSet<String> ownedIssuesTableColumns = new HashSet<>();
    ownedIssuesTableColumns.add(ComicContract.IssueEntry._ID);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_ID);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_NAME);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_NUMBER);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_STORE_DATE);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_COVER_DATE);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_SMALL_IMAGE);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_MEDIUM_IMAGE);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_HD_IMAGE);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_VOLUME_ID);
    ownedIssuesTableColumns.add(ComicContract.IssueEntry.COLUMN_ISSUE_VOLUME_NAME);

    int columnNameIndex = queryCursor.getColumnIndex("name");

    do {
      String columnName = queryCursor.getString(columnNameIndex);
      ownedIssuesTableColumns.remove(columnName);
    } while (queryCursor.moveToNext());

    assertTrue("Error: The table doesn't contain all required columns",
        ownedIssuesTableColumns.isEmpty());

    queryCursor.close();
  }

  @Test
  public void testTrackedVolumesTableColumnsCreation() {

    Cursor queryCursor = database
        .rawQuery("PRAGMA table_info(" + ComicContract.TrackedVolumeEntry.TABLE_NAME_TRACKED_VOLUMES + ")", null);
    assertTrue("Error: Unable to query the database for table information.",
        queryCursor.moveToFirst());

    final HashSet<String> trackedVolumesTableColumns = new HashSet<>();
    trackedVolumesTableColumns.add(ComicContract.TrackedVolumeEntry._ID);
    trackedVolumesTableColumns.add(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_ID);
    trackedVolumesTableColumns.add(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_NAME);
    trackedVolumesTableColumns.add(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_ISSUES_COUNT);
    trackedVolumesTableColumns.add(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_PUBLISHER_NAME);
    trackedVolumesTableColumns.add(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_START_YEAR);
    trackedVolumesTableColumns.add(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_SMALL_IMAGE);
    trackedVolumesTableColumns.add(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_MEDIUM_IMAGE);
    trackedVolumesTableColumns.add(ComicContract.TrackedVolumeEntry.COLUMN_VOLUME_HD_IMAGE);

    int columnNameIndex = queryCursor.getColumnIndex("name");

    do {
      String columnName = queryCursor.getString(columnNameIndex);
      trackedVolumesTableColumns.remove(columnName);
    } while (queryCursor.moveToNext());

    assertTrue("Error: The table doesn't contain all required columns",
        trackedVolumesTableColumns.isEmpty());

    queryCursor.close();
  }

  @Test
  public void testInsertTodayIssueRecord() {

    ContentValues testValues = ContentUtils.issueInfoToContentValues(getDummyIssueInfo());
    long inserted = database.insert(ComicContract.IssueEntry.TABLE_NAME_TODAY_ISSUES, null, testValues);
    assertTrue(inserted != -1);

    Cursor queryCursor = database.query(
        ComicContract.IssueEntry.TABLE_NAME_TODAY_ISSUES,
        null,
        null,
        null,
        null,
        null,
        null);

    assertTrue("Error: No Records returned from the query", queryCursor.moveToFirst());

    TestUtils.validateCurrentRecord("Error: Today's issue query validation failed",
        queryCursor, testValues);

    queryCursor.close();
  }

  @Test
  public void testInsertOwnedIssueRecord() {

    ContentValues testValues = ContentUtils.issueInfoToContentValues(getDummyIssueInfo());
    long inserted = database.insert(ComicContract.IssueEntry.TABLE_NAME_OWNED_ISSUES, null, testValues);
    assertTrue(inserted != -1);

    Cursor queryCursor = database.query(
        ComicContract.IssueEntry.TABLE_NAME_OWNED_ISSUES,
        null,
        null,
        null,
        null,
        null,
        null);

    assertTrue("Error: No Records returned from the query", queryCursor.moveToFirst());

    TestUtils.validateCurrentRecord("Error: Owned issue query validation failed",
        queryCursor, testValues);

    queryCursor.close();
  }

  @Test
  public void testInsertTrackedVolumeRecord() {

    ContentValues testValues = ContentUtils.volumeInfoToContentValues(TestUtils.getDummyVolumeInfo());
    long inserted = database
        .insert(ComicContract.TrackedVolumeEntry.TABLE_NAME_TRACKED_VOLUMES, null, testValues);
    assertTrue(inserted != -1);

    Cursor queryCursor = database.query(
        ComicContract.TrackedVolumeEntry.TABLE_NAME_TRACKED_VOLUMES,
        null,
        null,
        null,
        null,
        null,
        null);

    assertTrue("Error: No records returned from the query", queryCursor.moveToFirst());

    TestUtils.validateCurrentRecord("Error: Tracked volume query validation failed",
        queryCursor, testValues);

    queryCursor.close();
  }

  @After
  public void cleanUp() {
    comicDbHelper.close();
    database.close();
  }
}
