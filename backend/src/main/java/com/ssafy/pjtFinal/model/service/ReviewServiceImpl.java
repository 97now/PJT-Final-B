package com.ssafy.pjtFinal.model.service;

import com.ssafy.pjtFinal.model.dao.ReviewDao;
import com.ssafy.pjtFinal.model.dto.Review;
import com.ssafy.pjtFinal.model.dto.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    // 의존성 주입
    ReviewDao reviewDao;
    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao){
        this.reviewDao = reviewDao;
    }

    ////////////////////////////////
    // 리뷰 추가
    @Override
    public void reviewAdd(Review review) {
        reviewDao.reviewInsert(review);
    }

    // 비디오, 유저 아이디 등 특정 조건에 따른 리뷰 목록 가져오기
    @Override
    public List<Review> getReviewList(SearchCondition condition) {
        return reviewDao.reviewSelectAllByCondition(condition);
    }

    // 모든 영상에 등록된 모든 리뷰 가져오기
    @Override
    public List<Review> getAllReviewList() {
        return reviewDao.reviewSelectAll();
    }

    // 특정 리뷰 선택하기
    @Override
    public Review getReview(int reviewId) {
        return reviewDao.reviewSelectOne(reviewId);
    }

    // 리뷰 수정
    @Override
    public void reviewModify(Review review) {
        reviewDao.reviewUpdate(review);
    }

    // 리뷰 삭제
    @Override
    public void reviewRemove(int reviewId) {
        reviewDao.reviewDelete(reviewId);
    }
}
