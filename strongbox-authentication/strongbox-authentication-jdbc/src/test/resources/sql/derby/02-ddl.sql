
-- DDL Script for strongbox

INSERT INTO users (USERNAME, PASSWORD, FULL_NAME, EMAIL)
           VALUES ('admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'Admin', 'admin@carlspring.org');
           -- VALUES ('admin', 'MD5:5f4dcc3b5aa765d61d8327deb882cf99');
INSERT INTO users (USERNAME, PASSWORD, FULL_NAME, EMAIL)
           VALUES ('testuser', '5f4dcc3b5aa765d61d8327deb882cf99', 'Testuser', 'testuser@carlspring.org');
           -- VALUES ('testuser', 'MD5:5f4dcc3b5aa765d61d8327deb882cf99');

INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('Administer', 'Administer');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('Deploy', 'Deploy artifacts');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('Download', 'Download artifacts');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('Update', 'Update artifacts');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('Delete', 'Delete artifacts');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('Login', 'Login privilege');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('Reindex', 'Reindex privilege');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('Rebuild Maven Metadata', 'Rebuild Maven Metadata privilege');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('Clear repository cache', 'Clear repository cache privilege');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('UI: Basic rights', 'Basic UI rights');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('UI: Search', 'Search privilege');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('UI: View RSS feeds', 'View RSS feeds privilege');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('UI: Config files', 'View config files privilege');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('UI: Logs', 'View logs privilege');
INSERT INTO access_privileges (PRIVILEGE_NAME, DESCRIPTION) VALUES ('UI: Logging', 'Manage and view logging privilege');

INSERT INTO access_roles (role_name, description) VALUES ('ADMINISTRATOR', 'Administrative user role');
INSERT INTO access_roles (role_name, description) VALUES ('USER', 'Regular user role');
INSERT INTO access_roles (role_name, description) VALUES ('UI: Basic rights', 'Basic UI rights.');
INSERT INTO access_roles (role_name, description) VALUES ('Deployment', 'Deployment rights.');

INSERT INTO role_mappings (ROLE_NAME, PRIVILEGE_NAME)   VALUES ('UI: Basic rights', 'Login');
INSERT INTO role_mappings (ROLE_NAME, PRIVILEGE_NAME)   VALUES ('UI: Basic rights', 'UI: Search');
INSERT INTO role_mappings (ROLE_NAME, PRIVILEGE_NAME)   VALUES ('UI: Basic rights', 'UI: View RSS feeds');
INSERT INTO role_mappings (ROLE_NAME, PRIVILEGE_NAME)   VALUES ('Deployment', 'Deploy');
INSERT INTO role_mappings (ROLE_NAME, NESTED_ROLE_NAME) VALUES ('Deployment', 'UI: Basic rights');

-- Admin user
INSERT INTO user_roles VALUES ('admin', 'ADMINISTRATOR');
-- Admin user must have USER role
INSERT INTO user_roles VALUES ('admin', 'USER');
-- A regular user.
INSERT INTO user_roles VALUES ('testuser', 'USER');

