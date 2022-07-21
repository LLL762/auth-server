INSERT INTO account_status(id,name) VALUES (1,'OK'), (2,'LOCKED_AUTH'), (3,'LOCKED_ADMIN'), (4,'BANNED');
INSERT INTO account_role(id,name) VALUES (1,'FREE_MEMBER'), (2,'PREMIUM_MEMBER'), (3,'MODERATOR'), (4,'ADMIN'), (5,'GOD');

/**
 password = arnold
*/
INSERT INTO account (username,email, password, has_multi_factor_auth,phone, remaining_tries, remember_me,role_id, status_id)
VALUES ('Louis','lamirallaurent@gmail.com','$2a$10$10YqA4moO7vwr/4fboeYLeCi1aHl4kziybO.QQUrUO12LgI1LGKZq', 0, 0258964781 ,5,1,1,1);