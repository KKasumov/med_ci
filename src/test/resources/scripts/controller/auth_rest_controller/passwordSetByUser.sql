INSERT INTO role (id, name)
VALUES (100, 'PATIENT');

INSERT INTO users (id, dtype, email, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id, password)
VALUES
    (150, 'Patient','mis_mono_1@bk.ru','123-456 78', 'Peter', 'Jackson', 'Petrovich', '1990-01-01', 'MALE', true, 100, '$2a$12$snwnLHxW1fWLhrFhdjeTZuB94l7jQ/x2BoX2IcOpho.PMHo4oMZM2'),
    (151, 'Patient','patient1@mail.com','123-456 79', 'f_name_151', 'l_name_151', 'patronymic_151', '1990-01-01', 'MALE', true, 100, '$2a$12$snwnLHxW1fWLhrFhdjeTZuB94l7jQ/x2BoX2IcOpho.PMHo4oMZM2');

INSERT INTO invite (email, token, expiration_date)
VALUES
    ('mis_mono_1@bk.ru', '0123456789abcdef', CURRENT_TIMESTAMP + interval '0 0:30:0' DAY TO SECOND),
    ('mis_mono_12@bk.ru', 'abcdef0123456789', CURRENT_TIMESTAMP - interval '1 0:30:0' DAY TO SECOND);