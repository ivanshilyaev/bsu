USE conference_db;

CREATE TABLE section
(
    id     INTEGER,
    name   VARCHAR(255),
    judges INTEGER,
    CONSTRAINT pk_section PRIMARY KEY (id)
);

CREATE TABLE report
(
    id         INTEGER,
    name       VARCHAR(255),
    section_id INTEGER,
    CONSTRAINT pk_report PRIMARY KEY (id),
    CONSTRAINT fk_report_section FOREIGN KEY (section_id) REFERENCES section (id)
);

CREATE TABLE presenter
(
    surname    VARCHAR(255),
    name       VARCHAR(255),
    patronymic VARCHAR(255),
    report_id  INTEGER,
    CONSTRAINT fk_presenter_report FOREIGN KEY (report_id) REFERENCES report (id)
);