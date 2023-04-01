-- liquibase formatted sql

-- changeset lumlum:1
create table cat
(
    id            integer PRIMARY KEY not null,
    breed         varchar             not null,
    "name"        varchar             not null,
    year_of_birth integer             not null,
    description   varchar
);
