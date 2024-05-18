INSERT INTO users (login, password, name, email,enabled)
values ('admin', '$2a$10$9ud9wQXJw0JESJFnNfXUNefaO8Wyhjw29/jtowuYokx6yrwqsUnXW', 'SUPERUSER', 'harysh.bogdan@gmail.com',true);

INSERT into users_roles (role, user_id)
values ('MANAGER', 1);