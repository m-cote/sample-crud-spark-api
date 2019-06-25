DELETE FROM user_settings;
DELETE FROM users;

INSERT INTO users VALUES ('1','Anna','Gutkowski'),
                           ('2','Dashawn','Deckow'),
                           ('3','Joesph','Cormier'),
                           ('4','Missouri','Lemke');

INSERT INTO user_settings VALUES ('1',TRUE,TRUE),
                              ('2',TRUE,FALSE),
                              ('3',FALSE,TRUE),
                              ('4',FALSE,FALSE);

INSERT INTO user_attributes VALUES ('1','email','test@email.com'),
                                   ('1','phone','+380990001111'),
                                   ('2','email','test2@email.com'),
                                   ('2','phone','+380990002222'),
                                   ('3','email','test3@email.com'),
                                   ('3','phone','+380990003333'),
                                   ('4','email','test4@email.com'),
                                   ('4','phone','+380990004444');