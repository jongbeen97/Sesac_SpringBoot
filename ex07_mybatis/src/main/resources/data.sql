-- data.sql(데이터 정의) insert

-- 사용자 초기 Mock 데이터 
INSERT INTO users (email, nickname) VALUES
('user1@example.com' ,'user1'),
('user2@example.com' ,'user2'),
('user3@example.com' ,'user3');

-- 게시글 초기 Mock 데이터 
INSERT INTO posts (user_id,title,content) VALUES
(1, '스프링 스터디 모집', '함께 공부하실 분 모집합니다.!!'),
(1, 'MyBatis 알려주실 분 살려주세요', 'MyBatis알려주시면 점심 사드려요 !!! '),
(2, 'RestAPI 고도화 전략 스터디', 'HTTP 상태코드와 에러 응답의 표준을 정합니다 ! ');