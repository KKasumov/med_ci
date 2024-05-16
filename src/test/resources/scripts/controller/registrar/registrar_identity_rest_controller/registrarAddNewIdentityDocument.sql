INSERT INTO role (id, name)
VALUES (1, 'PATIENT'),
       (2, 'REGISTRAR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (10, 'Patient', 'ivan@mail.com', '$2a$12$2aDDzgBO5LoOBU5KxZSIvOmb.zyf9ocjcZdFz7KdtanjSDG1Jl5k6', '000-000-000 00', 'f_patient', 'l_patient', 'p_patient', '1888-08-08', 'MALE', true, 1),
       (20, 'Employee', 'registrar@mail.com', '$2a$12$X14Abk3ZWuQqTEWA/6qcg.pB.2uhPy3VykUJDybK0U5fzW7niWSHy', '000-000-000 00', 'f_registrar', 'l_registrar', 'p_registrar', '1999-09-09', 'MALE', true, 2);

INSERT INTO user_history (id, user_id)
VALUES (100, 10);

INSERT INTO identity_document (id, document_type, serial, number, date_start, first_name, last_name, patronymic, birthday, gender, is_deprecated, user_history_id)
VALUES (1111, 'PASSPORT', '0000', '000000000', '2020-02-02', 'f_patient', 'l_patient', 'p_patient', '1888-08-08', 'MALE', false, 100);