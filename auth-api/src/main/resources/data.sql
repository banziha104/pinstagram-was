create schema if not exists pinstagram;

create table if not exists account
(
    account_id       bigint auto_increment
    primary key,
    email    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null
    );
