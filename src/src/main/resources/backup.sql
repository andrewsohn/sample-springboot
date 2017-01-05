CREATE DATABASE IF NOT EXISTS `sample-sboot` DEFAULT CHARACTER SET `utf8` COLLATE `utf8_general_ci`;

CREATE USER `usboot`@`localhost` IDENTIFIED BY `usboot!@`;

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER ON `sample-sboot`.* TO `usboot`@`localhost`;