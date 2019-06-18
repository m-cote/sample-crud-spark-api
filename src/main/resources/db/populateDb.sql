DELETE FROM user_settings;
DELETE FROM `users`;

INSERT INTO `users` VALUES ('1','Anna','Gutkowski'),
                           ('2','Dashawn','Deckow'),
                           ('3','Joesph','Cormier'),
                           ('4','Missouri','Lemke');

INSERT INTO `user_settings` VALUES ('1',TRUE,TRUE),
                              ('2',TRUE,FALSE),
                              ('3',FALSE,TRUE),
                              ('4',FALSE,FALSE);
