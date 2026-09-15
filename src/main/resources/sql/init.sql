CREATE DATABASE kofia_copeerative;

CREATE USER kofia_copeerative_user WITH PASSWORD '123456';

GRANT ALL ON SCHEMA public TO collectivite_agricole_user;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO kofia_copeerative_user;

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO kofia_copeerative_user;

GRANT ALL ON SCHEMA public TO kofia_copeerative_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO kofia_copeerative_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO kofia_copeerative_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL ON TABLES TO kofia_copeerative_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL ON SEQUENCES TO kofia_copeerative_user;

GRANT USAGE, CREATE
    ON SCHEMA public TO kofia_copeerative_user;
GRANT ALL PRIVILEGES ON SCHEMA public TO kofia_copeerative_user;