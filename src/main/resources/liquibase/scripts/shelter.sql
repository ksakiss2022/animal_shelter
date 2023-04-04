-- liquibase formatted sql

-- changeset lumlum:1
create table if not exists cat
(
    id            BIGINT PRIMARY KEY not null,
    breed         varchar             not null,
    name_cat        varchar             not null,
    year_of_birth integer             not null,
    description   varchar
);

-- changeset skorobeynikova:2


CREATE SCHEMA IF NOT EXISTS dog_database;
CREATE TABLE IF NOT EXISTS dog
(
    id                BIGINT generated by default as identity primary key,
    breed             TEXT NOT NULL,
    name_dog          TEXT NOT NULL,
    year_of_birth_dog INT  NOT NULL,
    description       TEXT NOT NULL

);

CREATE SCHEMA IF NOT EXISTS person_cat;
CREATE TABLE IF NOT EXISTS person_cat
(
    id            BIGINT generated by default as identity primary key,
    name          TEXT   NOT NULL,
    phone         TEXT   NOT NULL,
    year_of_birth INT    NOT NULL,
    mail          TEXT   NOT NULL,
    address       TEXT   NOT NULL,
    chat_id       BIGINT NOT NULL
);

CREATE SCHEMA IF NOT EXISTS person_dog;
CREATE TABLE IF NOT EXISTS person_dog
(
    id            BIGINT generated by default as identity primary key,
    name          TEXT   NOT NULL,
    phone         TEXT   NOT NULL,
    year_of_birth INT    NOT NULL,
    mail          TEXT   NOT NULL,
    address       TEXT   NOT NULL,
    chat_id       BIGINT NOT NULL
);




