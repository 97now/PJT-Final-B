package com.ssafy.pjtFinal.controller;

import com.ssafy.pjtFinal.error.CustomException;
import com.ssafy.pjtFinal.error.ErrorCode;
import com.ssafy.pjtFinal.model.dto.Review;
import com.ssafy.pjtFinal.model.dto.SearchCondition;
import com.ssafy.pjtFinal.model.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewServiceImpl) {
        reviewService = reviewServiceImpl;
    }

    // 리뷰 추가
    @PostMapping
    public ResponseEntity<Review> addReview(@Valid @RequestBody Review review) {
        reviewService.reviewAdd(review);
        URI location = URI.create("/api/review/" + review.getReviewId());
        return ResponseEntity.created(location).body(review);
    }

    // 비디오, 유저 아이디 등 특정 조건에 따른 리뷰 목록 가져오기
    @GetMapping
    public ResponseEntity<List<Review>> getReviewListByCondition(@RequestBody SearchCondition condition){
        List<Review> reviewList = reviewService.getReviewList(condition);
        return ResponseEntity.ok(reviewList);
    }

    // 전체 리뷰 조회
    @GetMapping("/all-review")
    public ResponseEntity<List<Review>> getAllReviewList() {
        List<Review> reviewList = reviewService.getAllReviewList();
        return ResponseEntity.ok(reviewList);
    }

    // 리뷰 한 개 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable int reviewId){
        Review review = reviewService.getReview(reviewId);
        if(review == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return ResponseEntity.ok(review);
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> modifyReview(@PathVariable int reviewId, @RequestBody Review review) {
        Review oldReview = reviewService.getReview(reviewId);
        if(oldReview == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        reviewService.reviewModify(review);
        return ResponseEntity.ok(review);
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable int reviewId){
        Review review = reviewService.getReview(reviewId);
        if(review == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        reviewService.reviewRemove(reviewId);
        return ResponseEntity.ok().build();
    }
}
