package com.ssafy.pjtFinal.model.dao;

import com.ssafy.pjtFinal.model.dto.Review;
import com.ssafy.pjtFinal.model.dto.SearchCondition;

import java.util.List;

public interface ReviewDao {
    void reviewInsert(Review review);
    List<Review> reviewSelectAllByCondition(SearchCondition condition);
    List<Review> reviewSelectAll();
    Review reviewSelectOne(int reviewId);
    void reviewUpdate(Review review);
    void reviewDelete(int reviewId);
}
