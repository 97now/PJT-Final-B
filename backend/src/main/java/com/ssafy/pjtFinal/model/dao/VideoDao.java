package com.ssafy.pjtFinal.model.dao;

import com.ssafy.pjtFinal.model.dto.SearchCondition;
import com.ssafy.pjtFinal.model.dto.Video;

import java.util.List;

public interface VideoDao {
    void videoInsert(Video video);
    List<Video> videoSelectAll();
    Video videoSelectOne(long videoId);
    void videoUpdate(Video video);
    void videoDelete(long videoId);
    List<Video> videoSearch(SearchCondition condition);
    void videoUpdateViewCount(long videoId);


}
