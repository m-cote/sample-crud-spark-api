DELETE FROM user_settings;
DELETE FROM `users`;

INSERT INTO `users` VALUES ('1','Anna','Gutkowski'),
                           ('2','Dashawn','Deckow'),
                           ('3','Rosalee','Thompson'),
                           ('4','Joesph','Cormier'),
                           ('5','Dallas','D\'Amore'),
                           ('6','Missouri','Lemke'),
                           ('7','Libby','O\'Hara'),
                           ('8','Tate','Veum'),
                           ('9','Kali','Hoeger'),
                           ('10','Olen','Howe');

INSERT INTO `user_settings` VALUES ('1',TRUE,TRUE),
                              ('2',TRUE,FALSE),
                              ('3',TRUE,FALSE),
                              ('4',FALSE,FALSE),
                              ('5',FALSE,TRUE),
                              ('6',TRUE,FALSE),
                              ('7',TRUE,FALSE),
                              ('8',TRUE,TRUE),
                              ('9',FALSE,TRUE),
                              ('10',TRUE,FALSE);