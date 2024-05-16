INSERT INTO smo (id, name, region, start_date, end_date, code)
VALUES (250, 'smo1','MOSCOW', '2011-01-01', '2031-01-01', '0000'),
       (251, 'smo2','SAINT_PETERSBURG_REGION', '2012-02-02', '2032-02-02', '0001'),
       (252, 'smo3','KRASNODAR_REGION', '2013-03-03', '2033-03-03', '0002');

INSERT INTO role (id, name)
VALUES (100, 'PATIENT');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (30, 'Patient', 'nikolay@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Nikolay', 'Nikolayev', 'Nikolayevich', '1990-06-25', 'MALE', true, 100);
