INSERT INTO role (id, name)
VALUES (100, 'PATIENT');

INSERT INTO users (id, dtype, email, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id, password)
VALUES
    (150, 'Patient','patient@email.com','123-456 78', 'f_name_150', 'l_name_150', 'patronymic_150', '1990-01-01', 'MALE', true, 100, '$2a$12$snwnLHxW1fWLhrFhdjeTZuB94l7jQ/x2BoX2IcOpho.PMHo4oMZM2'),
    (151, 'Patient','patient1@email.com','123-456 79', 'f_name_151', 'l_name_151', 'patronymic_151', '1990-01-01', 'MALE', true, 100, '$2a$12$snwnLHxW1fWLhrFhdjeTZuB94l7jQ/x2BoX2IcOpho.PMHo4oMZM2');