delete from user_roles;
delete from users;

insert into users(id, username, email, first_name, last_name, password, created, updated) values
(1, "pkyfen", "email@email.com", "Denis", "Podkovyrov", "$2a$04$taRZR0eQFGM4YPn7Ep1biueTekbAVMtFEGqbwlF6NhrTFZG3Wzjp6", "2019-09-15 12:22:47", "2019-09-15 12:22:47");

insert into user_roles(user_id, role_id) values
(1, 1),
(1,2);