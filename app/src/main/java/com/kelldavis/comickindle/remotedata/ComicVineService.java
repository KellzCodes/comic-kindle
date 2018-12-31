package com.kelldavis.comickindle.remotedata;

import com.kelldavis.comickindle.model.ComicCharacterInfo;
import com.kelldavis.comickindle.model.ComicCharacterInfoList;
import com.kelldavis.comickindle.model.ComicIssueInfo;
import com.kelldavis.comickindle.model.ComicIssueInfoList;
import com.kelldavis.comickindle.model.ComicVolumeInfo;
import com.kelldavis.comickindle.model.ComicVolumeInfoList;
import com.kelldavis.comickindle.model.ServerResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ComicVineService {

    String ENDPOINT = "https://comicvine.gamespot.com/";
    String ISSUE_TYPE_CODE = "4000";
    String VOLUME_TYPE_CODE = "4050";
    String CHARACTER_TYPE_CODE = "4005";

    // Request issues list
    @GET("/api/issues/")
    Observable<ServerResponse<List<ComicIssueInfoList>>> getIssuesList(
            @QueryMap Map<String, String> options);

    // Request issue details
    @GET("/api/issue/" + ISSUE_TYPE_CODE + "-{id}/")
    Observable<ServerResponse<ComicIssueInfo>> getIssueDetails(
            @Path("id") long issueId,
            @QueryMap Map<String, String> options);

    // Request volumes list
    @GET("/api/volumes/")
    Observable<ServerResponse<List<ComicVolumeInfoList>>> getVolumesList(
            @QueryMap Map<String, String> options);

    // Request volume details
    @GET("/api/volume/" + VOLUME_TYPE_CODE + "-{id}/")
    Observable<ServerResponse<ComicVolumeInfo>> getVolumeDetails(
            @Path("id") long volumeId,
            @QueryMap Map<String, String> options);

    // Request characters list
    @GET("/api/characters/")
    Observable<ServerResponse<List<ComicCharacterInfoList>>> getCharactersList(
            @QueryMap Map<String, String> options);

    // Request character details
    @GET("/api/character/" + CHARACTER_TYPE_CODE + "-{id}/")
    Observable<ServerResponse<ComicCharacterInfo>> getCharacterDetails(
            @Path("id") long characterId,
            @QueryMap Map<String, String> options);

}

