INSERT INTO role (id, name)
VALUES (100, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (20, 'Employee', 'economist@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', true, 100);

INSERT INTO smo (id, name, region, start_date, end_date, code)
VALUES (601, 'smo_pattern1_name', 'MOSCOW',             '2011-01-01', DATE_TRUNC('day', CURRENT_DATE) + INTERVAL '10 year', '0000'),
       (602, 'smo_pattern2_name', 'KAZAN_REGION',       '2011-01-01', DATE_TRUNC('day', CURRENT_DATE) + INTERVAL '10 year', '0001'),
       (603, 'smo_3_name',        'CHELYABINSK_REGION', '2011-01-01', DATE_TRUNC('day', CURRENT_DATE) + INTERVAL '10 year', '0002'),
       (604, 'smo_pattern4_name', 'MOSCOW',             '2011-01-01', DATE_TRUNC('day', CURRENT_DATE) + INTERVAL '10 year', '0003'),
       (605, 'smo_5_name',        'MOSCOW',             '2011-01-01', DATE_TRUNC('day', CURRENT_DATE) + INTERVAL '10 year', '0004');

