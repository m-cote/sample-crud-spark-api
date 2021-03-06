DROP TABLE IF EXISTS user_settings;
DROP TABLE IF EXISTS user_attributes;
DROP TABLE IF EXISTS users;

CREATE TABLE `users` (
                         `id` int(9) unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(100) NOT NULL DEFAULT '',
                         `last_name` varchar(100) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_settings` (
                            `user_id` int(9) unsigned PRIMARY KEY NOT NULL,
                            `send_sms` bit(1) NOT NULL,
                            `send_email` bit(1) NOT NULL,
                            FOREIGN KEY (`user_id`)
                                REFERENCES users(id)
                                ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table user_attributes
(
    `user_id` int(9) unsigned NOT NULL,
    `key`   varchar(150) not null,
    value   varchar(1000)   not null,
    PRIMARY KEY (user_id, `key`),
    FOREIGN KEY (`user_id`)
        REFERENCES users(id)
        ON DELETE CASCADE

);
