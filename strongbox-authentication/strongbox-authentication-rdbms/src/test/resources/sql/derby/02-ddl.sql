
-- DDL Script for strongbox

INSERT INTO users (USERNAME, PASSWORD)
           VALUES ('admin', '5f4dcc3b5aa765d61d8327deb882cf99');
           -- VALUES ('admin', 'MD5:5f4dcc3b5aa765d61d8327deb882cf99');
INSERT INTO users (USERNAME, PASSWORD)
           VALUES ('testuser', '5f4dcc3b5aa765d61d8327deb882cf99');
           -- VALUES ('testuser', 'MD5:5f4dcc3b5aa765d61d8327deb882cf99');

INSERT INTO roles (role_name, description) VALUES ('ADMINISTRATOR', 'Administrative privilege');
INSERT INTO roles (role_name, description) VALUES ('USER', 'Regular user privilege');
INSERT INTO roles (role_name, description) VALUES ('GET', 'Request artifacts');
INSERT INTO roles (role_name, description) VALUES ('VIEW', 'Request artifacts');
INSERT INTO roles (role_name, description) VALUES ('PUT', 'Upload artifacts');
INSERT INTO roles (role_name, description) VALUES ('DELETE', 'Delete artifacts');

-- Admin user
INSERT INTO user_roles VALUES ('admin', 'ADMINISTRATOR');
-- Admin user must have USER role
INSERT INTO user_roles VALUES ('admin', 'USER');
-- A regular user.
INSERT INTO user_roles VALUES ('testuser', 'USER');

