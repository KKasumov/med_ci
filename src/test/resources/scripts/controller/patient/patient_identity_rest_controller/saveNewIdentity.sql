INSERT INTO role (id, name)
VALUES (100, 'PATIENT');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (20, 'Patient', 'ivan@mail.com', '$2a$12$2aDDzgBO5LoOBU5KxZSIvOmb.zyf9ocjcZdFz7KdtanjSDG1Jl5k6', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100);

INSERT INTO user_history (id, user_id)
VALUES (201, 20);

INSERT INTO identity_document (id, document_type, serial, number, date_start, first_name, last_name, patronymic, birthday, gender, is_deprecated, user_history_id)
VALUES (50001, 'PASSPORT', '0000', '111111110', '2012-01-01', 'Ivan', 'Ivanov', 'Ivanovich', '1991-02-13', 'MALE', false, 201);