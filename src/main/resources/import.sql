INSERT INTO `springboot`.`roles` (`id`, `name`) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `springboot`.`roles` (`id`, `name`) VALUES ('2', 'ROLE_USER');
INSERT INTO `springboot`.`users` (`username`, `email`, `password`) VALUES ('admin', 'admin@admin', '$2a$10$rQnl/Z6gxB3IOgfOXFAyVe6G4lLa3ik8l7jQTGAP5yNPxL4i5Xl5S');
INSERT INTO `springboot`.`users` (`username`, `email`, `password`) VALUES ('user', 'user@user','$2a$10$fzfTO8VCpMxkk1n/9BjOuuO6ZbQCAGJ6hW7CpWDvr0CXskYEB7S4e');
INSERT INTO `springboot`.`users_roles` (`user_id`, `roles_id`) VALUES ('1', '1');
INSERT INTO `springboot`.`users_roles` (`user_id`, `roles_id`) VALUES ('2', '2');