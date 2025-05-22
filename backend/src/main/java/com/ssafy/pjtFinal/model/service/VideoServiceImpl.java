package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.model.dao.VideoDao;
import com.ssafy.pjtFinal.model.dto.SearchCondition;
import com.ssafy.pjtFinal.model.dto.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    // 의존성 주입
    VideoDao videoDao;
    ResourceLoader resLoader;

    @Autowired
    public VideoServiceImpl(VideoDao videoDao, ResourceLoader resLoader) {
        this.videoDao = videoDao;
        this.resLoader = resLoader;
    }

    // 로직
    @Override
    @Transactional
    public void videoAdd(Video video, MultipartFile file) {
        Resource res = resLoader.getResource("classpath:/static/upload");

        try {
            String orgName = file.getOriginalFilename();
            String unqName = System.currentTimeMillis() + "_" + orgName;
            file.transferTo(new File(res.getFile(), unqName));
            video.setOrgImgName(orgName);
            video.setUnqImgName(unqName);
            videoDao.videoInsert(video);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    @Override
    public List<Video> getVideoAll() {
        return videoDao.videoSelectAll();
    }

    @Override
    public Video getVideo(long videoId) {
        return videoDao.videoSelectOne(videoId);
    }

    @Override
    public List<Video> searchVideo(SearchCondition condition) {
        return videoDao.videoSearch(condition);
    }

    @Override
    @Transactional
    public void videoModify(Video video, MultipartFile file) {
        Resource res = resLoader.getResource("classpath:/static/upload");

        try {
            String orgName = file.getOriginalFilename();
            String unqName = System.currentTimeMillis() + "_" + orgName;
            video.setOrgImgName(orgName);
            video.setUnqImgName(unqName);
            file.transferTo(new File(res.getFile(), unqName));
            videoDao.videoUpdate(video);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    @Transactional
    public void videoRemove(long videoId) {
        videoDao.videoDelete(videoId);
    }

    @Override
    public void videoUpdateViewCnt(long videoId) {
        videoDao.videoUpdateViewCount(videoId);
    }
}
