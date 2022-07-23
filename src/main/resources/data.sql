INSERT INTO account_status(id,name) VALUES (1,'OK'), (2,'LOCKED_AUTH'), (3,'LOCKED_ADMIN'), (4,'BANNED');
INSERT INTO account_role(id,name) VALUES (1,'FREE_MEMBER'), (2,'PREMIUM_MEMBER'), (3,'MODERATOR'), (4,'ADMIN'), (5,'GOD');

/**
 password = rebecca
*/
INSERT INTO account (username,email, password, has_multi_factor_auth,phone, remaining_tries, remember_me,role_id, status_id)
VALUES ('Rebecca','Rebecca.AZvarebes@gmail.com','$2y$10$LgZx.XhASC/Uhxcf75vzBudnPiL0KCCcHIT6jN1C6aWoNJD54Z1NC', 0, 0258964781 ,5,1,1,1);

/**
 password = arnold
*/
INSERT INTO account (username,email, password, has_multi_factor_auth,phone, remaining_tries, remember_me,role_id, status_id)
VALUES ('Arnold','testmail45891@gmail.com','$2y$10$7qLmGaz.N4FZ4fnYmN.F3e5ne3.QTajm8mGJVG.wBPpC3V1RBA.t2', 1, 0258964781 ,5,1,1,1);


