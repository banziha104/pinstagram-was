create schema if not exists app;

create table if not exists author
(
    author_id bigint auto_increment
    primary key,
    name      varchar(255) null
    );

create table if not exists book
(
    book_id     bigint auto_increment
    primary key,
    description varchar(255) null,
    name        varchar(255) null,
    tag         varchar(255) null,
    thumbnail   varchar(255) null,
    author_id   bigint       null,
    constraint FKklnrv3weler2ftkweewlky958
    foreign key (author_id) references author (author_id)
    );

create table if not exists episode
(
    episode_id     bigint auto_increment
    primary key,
    create_at      datetime     null,
    episode_number bigint       null,
    name           varchar(255) null,
    thumbnail      varchar(255) null,
    book_id        bigint       null,
    constraint FKhb80e2v5n2k9316s5ppyagbt1
    foreign key (book_id) references book (book_id)
    );

create table if not exists scene
(
    scene_id   bigint auto_increment
    primary key,
    image      varchar(255) null,
    episode_id bigint       null,
    constraint FKo2kk0w6odvi91q0u2gos2dg4m
    foreign key (episode_id) references episode (episode_id)
    );



INSERT INTO author (author_id, name) VALUES (1, '쿠사나기 미즈호');
INSERT INTO author (author_id, name) VALUES (2, '야마자키 코레');
INSERT INTO author (author_id, name) VALUES (3, '호시코시 코헤이');
INSERT INTO author (author_id, name) VALUES (4, '야타테 하지메');
INSERT INTO author (author_id, name) VALUES (5, '마츠이 유세이');

INSERT INTO book (book_id, description, name, tag, thumbnail, author_id) VALUES (1, '고화국의 공주인 연화는 일 왕의 보호 아래 세상 일은 전혀 모른 채 항상 성 안에서 지내고 있었으며 그녀의 소꿉친구로는 학과 수원이 있었다. 그러나 부왕 일이 소꿉친구이자 짝사랑 상대였던 사촌 수원의 손에 살해 당하고, 이 광경을 목격한 연화는 16살의 생일에 일생 가장 큰 위기를 겪게 된다.', '새벽의 연화', '드라마 액션 판타지', 'https://storage.cloud.google.com/manhwatest/1/thumbnail.jpeg', 1);
INSERT INTO book (book_id, description, name, tag, thumbnail, author_id) VALUES (2, '선천적으로 인간이 아닌 것을 볼 수 있었던 탓에 주위로부터도 가족으로부터도 소외되어 불행과 고독 속에서 살아온 일본인 소녀 하토리 치세. 그녀는 자포자기의 심정으로 영국으로 가 경매에 자신을 상품으로서 판매한다.', '마법사의 신부', '로맨스 판타지', 'https://storage.cloud.google.com/manhwatest/2/thumbnail.jpeg', 2);
INSERT INTO book (book_id, description, name, tag, thumbnail, author_id) VALUES (3, '전 세계 총인구 약 80%가 개성이라고 불리는 초능력을 가지고 있는 허구의 세계. 그 곳에선 개성을 악용하는 빌런이라는 자들이 있다. 그런 빌런을 잡기 위하여 개성을 쓰는, 히어로라는 직업이 관심을 받고 있다. 하지만 그런 세계에서도 개성이 없는 사람, 일명 무개성이 존재한다. ', '나의 히어로 아카데미아', '드라마 액션 판타지', 'https://storage.cloud.google.com/manhwatest/3/thumbnail.jpeg', 3);
INSERT INTO book (book_id, description, name, tag, thumbnail, author_id) VALUES (4, '이야기는 2071년 화성에서 시작한다. 대대적인 할로윈 축제를 앞둔 알파시티에서 끔찍한 탱크차 폭발 사고가 발생한다. 수백명의 사상자가 속출하지만, 죽음의 원인은 오리무중이다. 흔적을 남기지 않는 미지의 바이러스?', '카우보이 비밥', '느와르 SF 판타지', 'https://storage.cloud.google.com/manhwatest/4/thumbnail.jpeg', 4);
INSERT INTO book (book_id, description, name, tag, thumbnail, author_id) VALUES (5, '쿠누기가오카 중학교 최악의 반 3-E반에 정체불명의 문어 모양의 괴물이 담임선생님이라며 나타났다. 정부는 E반 학생들에게 3월에 세계를 파괴하겠다고 협박하는 일명 ''살생님''이라는 괴물을 암살하라는 비밀 지령을 내린다.', '암살교실', '드라마 액션 판타지', 'https://storage.cloud.google.com/manhwatest/5/thumbnail.jpeg', 5);

INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (1, '2021-07-26 14:41:52', 1, '새벽의 연화 1화', 1, 'https://storage.cloud.google.com/manhwatest/1/1/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (2, '2021-07-26 14:42:14', 2, '새벽의 연화 2화', 1, 'https://storage.cloud.google.com/manhwatest/1/2/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (3, '2021-07-26 14:42:14', 3, '새벽의 연화 3화', 1, 'https://storage.cloud.google.com/manhwatest/1/3/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (4, '2021-07-26 14:42:15', 1, '마법사의 신부 1화', 2, 'https://storage.cloud.google.com/manhwatest/2/1/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (5, '2021-07-26 14:42:16', 2, '마법사의 신부 2화', 2, 'https://storage.cloud.google.com/manhwatest/2/2/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (6, '2021-07-26 14:42:17', 3, '마법사의 신부 3화', 2, 'https://storage.cloud.google.com/manhwatest/2/3/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (7, '2021-07-26 14:42:18', 1, '나히아 1화', 3, 'https://storage.cloud.google.com/manhwatest/3/1/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (8, '2021-07-26 14:42:18', 2, '나히아 2화', 3, 'https://storage.cloud.google.com/manhwatest/3/2/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (9, '2021-07-26 14:42:19', 3, '나히아 3화', 3, 'https://storage.cloud.google.com/manhwatest/3/3/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (10, '2021-07-26 14:42:20', 1, '카우보이 비밥 1화', 4, 'https://storage.cloud.google.com/manhwatest/4/1/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (11, '2021-07-26 14:42:21', 2, '카우보이 비밥 2화', 4, 'https://storage.cloud.google.com/manhwatest/4/2/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (12, '2021-07-26 14:42:22', 3, '카우보이 비밥 3화', 4, 'https://storage.cloud.google.com/manhwatest/4/3/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (13, '2021-07-26 14:42:23', 1, '암살교실 1화', 5, 'https://storage.cloud.google.com/manhwatest/5/1/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (14, '2021-07-26 14:42:23', 2, '암살교실 2화', 5, 'https://storage.cloud.google.com/manhwatest/5/2/thumbnail.jpeg');
INSERT INTO episode (episode_id, create_at, episode_number, name, book_id, thumbnail) VALUES (15, '2021-07-26 14:42:24', 3, '암살교실 3화', 5, 'https://storage.cloud.google.com/manhwatest/5/3/thumbnail.jpeg');

INSERT INTO scene (scene_id, image, episode_id) VALUES (1, 'https://storage.cloud.google.com/manhwatest/1/1/1.png', 1);
INSERT INTO scene (scene_id, image, episode_id) VALUES (2, 'https://storage.cloud.google.com/manhwatest/1/1/2.png', 1);
INSERT INTO scene (scene_id, image, episode_id) VALUES (3, 'https://storage.cloud.google.com/manhwatest/1/1/3.png', 1);
INSERT INTO scene (scene_id, image, episode_id) VALUES (4, 'https://storage.cloud.google.com/manhwatest/1/1/4.png', 1);
INSERT INTO scene (scene_id, image, episode_id) VALUES (5, 'https://storage.cloud.google.com/manhwatest/1/1/5.png', 1);
INSERT INTO scene (scene_id, image, episode_id) VALUES (6, 'https://storage.cloud.google.com/manhwatest/1/2/1.png', 2);
INSERT INTO scene (scene_id, image, episode_id) VALUES (7, 'https://storage.cloud.google.com/manhwatest/1/2/2.png', 2);
INSERT INTO scene (scene_id, image, episode_id) VALUES (8, 'https://storage.cloud.google.com/manhwatest/1/2/3.png', 2);
INSERT INTO scene (scene_id, image, episode_id) VALUES (9, 'https://storage.cloud.google.com/manhwatest/1/2/4.png', 2);
INSERT INTO scene (scene_id, image, episode_id) VALUES (10, 'https://storage.cloud.google.com/manhwatest/1/2/5.png', 2);
INSERT INTO scene (scene_id, image, episode_id) VALUES (11, 'https://storage.cloud.google.com/manhwatest/1/3/1.png', 3);
INSERT INTO scene (scene_id, image, episode_id) VALUES (12, 'https://storage.cloud.google.com/manhwatest/1/3/2.png', 3);
INSERT INTO scene (scene_id, image, episode_id) VALUES (13, 'https://storage.cloud.google.com/manhwatest/1/3/3.png', 3);
INSERT INTO scene (scene_id, image, episode_id) VALUES (14, 'https://storage.cloud.google.com/manhwatest/1/3/4.png', 3);
INSERT INTO scene (scene_id, image, episode_id) VALUES (15, 'https://storage.cloud.google.com/manhwatest/1/3/5.png', 3);
INSERT INTO scene (scene_id, image, episode_id) VALUES (16, 'https://storage.cloud.google.com/manhwatest/2/1/1.png', 4);
INSERT INTO scene (scene_id, image, episode_id) VALUES (17, 'https://storage.cloud.google.com/manhwatest/2/1/2.png', 4);
INSERT INTO scene (scene_id, image, episode_id) VALUES (18, 'https://storage.cloud.google.com/manhwatest/2/1/3.png', 4);
INSERT INTO scene (scene_id, image, episode_id) VALUES (19, 'https://storage.cloud.google.com/manhwatest/2/1/4.png', 4);
INSERT INTO scene (scene_id, image, episode_id) VALUES (20, 'https://storage.cloud.google.com/manhwatest/2/1/5.png', 4);
INSERT INTO scene (scene_id, image, episode_id) VALUES (21, 'https://storage.cloud.google.com/manhwatest/2/2/1.png', 5);
INSERT INTO scene (scene_id, image, episode_id) VALUES (22, 'https://storage.cloud.google.com/manhwatest/2/2/2.png', 5);
INSERT INTO scene (scene_id, image, episode_id) VALUES (23, 'https://storage.cloud.google.com/manhwatest/2/2/3.png', 5);
INSERT INTO scene (scene_id, image, episode_id) VALUES (24, 'https://storage.cloud.google.com/manhwatest/2/2/4.png', 5);
INSERT INTO scene (scene_id, image, episode_id) VALUES (25, 'https://storage.cloud.google.com/manhwatest/2/2/5.png', 5);
INSERT INTO scene (scene_id, image, episode_id) VALUES (26, 'https://storage.cloud.google.com/manhwatest/2/3/1.png', 6);
INSERT INTO scene (scene_id, image, episode_id) VALUES (27, 'https://storage.cloud.google.com/manhwatest/2/3/2.png', 6);
INSERT INTO scene (scene_id, image, episode_id) VALUES (28, 'https://storage.cloud.google.com/manhwatest/2/3/3.png', 6);
INSERT INTO scene (scene_id, image, episode_id) VALUES (29, 'https://storage.cloud.google.com/manhwatest/2/3/4.png', 6);
INSERT INTO scene (scene_id, image, episode_id) VALUES (30, 'https://storage.cloud.google.com/manhwatest/2/3/5.png', 6);
INSERT INTO scene (scene_id, image, episode_id) VALUES (31, 'https://storage.cloud.google.com/manhwatest/3/1/1.png', 7);
INSERT INTO scene (scene_id, image, episode_id) VALUES (32, 'https://storage.cloud.google.com/manhwatest/3/1/2.png', 7);
INSERT INTO scene (scene_id, image, episode_id) VALUES (33, 'https://storage.cloud.google.com/manhwatest/3/1/3.png', 7);
INSERT INTO scene (scene_id, image, episode_id) VALUES (34, 'https://storage.cloud.google.com/manhwatest/3/1/4.png', 7);
INSERT INTO scene (scene_id, image, episode_id) VALUES (35, 'https://storage.cloud.google.com/manhwatest/3/1/5.png', 7);
INSERT INTO scene (scene_id, image, episode_id) VALUES (36, 'https://storage.cloud.google.com/manhwatest/3/2/1.png', 8);
INSERT INTO scene (scene_id, image, episode_id) VALUES (37, 'https://storage.cloud.google.com/manhwatest/3/2/2.png', 8);
INSERT INTO scene (scene_id, image, episode_id) VALUES (38, 'https://storage.cloud.google.com/manhwatest/3/2/3.png', 8);
INSERT INTO scene (scene_id, image, episode_id) VALUES (39, 'https://storage.cloud.google.com/manhwatest/3/2/3.png', 8);
INSERT INTO scene (scene_id, image, episode_id) VALUES (40, 'https://storage.cloud.google.com/manhwatest/3/2/5.png', 8);
INSERT INTO scene (scene_id, image, episode_id) VALUES (41, 'https://storage.cloud.google.com/manhwatest/3/3/1.png', 9);
INSERT INTO scene (scene_id, image, episode_id) VALUES (42, 'https://storage.cloud.google.com/manhwatest/3/3/2.png', 9);
INSERT INTO scene (scene_id, image, episode_id) VALUES (43, 'https://storage.cloud.google.com/manhwatest/3/3/3.png', 9);
INSERT INTO scene (scene_id, image, episode_id) VALUES (44, 'https://storage.cloud.google.com/manhwatest/3/3/4.png', 9);
INSERT INTO scene (scene_id, image, episode_id) VALUES (45, 'https://storage.cloud.google.com/manhwatest/3/3/5.png', 9);
INSERT INTO scene (scene_id, image, episode_id) VALUES (46, 'https://storage.cloud.google.com/manhwatest/4/1/1.png', 10);
INSERT INTO scene (scene_id, image, episode_id) VALUES (47, 'https://storage.cloud.google.com/manhwatest/4/1/2.png', 10);
INSERT INTO scene (scene_id, image, episode_id) VALUES (48, 'https://storage.cloud.google.com/manhwatest/4/1/3.png', 10);
INSERT INTO scene (scene_id, image, episode_id) VALUES (49, 'https://storage.cloud.google.com/manhwatest/4/1/4.png', 10);
INSERT INTO scene (scene_id, image, episode_id) VALUES (50, 'https://storage.cloud.google.com/manhwatest/4/1/5.png', 10);
INSERT INTO scene (scene_id, image, episode_id) VALUES (51, 'https://storage.cloud.google.com/manhwatest/4/2/1.png', 11);
INSERT INTO scene (scene_id, image, episode_id) VALUES (52, 'https://storage.cloud.google.com/manhwatest/4/2/2.png', 11);
INSERT INTO scene (scene_id, image, episode_id) VALUES (53, 'https://storage.cloud.google.com/manhwatest/4/2/3.png', 11);
INSERT INTO scene (scene_id, image, episode_id) VALUES (54, 'https://storage.cloud.google.com/manhwatest/4/2/3.png', 11);
INSERT INTO scene (scene_id, image, episode_id) VALUES (55, 'https://storage.cloud.google.com/manhwatest/4/2/5.png', 11);
INSERT INTO scene (scene_id, image, episode_id) VALUES (56, 'https://storage.cloud.google.com/manhwatest/4/3/1.png', 12);
INSERT INTO scene (scene_id, image, episode_id) VALUES (57, 'https://storage.cloud.google.com/manhwatest/4/3/2.png', 12);
INSERT INTO scene (scene_id, image, episode_id) VALUES (58, 'https://storage.cloud.google.com/manhwatest/4/3/3.png', 12);
INSERT INTO scene (scene_id, image, episode_id) VALUES (59, 'https://storage.cloud.google.com/manhwatest/4/3/4.png', 12);
INSERT INTO scene (scene_id, image, episode_id) VALUES (60, 'https://storage.cloud.google.com/manhwatest/4/3/5.png', 12);
INSERT INTO scene (scene_id, image, episode_id) VALUES (61, 'https://storage.cloud.google.com/manhwatest/5/1/1.png', 13);
INSERT INTO scene (scene_id, image, episode_id) VALUES (62, 'https://storage.cloud.google.com/manhwatest/5/1/2.png', 13);
INSERT INTO scene (scene_id, image, episode_id) VALUES (63, 'https://storage.cloud.google.com/manhwatest/5/1/3.png', 13);
INSERT INTO scene (scene_id, image, episode_id) VALUES (64, 'https://storage.cloud.google.com/manhwatest/5/1/4.png', 13);
INSERT INTO scene (scene_id, image, episode_id) VALUES (65, 'https://storage.cloud.google.com/manhwatest/5/1/5.png', 13);
INSERT INTO scene (scene_id, image, episode_id) VALUES (66, 'https://storage.cloud.google.com/manhwatest/5/2/1.png', 14);
INSERT INTO scene (scene_id, image, episode_id) VALUES (67, 'https://storage.cloud.google.com/manhwatest/5/2/2.png', 14);
INSERT INTO scene (scene_id, image, episode_id) VALUES (68, 'https://storage.cloud.google.com/manhwatest/5/2/3.png', 14);
INSERT INTO scene (scene_id, image, episode_id) VALUES (69, 'https://storage.cloud.google.com/manhwatest/5/2/3.png', 14);
INSERT INTO scene (scene_id, image, episode_id) VALUES (70, 'https://storage.cloud.google.com/manhwatest/5/2/5.png', 14);
INSERT INTO scene (scene_id, image, episode_id) VALUES (71, 'https://storage.cloud.google.com/manhwatest/5/3/1.png', 15);
INSERT INTO scene (scene_id, image, episode_id) VALUES (72, 'https://storage.cloud.google.com/manhwatest/5/3/2.png', 15);
INSERT INTO scene (scene_id, image, episode_id) VALUES (73, 'https://storage.cloud.google.com/manhwatest/5/3/3.png', 15);
INSERT INTO scene (scene_id, image, episode_id) VALUES (74, 'https://storage.cloud.google.com/manhwatest/5/3/4.png', 15);
INSERT INTO scene (scene_id, image, episode_id) VALUES (75, 'https://storage.cloud.google.com/manhwatest/5/3/5.png', 15);