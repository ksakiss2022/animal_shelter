-- liquibase formatted sql

-- changeset lumlum:1
CREATE SCHEMA IF NOT EXISTS cat;
create table if not exists cat
(
    id            BIGINT generated by default as identity primary key,
    breed         TEXT             NOT NULL,
    name_cat       TEXT             NOT NULL,
    year_of_birth INT             NOT NULL,
    description   TEXT            NOT NULL
);


-- changeset skorobeynikova:2

CREATE SCHEMA IF NOT EXISTS dog;
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

-- changeset lumlum:3
ALTER TABLE person_dog
    ADD dog_id BIGINT NOT NULL UNIQUE;

ALTER TABLE person_cat
    ADD cat_id BIGINT NOT NULL UNIQUE;

ALTER TABLE person_dog
    ADD FOREIGN KEY (dog_id) REFERENCES dog (id);

ALTER TABLE person_cat
    ADD FOREIGN KEY (cat_id) REFERENCES cat (id);

-- changeset salimgareeva:4

CREATE SCHEMA IF NOT EXISTS shelter;
CREATE TABLE IF NOT EXISTS shelter
(
    id                      BIGINT generated by default as identity primary key,
    name                    TEXT NOT NULL,
    information             TEXT NOT NULL,
    schedule                TEXT NOT NULL,
    address                 TEXT NOT NULL,
    safety_recommendations  TEXT NOT NULL

);

-- changeset salimgareeva:5

CREATE SCHEMA IF NOT EXISTS location_map;
CREATE TABLE IF NOT EXISTS location_map
(
    id                      BIGINT generated by default as identity primary key,
    file_path               TEXT NOT NULL,
    file_size               BIGINT NOT NULL,
    media_type              TEXT NOT NULL,
    data                    BYTEA NOT NULL,
    shelter_id              BIGINT NOT NULL

);

-- changeset kulakov: 6

INSERT INTO cat (id, breed, name_cat, year_of_birth, description) VALUES
    (1, 'Шотландская вислоухая', 'Багира', 2020, 'няшная внешность никого не оставит равнодушным'),
    (2, 'Мейн-Кун', 'Агата', 2017, 'ласковый гигант с серьёзным взглядом'),
    (3, 'Британская короткошёрстная', 'Ватрушка', 2018, 'аристократичная, дружелюбная, харизматичная'),
    (4, 'Бенгальская', 'Десси', 2021, 'леопардовый окрас'),
    (5, 'Сиамская', 'Грация', 2022, 'грациозная восточная красавица'),
    (6, 'Русская голубая', 'Жемчужинка', 2023, 'пронзительные зелёные глаза'),
    (7, 'Сибирская', 'Забава', 2017, 'настоящая русская красавица');

INSERT INTO dog (id, breed, name_dog, year_of_birth_dog, description) VALUES
    (1, 'Пудель', 'Амур', 2020, 'французская порода, привлекается к работе в полиции'),
    (2, 'Немецкая овчарка', 'Охотник', 2017, 'прекрасный и верный друг семьи'),
    (3, 'Пекинес', 'Оливия', 2018, 'любимец китайских императоров'),
    (4, 'Чау-чау', 'Палаш', 2021, 'собака, принадлежащая династии Тан'),
    (5, 'Шарпей', 'Стингер', 2022, 'китайцы верят, что синий язык будет отгонять духов'),
    (6, 'Бигль', 'Мелани', 2015, 'умеет выполнять команду зайка'),
    (7, 'Далматинец', 'Терри', 2017, 'пятна появляются только через две недели после рождения');

INSERT INTO person_cat (id, name, phone, year_of_birth, mail, address, chat_id, cat_id) VALUES
    (1, 'Маргарита Салимгареева', '+79011111111', 2000, 'm.salimgareeva@mail.ru', 'Россия', 11111111, 1),
    (2, 'Светлана Коробейкина', '+79022222222', 2000, 's.korobeykina@mail.ru', 'Россия', 2222222, 3),
    (3, 'Мария Подгорная', '+79033333333', 1995, 'm.podgornaya@mail.ru', 'Россия', 3333333, 5),
    (4, 'Ирина Деревянкина', '+79044444444', 1985, 'i.derevyankina@mail.ru', 'Россия', 44444444, 6);

INSERT INTO person_dog (id, name, phone, year_of_birth, mail, address, chat_id, dog_id) VALUES
    (1, 'Дмитрий Бизин', '+7905555555', 1990, 'd.bizin@yandex.ru', 'Италия', 5555555, 2),
    (2, 'Алексей Волков', '+79066666666', 1987, 'a.volkov@yandex.ru', 'Испания', 6666666, 3),
    (3, 'Николай Кулаков', '+79077777777', 1991, 'n.kulakov@yandex.ru', 'Россия', 7777777, 6),
    (4, 'Григорий Каляшов', '+7909999999', 1990, 'g.kalyashov@yandex.ru', 'Швеция', 9999999, 4);

-- changeset kulakov: 7

CREATE SCHEMA IF NOT EXISTS report;
CREATE TABLE IF NOT EXISTS report
(
    id              BIGINT PRIMARY KEY NOT NULL,
    chat_id         BIGINT             NOT NULL,
    ration          TEXT               NOT NULL,
    health          TEXT               NOT NULL,
    habits          TEXT               NOT NULL,
    days            BIGINT             NOT NULL,
    file_path       TEXT               NOT NULL,
    file_size       BIGINT             NOT NULL,
    data            BYTEA              NOT NULL,
    caption         TEXT               NOT NULL,
    last_message    TIMESTAMP          NOT NULL,
    last_message_ms BIGINT             NOT NULL,
    person_id       BIGINT             NOT NULL
);

ALTER TABLE report
    ADD FOREIGN KEY (person_id) REFERENCES person_cat (id);

ALTER TABLE report
    ADD FOREIGN KEY (person_id) REFERENCES person_dog (id);

-- changeset kulakov: 8

CREATE SCHEMA IF NOT EXISTS person_cat_report;
CREATE TABLE IF NOT EXISTS person_cat_report
(
    person_cat              BIGINT PRIMARY KEY NOT NULL,
    report_id               BIGINT NOT NULL
);

CREATE SCHEMA IF NOT EXISTS person_dog_report;
CREATE TABLE IF NOT EXISTS person_dog_report
(
    person_dog              BIGINT PRIMARY KEY NOT NULL,
    report_id               BIGINT NOT NULL
);

ALTER TABLE person_cat_report
    ADD FOREIGN KEY (report_id) REFERENCES report (id);

ALTER TABLE person_dog_report
    ADD FOREIGN KEY (report_id) REFERENCES report (id);

ALTER TABLE person_cat_report
    ADD FOREIGN KEY (person_cat) REFERENCES cat (id);

ALTER TABLE person_dog_report
    ADD FOREIGN KEY (person_dog) REFERENCES dog (id);

-- changeset salimgareeva:9
CREATE SCHEMA IF NOT EXISTS types_document_dog;
create type types_document_dog as enum (
    'DOG_DATING_RULES',
    'DOCUMENTS_TO_ADOPT_DOG',
    'SHIPPING_RECOMMENDATIONS',
    'PUPPY_HOME_IMPROVEMENT_TIPES',
    'DOG_HOME_IMPROVEMENT_TIPES',
    'DOG_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES',
    'CYNOLOG_ADVIVCE',
    'REASONS_FOR_REJECTION');


CREATE SCHEMA IF NOT EXISTS document_dog;
CREATE TABLE IF NOT EXISTS document_dog
(
    type_document_dog       types_document_dog NOT NULL primary key,
    text                    TEXT NOT NULL

);

-- changeset salimgareeva:10
CREATE SCHEMA IF NOT EXISTS bot_user;
CREATE TABLE IF NOT EXISTS bot_user
(
    id                      BIGINT NOT NULL primary key,
    last_request            TEXT NOT NULL,
    contact                 TEXT NOT NULL,
    shelter_id              BIGINT NOT NULL


);









