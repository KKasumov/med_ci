INSERT INTO role (id, name)
VALUES (700, 'HR_MANAGER');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name,  birthday, gender, is_enabled, role_id)
VALUES (50, 'Employee', 'hr@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '777-777-777 777', 'Ivan', 'Ivanov',  '1970-01-01', 'MALE', true, 700);

INSERT INTO university (id, name)
VALUES (1, 'University1');

INSERT INTO diploma (id, serial_number, end_date, university_id)
VALUES (700, 'diploma1', '2023-08-31', 1);


