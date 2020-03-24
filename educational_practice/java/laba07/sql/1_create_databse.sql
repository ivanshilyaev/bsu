CREATE DATABASE conference_db;
create user application@localhost identified by 'application_password';

GRANT SELECT, INSERT, UPDATE, DELETE
    ON conference_db.*
    TO application@localhost;