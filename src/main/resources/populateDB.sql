INSERT INTO `cruddevelopers`.`roles` (`id`, `name`) VALUES ('1', 'ROLE_USER');
INSERT INTO `cruddevelopers`.`roles` (`id`, `name`) VALUES ('2', 'ROLE_ADMIN');
INSERT INTO `cruddevelopers`.`roles` (`id`, `name`) VALUES ('3', 'ROLE_MODERATOR');
INSERT INTO users VALUES (1, NOW(), NOW(), '$2a$04$VwDadZn7OBcCxIOMgaYWROTr8PrZfnlOw7uMaxwq3RLd6WjB5mzDu', '12345', 'ACTIVE', NOW(), 'user');
INSERT INTO users VALUES (2, NOW(), NOW(), '$2a$04$VwDadZn7OBcCxIOMgaYWROTr8PrZfnlOw7uMaxwq3RLd6WjB5mzDu', '1234567', 'ACTIVE', NOW(), 'admin');
INSERT INTO users VALUES (3, NOW(), NOW(), '$2a$04$VwDadZn7OBcCxIOMgaYWROTr8PrZfnlOw7uMaxwq3RLd6WjB5mzDu', '123', 'ACTIVE', NOW(), 'moderator');
INSERT INTO users_roles VALUES(1, 1);
INSERT INTO users_roles VALUES(2, 2);
INSERT INTO users_roles VALUES(3, 3);