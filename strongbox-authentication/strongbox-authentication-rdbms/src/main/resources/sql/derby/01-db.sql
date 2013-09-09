

CREATE TABLE users (
       USERID              INTEGER           NOT NULL GENERATED ALWAYS AS IDENTITY (start with 1, increment by 1),
       USERNAME            VARCHAR(32)       NOT NULL UNIQUE,
       PASSWORD            VARCHAR(64)       NOT NULL
);

CREATE TABLE roles (
       ROLEID              INTEGER           NOT NULL GENERATED ALWAYS AS IDENTITY (start with 1, increment by 1),
       ROLE_NAME           VARCHAR(64)       NOT NULL UNIQUE,
       DESCRIPTION         VARCHAR(64)       NOT NULL
);

CREATE TABLE user_roles (
       USERID              INTEGER           NOT NULL,
       ROLEID              INTEGER           NOT NULL
);

