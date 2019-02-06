package com.kelldavis.comickindle.helper;

import com.kelldavis.comickindle.BuildConfig;
import com.kelldavis.comickindle.model.CharacterInfo;
import com.kelldavis.comickindle.model.CharacterInfoList;
import com.kelldavis.comickindle.model.IssueInfo;
import com.kelldavis.comickindle.model.IssueInfoList;
import com.kelldavis.comickindle.model.VolumeInfo;
import com.kelldavis.comickindle.model.VolumeInfoList;
import com.kelldavis.comickindle.model.ServerResponse;
import com.kelldavis.comickindle.service.ComicVineService;
import com.kelldavis.comickindle.utils.ClassUtils;
import com.kelldavis.comickindle.utils.ScheduleUtils;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class ComicRemoteDataHelper {

    private static final String API_KEY = BuildConfig.COMICVINE_API_KEY;

    private final ComicVineService comicVineService;

    @Inject
    public ComicRemoteDataHelper(ComicVineService comicVineService) {
        this.comicVineService = comicVineService;
    }

    /**
     * Request issues list (search by: current date).
     *
     * @param date Date string in YYYY-MM-DD format.
     * @return Issue info list.
     */
    public Single<List<IssueInfoList>> getIssuesListByDate(String date) {

        String fields = ClassUtils.getMethodsList(IssueInfoList.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("filter", "store_date:" + date);
        options.put("sort", "name:asc");
        options.put("format", "json");

        return comicVineService
                .getIssuesList(options)
                .compose(ScheduleUtils.applySchedulers())
                .map(ServerResponse::results)
                .singleOrError();

    }

    /**
     * Request issue details (search by: issue id).
     *
     * @param issueId Target issue id (!= issue number).
     * @return Detailed issue info.
     */
    public Single<IssueInfo> getIssueDetailsById(long issueId) {

        String fields = ClassUtils.getMethodsList(IssueInfo.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicVineService
                .getIssueDetails(issueId, options)
                .compose(ScheduleUtils.applySchedulers())
                .map(ServerResponse::results)
                .singleOrError();
    }

    /**
     * Request volumes list (search by: specified name)
     *
     * @param name Target volume name.
     * @return Volume info list.
     */
    public Single<List<VolumeInfoList>> getVolumesListByName(String name) {

        String fields = ClassUtils.getMethodsList(VolumeInfoList.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("filter", "name:" + name);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicVineService
                .getVolumesList(options)
                .compose(ScheduleUtils.applySchedulers())
                .map(ServerResponse::results)
                .singleOrError();
    }

    /**
     * Request volume details (search by: volume id).
     *
     * @param volumeId Target volume id.
     * @return Detailed volume info.
     */
    public Single<VolumeInfo> getVolumeDetailsById(long volumeId) {

        String fields = ClassUtils.getMethodsList(VolumeInfo.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicVineService
                .getVolumeDetails(volumeId, options)
                .compose(ScheduleUtils.applySchedulers())
                .map(ServerResponse::results)
                .singleOrError();

    }

    /**
     * Request characters list (search by: specified name)
     *
     * @param name Target character name to perform search.
     * @return Characters info list.
     */
    public Single<List<CharacterInfoList>> getCharactersListByName(String name) {

        String fields = ClassUtils.getMethodsList(CharacterInfoList.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("filter", "name:" + name);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicVineService
                .getCharactersList(options)
                .compose(ScheduleUtils.applySchedulers())
                .map(ServerResponse::results)
                .singleOrError();
    }

    /**
     * Request character details (search by: character id)
     *
     * @param characterId Target character ud.
     * @return Detailed character info.
     */
    public Single<CharacterInfo> getCharacterDetailsById(long characterId) {

        String fields = ClassUtils.getMethodsList(CharacterInfo.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicVineService
                .getCharacterDetails(characterId, options)
                .compose(ScheduleUtils.applySchedulers())
                .map(ServerResponse::results)
                .singleOrError();
    }
}