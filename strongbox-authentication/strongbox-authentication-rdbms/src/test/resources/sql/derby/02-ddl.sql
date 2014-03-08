
-- DDL Script for strongbox

INSERT INTO users (USERNAME, PASSWORD, FULL_NAME, EMAIL)
           VALUES ('admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'Admin', 'admin@carlspring.org');
           -- VALUES ('admin', 'MD5:5f4dcc3b5aa765d61d8327deb882cf99');
INSERT INTO users (USERNAME, PASSWORD, FULL_NAME, EMAIL)
           VALUES ('testuser', '5f4dcc3b5aa765d61d8327deb882cf99', 'Testuser', 'testuser@carlspring.org');
           -- VALUES ('testuser', 'MD5:5f4dcc3b5aa765d61d8327deb882cf99');

INSERT INTO access_roles (role_name, description) VALUES ('ADMINISTRATOR', 'Administrative user role');
INSERT INTO access_roles (role_name, description) VALUES ('USER', 'Regular user role');
INSERT INTO access_roles (role_name, description) VALUES ('GET', 'Request artifacts');
INSERT INTO access_roles (role_name, description) VALUES ('VIEW', 'Request artifacts');
INSERT INTO access_roles (role_name, description) VALUES ('PUT', 'Upload artifacts');
INSERT INTO access_roles (role_name, description) VALUES ('DELETE', 'Delete artifacts');

-- Admin user
INSERT INTO user_roles VALUES ('admin', 'ADMINISTRATOR');
-- Admin user must have USER role
INSERT INTO user_roles VALUES ('admin', 'USER');
-- A regular user.
INSERT INTO user_roles VALUES ('testuser', 'USER');

