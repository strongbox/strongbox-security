

CREATE TABLE users (
       USERNAME            VARCHAR(32)       NOT NULL UNIQUE,
       PASSWORD            VARCHAR(64)       NOT NULL
);

CREATE TABLE roles (
       ROLE_NAME           VARCHAR(64)       NOT NULL UNIQUE,
       DESCRIPTION         VARCHAR(64)       NOT NULL
);

CREATE TABLE user_roles (
       USERNAME            VARCHAR(32)       NOT NULL,
       ROLE_NAME           VARCHAR(64)       NOT NULL,
       PRIMARY KEY(USERNAME, ROLE_NAME),
       CONSTRAINT fk_users FOREIGN KEY (USERNAME) REFERENCES users(USERNAME),
       CONSTRAINT fk_roles FOREIGN KEY (ROLE_NAME) REFERENCES roles(ROLE_NAME)
);

