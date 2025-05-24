-- DB 초기화
DROP DATABASE IF EXISTS pjt_final;
CREATE DATABASE pjt_final DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pjt_final;

-- 사용자 테이블
CREATE TABLE user
(
    user_id        VARCHAR(50) PRIMARY KEY,
    user_pw        VARCHAR(255) NOT NULL,
    user_nick_name VARCHAR(50)  NOT NULL,
    user_email     VARCHAR(100) NOT NULL,
    user_birth     DATE,
    user_phone     VARCHAR(20),
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    follower_cnt   int      DEFAULT 0,
    followee_cnt   int      DEFAULT 0
);

-- 비디오 테이블
CREATE TABLE video
(
    video_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    video_title        VARCHAR(255) NOT NULL,
    video_part         VARCHAR(100) NOT NULL,
    video_url          VARCHAR(255) NOT NULL,
    video_view_cnt     INT    DEFAULT 0,
    video_like_cnt     BIGINT DEFAULT 0,
    video_channel_name VARCHAR(100) NOT NULL,
    org_img_name       VARCHAR(255) NOT NULL,
    unq_img_name       VARCHAR(255) NOT NULL
);

-- 리뷰 테이블
CREATE TABLE review
(
    review_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    video_id              BIGINT      NOT NULL,
    review_user_id        VARCHAR(50) NOT NULL,
    review_user_nick_name VARCHAR(50) NOT NULL,
    review_content        TEXT        NOT NULL,
    review_regist_date    DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (video_id) REFERENCES video (video_id) ON DELETE CASCADE
);

-- 좋아요 테이블
CREATE TABLE video_like
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id  VARCHAR(50) NOT NULL,
    video_id BIGINT      NOT NULL,
    liked_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, video_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (video_id) REFERENCES video (video_id) ON DELETE CASCADE
);

-- 팔로우 테이블
CREATE TABLE follow
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower    VARCHAR(50) NOT NULL,
    followee    VARCHAR(50) NOT NULL,
    followed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_follow_follower FOREIGN KEY (follower) REFERENCES user (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_follow_followee FOREIGN KEY (followee) REFERENCES user (user_id) ON DELETE CASCADE
);

INSERT INTO user (user_id, user_pw, user_nick_name, user_email, user_phone, created_at, follower_cnt, followee_cnt)
VALUES ('user0', 'pw0encrypted', 'nickname0', 'user0@mail.com', '010-1234-5600', '2024-10-27 00:00:00', 8, 7),
       ('user1', 'pw1encrypted', 'nickname1', 'user1@mail.com', '010-1234-5601', '2024-01-06 00:00:00', 0, 2),
       ('user2', 'pw2encrypted', 'nickname2', 'user2@mail.com', '010-1234-5602', '2024-03-17 00:00:00', 4, 1),
       ('user3', 'pw3encrypted', 'nickname3', 'user3@mail.com', '010-1234-5603', '2023-02-02 00:00:00', 1, 8),
       ('user4', 'pw4encrypted', 'nickname4', 'user4@mail.com', '010-1234-5604', '2023-07-09 00:00:00', 4, 5),
       ('user5', 'pw5encrypted', 'nickname5', 'user5@mail.com', '010-1234-5605', '2023-05-25 00:00:00', 3, 7),
       ('user6', 'pw6encrypted', 'nickname6', 'user6@mail.com', '010-1234-5606', '2024-10-24 00:00:00', 8, 2),
       ('user7', 'pw7encrypted', 'nickname7', 'user7@mail.com', '010-1234-5607', '2023-03-28 00:00:00', 2, 0),
       ('user8', 'pw8encrypted', 'nickname8', 'user8@mail.com', '010-1234-5608', '2023-07-30 00:00:00', 0, 3),
       ('user9', 'pw9encrypted', 'nickname9', 'user9@mail.com', '010-1234-5609', '2024-03-25 00:00:00', 6, 3);

INSERT INTO video (video_id, video_title, video_part, video_url, video_view_cnt, video_like_cnt, video_channel_name,
                   org_img_name, unq_img_name)
VALUES (1, '하루 15분! 전신 칼로리 불태우는 다이어트 운동', '전신운동', 'https://www.youtube.com/watch?v=swRNeYw1JkY', 1000000, 50000, '디글',
        'original1.jpg', 'unique_1_1234.jpg'),
       (2, '자극 최고! 덤벨 30분 기본 전신운동 홈트(상하체 통합편)', '전신운동', 'https://m.youtube.com/watch?v=L2-74-K-9VM', 800000, 40000,
        '빵그리', 'original2.jpg', 'unique_2_5678.jpg'),
       (3, '1시간 유산소 운동 효과 만들어주는 다이어트 댄스', '유산소운동', 'https://www.youtube.com/watch?v=e2RZlKLOv28', 600000, 30000, '맘즈헬스',
        'original3.jpg', 'unique_3_9101.jpg'),
       (4, '집에서 칼로리 소모 폭탄 걷기 운동 [칼소폭3]', '유산소운동', 'https://www.youtube.com/watch?v=MWnD6DhLjyc', 400000, 20000, '땅끄부부',
        'original4.jpg', 'unique_4_1121.jpg'),
       (5, '25 MINUTE Pilates that you have to do every day', '필라테스', 'https://www.youtube.com/watch?v=sb51gF18cYo',
        200000, 10000, 'Pilates Channel', 'original5.jpg', 'unique_5_3141.jpg');

INSERT INTO review (review_id, video_id, review_user_id, review_user_nick_name, review_content, review_regist_date)
VALUES (1, 3, 'user0', 'nickname4', '운동 리뷰 1번입니다. 효과 좋아요!', '2023-09-16 00:00:00'),
       (2, 1, 'user7', 'nickname3', '운동 리뷰 2번입니다. 효과 좋아요!', '2023-04-25 00:00:00'),
       (3, 2, 'user2', 'nickname4', '운동 리뷰 3번입니다. 효과 좋아요!', '2023-12-06 00:00:00'),
       (4, 4, 'user2', 'nickname3', '운동 리뷰 4번입니다. 효과 좋아요!', '2023-11-20 00:00:00'),
       (5, 1, 'user4', 'nickname6', '운동 리뷰 5번입니다. 효과 좋아요!', '2023-05-24 00:00:00'),
       (6, 2, 'user2', 'nickname5', '운동 리뷰 6번입니다. 효과 좋아요!', '2024-08-02 00:00:00'),
       (7, 3, 'user7', 'nickname9', '운동 리뷰 7번입니다. 효과 좋아요!', '2023-03-23 00:00:00'),
       (8, 3, 'user3', 'nickname1', '운동 리뷰 8번입니다. 효과 좋아요!', '2024-09-03 00:00:00'),
       (9, 5, 'user3', 'nickname9', '운동 리뷰 9번입니다. 효과 좋아요!', '2024-12-06 00:00:00'),
       (10, 4, 'user0', 'nickname4', '운동 리뷰 10번입니다. 효과 좋아요!', '2023-03-18 00:00:00'),
       (11, 3, 'user8', 'nickname6', '운동 리뷰 11번입니다. 효과 좋아요!', '2024-04-16 00:00:00'),
       (12, 2, 'user4', 'nickname8', '운동 리뷰 12번입니다. 효과 좋아요!', '2023-12-31 00:00:00'),
       (13, 3, 'user2', 'nickname4', '운동 리뷰 13번입니다. 효과 좋아요!', '2024-03-19 00:00:00'),
       (14, 4, 'user9', 'nickname0', '운동 리뷰 14번입니다. 효과 좋아요!', '2024-03-08 00:00:00'),
       (15, 1, 'user8', 'nickname8', '운동 리뷰 15번입니다. 효과 좋아요!', '2023-06-08 00:00:00'),
       (16, 3, 'user6', 'nickname3', '운동 리뷰 16번입니다. 효과 좋아요!', '2023-05-28 00:00:00'),
       (17, 5, 'user2', 'nickname8', '운동 리뷰 17번입니다. 효과 좋아요!', '2023-03-14 00:00:00'),
       (18, 1, 'user5', 'nickname3', '운동 리뷰 18번입니다. 효과 좋아요!', '2024-07-13 00:00:00'),
       (19, 2, 'user5', 'nickname9', '운동 리뷰 19번입니다. 효과 좋아요!', '2023-06-29 00:00:00'),
       (20, 3, 'user6', 'nickname5', '운동 리뷰 20번입니다. 효과 좋아요!', '2023-06-16 00:00:00'),
       (21, 4, 'user3', 'nickname8', '운동 리뷰 21번입니다. 효과 좋아요!', '2023-02-10 00:00:00'),
       (22, 5, 'user7', 'nickname7', '운동 리뷰 22번입니다. 효과 좋아요!', '2023-05-24 00:00:00'),
       (23, 2, 'user4', 'nickname0', '운동 리뷰 23번입니다. 효과 좋아요!', '2024-06-06 00:00:00'),
       (24, 2, 'user0', 'nickname3', '운동 리뷰 24번입니다. 효과 좋아요!', '2024-11-19 00:00:00'),
       (25, 4, 'user8', 'nickname9', '운동 리뷰 25번입니다. 효과 좋아요!', '2023-11-22 00:00:00'),
       (26, 5, 'user1', 'nickname6', '운동 리뷰 26번입니다. 효과 좋아요!', '2024-06-14 00:00:00'),
       (27, 2, 'user3', 'nickname4', '운동 리뷰 27번입니다. 효과 좋아요!', '2024-10-01 00:00:00'),
       (28, 5, 'user2', 'nickname1', '운동 리뷰 28번입니다. 효과 좋아요!', '2023-09-10 00:00:00'),
       (29, 2, 'user9', 'nickname2', '운동 리뷰 29번입니다. 효과 좋아요!', '2024-05-20 00:00:00'),
       (30, 5, 'user7', 'nickname5', '운동 리뷰 30번입니다. 효과 좋아요!', '2023-08-15 00:00:00');

INSERT INTO video_like (id, user_id, video_id, liked_at)
VALUES (1, 'user5', 1, '2023-08-12 00:00:00'),
       (2, 'user5', 4, '2023-04-07 00:00:00'),
       (3, 'user4', 4, '2023-11-02 00:00:00'),
       (4, 'user8', 1, '2023-02-07 00:00:00'),
       (5, 'user8', 4, '2024-11-21 00:00:00'),
       (6, 'user0', 1, '2023-05-27 00:00:00'),
       (7, 'user6', 2, '2023-08-29 00:00:00'),
       (8, 'user3', 1, '2023-05-29 00:00:00'),
       (9, 'user1', 2, '2023-11-21 00:00:00'),
       (10, 'user1', 5, '2024-06-16 00:00:00'),
       (11, 'user5', 3, '2023-09-07 00:00:00'),
       (12, 'user2', 2, '2023-03-23 00:00:00'),
       (13, 'user2', 5, '2024-08-08 00:00:00'),
       (14, 'user7', 2, '2024-09-23 00:00:00'),
       (15, 'user7', 5, '2024-02-10 00:00:00'),
       (16, 'user0', 3, '2023-04-15 00:00:00'),
       (17, 'user9', 2, '2023-01-22 00:00:00'),
       (18, 'user6', 4, '2024-12-13 00:00:00'),
       (19, 'user3', 3, '2024-09-25 00:00:00'),
       (20, 'user1', 4, '2023-02-22 00:00:00'),
       (21, 'user5', 2, '2023-10-17 00:00:00'),
       (22, 'user5', 5, '2024-11-28 00:00:00'),
       (23, 'user1', 3, '2024-07-06 00:00:00'),
       (24, 'user4', 2, '2024-04-20 00:00:00'),
       (25, 'user8', 2, '2024-04-09 00:00:00'),
       (26, 'user0', 5, '2023-04-16 00:00:00'),
       (27, 'user0', 2, '2023-03-31 00:00:00'),
       (28, 'user3', 2, '2024-12-14 00:00:00'),
       (29, 'user9', 4, '2024-11-28 00:00:00'),
       (30, 'user3', 5, '2024-10-31 00:00:00');

INSERT INTO follow (id, follower, followee, followed_at)
VALUES (1, 'user2', 'user7', '2023-07-20 00:00:00'),
       (2, 'user5', 'user4', '2024-01-18 00:00:00'),
       (3, 'user8', 'user2', '2024-10-06 00:00:00'),
       (4, 'user8', 'user3', '2024-07-02 00:00:00'),
       (5, 'user0', 'user2', '2023-12-05 00:00:00'),
       (6, 'user8', 'user4', '2023-03-24 00:00:00'),
       (7, 'user0', 'user1', '2024-05-01 00:00:00'),
       (8, 'user9', 'user8', '2024-12-10 00:00:00'),
       (9, 'user3', 'user4', '2024-12-02 00:00:00'),
       (10, 'user0', 'user6', '2024-03-22 00:00:00'),
       (11, 'user6', 'user5', '2024-07-29 00:00:00'),
       (12, 'user6', 'user2', '2023-04-18 00:00:00'),
       (13, 'user1', 'user4', '2024-09-07 00:00:00'),
       (14, 'user2', 'user9', '2024-11-05 00:00:00'),
       (15, 'user2', 'user1', '2023-09-27 00:00:00'),
       (16, 'user7', 'user5', '2024-10-27 00:00:00'),
       (17, 'user7', 'user1', '2023-10-26 00:00:00'),
       (18, 'user9', 'user2', '2024-05-27 00:00:00'),
       (19, 'user2', 'user6', '2024-06-04 00:00:00'),
       (20, 'user5', 'user9', '2023-01-22 00:00:00');