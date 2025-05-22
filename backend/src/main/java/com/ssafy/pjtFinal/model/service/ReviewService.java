package com.ssafy.pjtFinal.model.service;

import java.util.*;
import com.ssafy.pjtFinal.model.dto.Review;
import com.ssafy.pjtFinal.model.dto.SearchCondition;

public interface ReviewService {
    // 리뷰 추가
    void reviewAdd(Review review);

    // 비디오, 유저 아이디 등 특정 조건에 따른 리뷰 목록 가져오기
    List<Review> getReviewList(SearchCondition condition);

    // 등록된 모든 리뷰 리스트
    List<Review> getAllReviewList();

    // 리뷰 하나
    Review getReview(int reviewId);
    
    // 리뷰 수정
    void reviewModify(Review review);
    
    // 리뷰 삭제
    void reviewRemove(int reviewId);
}
