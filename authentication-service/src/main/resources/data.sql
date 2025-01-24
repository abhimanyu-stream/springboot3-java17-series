-- Create roles
INSERT INTO authentication_service.role (id, rolename) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authentication_service.role (id, rolename) VALUES (2, 'ROLE_USER');
INSERT INTO authentication_service.role (id, rolename) VALUES (3, 'ROLE_MODERATOR');

-- Insert users
INSERT INTO authentication_service.user (id, username, email, password, first_name, last_name, phone, created, modified)
VALUES
    (1, 'Abhimanyu Kumar', 'stream.abhimanyu@yahoo.com', '$2a$10$0bWlTR3c7mQVAgX9gEr56eO9Qhn5/SgFpohhbnj7b7mfg.xlkMLkO', 'Abhimanyu', 'Kumar', '8789929244', NOW(), NOW()),
    (2, 'Elizabeth Forest', 'elijabeth.forest@yahoo.com', '$2a$10$0bWlTR3c7mQVAgX9gEr56eO9Qhn5/SgFpohhbnj7b7mfg.xlkMLkO', 'Elizabeth', 'Forest', '8789929244', NOW(), NOW());

-- Assign roles to users
INSERT INTO authentication_service.user_roles (user_id, role_id) VALUES (1, 1); -- Assign ROLE_ADMIN to Abhimanyu Kumar
INSERT INTO authentication_service.user_roles (user_id, role_id) VALUES (2, 3); -- Assign ROLE_MODERATOR to Elizabeth Forest
