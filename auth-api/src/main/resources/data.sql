create schema if not exists authentication;

create table if not exists account
(
    id       bigint auto_increment
    primary key,
    email    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null
    );

INSERT INTO account (id, email, name, password) VALUES (1, 'test@test.com', null, 'test');