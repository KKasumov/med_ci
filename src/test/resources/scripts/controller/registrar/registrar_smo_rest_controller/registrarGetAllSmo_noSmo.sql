INSERT INTO role (id, name)
VALUES (500, 'REGISTRAR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (50, 'Employee', 'arnold@mail.com', '$2a$12$AuIlC5NrTKy7Xe/c1Yg/teO8.s5lF4R/64DOMnClGYRNSt0MKQNkK', '500-000-000 00', 'Arnold', 'Schwarzenegger', 'Gustav', '1947-07-30', 'MALE', '1', 500);

