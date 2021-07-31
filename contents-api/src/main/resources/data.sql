create schema if not exists pinstagram;

create table if not exists account
(
    account_id       bigint auto_increment
    primary key,
    email    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null
    );


create table if not exists contents
(
    contents_id  bigint auto_increment
    primary key,
    create_at    datetime     null,
    description  varchar(255) not null,
    full_address varchar(255) not null,
    lat          double       not null,
    lng          double       not null,
    picture      varchar(255) not null,
    tag          varchar(255) not null,
    title        varchar(20)  not null,
    update_at    datetime     null,
    account_id   bigint       null,
    constraint FK18mrxlh28cr7tr70wsubxhltl
    foreign key (account_id) references account (account_id)
    );

INSERT INTO account (account_id, email, name, password) VALUES (1, 'test@test.com', '김테스트', 'test1234');
