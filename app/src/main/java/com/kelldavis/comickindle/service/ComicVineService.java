package com.kelldavis.comickindle.service;

import com.kelldavis.comickindle.model.CharacterInfo;
import com.kelldavis.comickindle.model.CharacterInfoList;
import com.kelldavis.comickindle.model.IssueInfo;
import com.kelldavis.comickindle.model.IssueInfoList;
import com.kelldavis.comickindle.model.VolumeInfo;
import com.kelldavis.comickindle.model.VolumeInfoList;
import com.kelldavis.comickindle.model.ServerResponse;

import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
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
  Observable<ServerResponse<List<IssueInfoList>>> getIssuesList(
      @QueryMap Map<String, String> options);

  // Request issue details
  @GET("/api/issue/" + ISSUE_TYPE_CODE + "-{id}/")
  Observable<ServerResponse<IssueInfo>> getIssueDetails(
      @Path("id") long issueId,
      @QueryMap Map<String, String> options);

  // Request volumes list
  @GET("/api/volumes/")
  Observable<ServerResponse<List<VolumeInfoList>>> getVolumesList(
      @QueryMap Map<String, String> options);

  // Request volume details
  @GET("/api/volume/" + VOLUME_TYPE_CODE + "-{id}/")
  Observable<ServerResponse<VolumeInfo>> getVolumeDetails(
      @Path("id") long volumeId,
      @QueryMap Map<String, String> options);

  // Request characters list
  @GET("/api/characters/")
  Observable<ServerResponse<List<CharacterInfoList>>> getCharactersList(
      @QueryMap Map<String, String> options);

  // Request character details
  @GET("/api/character/" + CHARACTER_TYPE_CODE + "-{id}/")
  Observable<ServerResponse<CharacterInfo>> getCharacterDetails(
      @Path("id") long characterId,
      @QueryMap Map<String, String> options);

}
