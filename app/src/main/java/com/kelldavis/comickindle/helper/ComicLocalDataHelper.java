package com.kelldavis.comickindle.helper;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.kelldavis.comickindle.contract.ComicContract;
import com.kelldavis.comickindle.contract.ComicContract.TrackedVolumeEntry;
import com.kelldavis.comickindle.contract.ComicContract.IssueEntry;
import com.kelldavis.comickindle.model.IssueInfoList;
import com.kelldavis.comickindle.model.VolumeInfoList;
import com.kelldavis.comickindle.utils.ContentUtils;

import io.reactivex.Single;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

public class ComicLocalDataHelper {

    private final ContentResolver contentResolver;

    @Inject
    public ComicLocalDataHelper(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void saveTodayIssuesToDb(@NonNull List<IssueInfoList> issues) {

        for (IssueInfoList issue : issues) {
            contentResolver.insert(
                    ComicContract.IssueEntry.CONTENT_URI_TODAY_ISSUES,
                    ContentUtils.issueInfoToContentValues(issue));
        }
    }

    public Single<List<IssueInfoList>> getTodayIssuesFromDb() {

        return Single.create(e -> {

            Cursor query = contentResolver
                    .query(IssueEntry.CONTENT_URI_TODAY_ISSUES, null, null, null, null);

            if (query != null) {
                List<IssueInfoList> list = ContentUtils.issueInfoFromCursor(query);
                query.close();
                e.onSuccess(list);
            }
        });
    }

    public Single<List<IssueInfoList>> getOwnedIssuesFromDb() {

        return Single.create(e -> {

            Cursor query = contentResolver
                    .query(IssueEntry.CONTENT_URI_OWNED_ISSUES,
                            null,
                            null,
                            null,
                            IssueEntry.COLUMN_ISSUE_VOLUME_ID + "," + IssueEntry.COLUMN_ISSUE_NUMBER);

            if (query != null) {
                List<IssueInfoList> list = ContentUtils.issueInfoFromCursor(query);
                query.close();
                e.onSuccess(list);
            }
        });
    }

    public void removeAllTodayIssuesFromDb() {
        contentResolver.delete(IssueEntry.CONTENT_URI_TODAY_ISSUES, null, null);
    }

    public boolean isIssueBookmarked(long issueId) {

        boolean result = false;

        Cursor cursor = contentResolver.query(
                IssueEntry.CONTENT_URI_OWNED_ISSUES,
                null,
                IssueEntry.COLUMN_ISSUE_ID + " = ?",
                new String[]{String.valueOf(issueId)},
                null);

        if (cursor != null) {
            result = cursor.getCount() > 0;
            cursor.close();
        }
        return result;
    }

    public void saveOwnedIssueToDb(@NonNull IssueInfoList issue) {
        contentResolver.insert(
                IssueEntry.CONTENT_URI_OWNED_ISSUES,
                ContentUtils.issueInfoToContentValues(issue));
    }

    public void removeOwnedIssueFromDb(long issueId) {
        Uri deletionUri = ContentUtils
                .buildDetailsUri(IssueEntry.CONTENT_URI_OWNED_ISSUES, issueId);

        contentResolver.delete(deletionUri, null, null);
    }

    public Set<Long> getTodayVolumesIdsFromDb() {

        Set<Long> ids = null;

        Cursor query = contentResolver.query(
                IssueEntry.CONTENT_URI_TODAY_ISSUES,
                new String[]{IssueEntry.COLUMN_ISSUE_VOLUME_ID},
                null,
                null,
                null);

        if (query != null) {
            ids = ContentUtils.getIdsFromCursor(query);
            query.close();
        }

        return ids;
    }

    public Set<Long> getTrackedVolumesIdsFromDb() {

        Set<Long> ids = null;

        Cursor query = contentResolver.query(
                TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES,
                new String[]{TrackedVolumeEntry.COLUMN_VOLUME_ID},
                null,
                null,
                null);

        if (query != null) {
            ids = ContentUtils.getIdsFromCursor(query);
            query.close();
        }

        return ids;
    }

    public String getTrackedVolumeNameById(long volumeId) {

        String result = "";

        Cursor cursor = contentResolver.query(
                TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES,
                new String[]{TrackedVolumeEntry.COLUMN_VOLUME_NAME},
                TrackedVolumeEntry.COLUMN_VOLUME_ID + " = ?",
                new String[]{String.valueOf(volumeId)},
                null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = cursor.getString(0);
            cursor.close();
        }
        return result;
    }

    public boolean isVolumeTracked(long volumeId) {
        boolean result = false;

        Cursor cursor = contentResolver.query(
                TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES,
                null,
                TrackedVolumeEntry.COLUMN_VOLUME_ID + " = ?",
                new String[]{String.valueOf(volumeId)},
                null);

        if (cursor != null) {
            result = cursor.getCount() > 0;
            cursor.close();
        }
        return result;
    }

    public void saveTrackedVolumeToDb(@NonNull VolumeInfoList volume) {
        contentResolver.insert(
                TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES,
                ContentUtils.volumeInfoToContentValues(volume));
    }

    public void removeTrackedVolumeFromDb(long volumeId) {
        Uri deletionUri = ContentUtils
                .buildDetailsUri(TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES, volumeId);

        contentResolver.delete(deletionUri, null, null);
    }
}

