

CREATE TABLE users (
       USERNAME             VARCHAR(32)       NOT NULL,
       PASSWORD             VARCHAR(64)       NOT NULL,
       FULL_NAME            VARCHAR(64)       NOT NULL,
       EMAIL                VARCHAR(128)      NOT NULL,
       PRIMARY KEY (USERNAME)
);

CREATE TABLE access_roles (
       ROLE_NAME            VARCHAR(64)       NOT NULL,
       DESCRIPTION          VARCHAR(128)      NOT NULL,
       PRIMARY KEY (ROLE_NAME)
);

CREATE TABLE access_privileges (
       PRIVILEGE_NAME       VARCHAR(64)       NOT NULL,
       DESCRIPTION          VARCHAR(128)      NOT NULL,
       PRIMARY KEY (PRIVILEGE_NAME)
);

CREATE TABLE role_mappings (
       MAPPING_ID           INTEGER           NOT NULL GENERATED ALWAYS AS IDENTITY (start with 1, increment by 1),
       ROLE_NAME            VARCHAR(64)       NOT NULL,
       NESTED_ROLE_NAME     VARCHAR(64)       DEFAULT NULL,
       PRIVILEGE_NAME       VARCHAR(64)       DEFAULT NULL,
       PRIMARY KEY (MAPPING_ID),
       CONSTRAINT fk_access_roles_rm_1 FOREIGN KEY (ROLE_NAME) REFERENCES access_roles (ROLE_NAME),
       CONSTRAINT fk_access_privileges FOREIGN KEY (PRIVILEGE_NAME) REFERENCES access_privileges(PRIVILEGE_NAME),
       CONSTRAINT fk_access_roles_rm_2 FOREIGN KEY (NESTED_ROLE_NAME) REFERENCES access_roles(ROLE_NAME)
);

CREATE TABLE user_roles (
       USERNAME             VARCHAR(32)       NOT NULL,
       ROLE_NAME            VARCHAR(64)       NOT NULL,
       PRIMARY KEY (USERNAME, ROLE_NAME),
       CONSTRAINT fk_users FOREIGN KEY (USERNAME) REFERENCES users(USERNAME),
       CONSTRAINT fk_access_roles_us_1 FOREIGN KEY (ROLE_NAME) REFERENCES access_roles (ROLE_NAME)
);

