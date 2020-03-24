USE conference_db;

CREATE TABLE section
(
    id     INTEGER      NOT NULL AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    judges INTEGER, /* количество членов жюри */
    CONSTRAINT pk_section PRIMARY KEY (id)
);

CREATE TABLE report
(
    id         INTEGER      NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL,
    section_id INTEGER      NOT NULL,
    CONSTRAINT pk_report PRIMARY KEY (id),
    CONSTRAINT fk_report_section FOREIGN KEY (section_id) REFERENCES section (id)
);

CREATE TABLE presenter
(
    surname    VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255) NOT NULL,
    report_id  INTEGER      NOT NULL,
    CONSTRAINT fk_presenter_report FOREIGN KEY (report_id) REFERENCES report (id)
);