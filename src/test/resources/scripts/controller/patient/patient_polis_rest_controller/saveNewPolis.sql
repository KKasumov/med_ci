INSERT INTO role (id, name)
VALUES
    (100, 'PATIENT');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (20, 'Patient', 'ivan@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (30, 'Patient', 'ivan30@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 01', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100);

INSERT INTO smo (id, name, region, start_date, end_date, code)
VALUES
    (601, 'smo1','MOSCOW', '2011-01-01', '2031-01-01', '0000'),
    (602, 'smo2','NIZHNY_NOVGOROD_REGION', '2011-01-01', '2031-01-01', '0001');

INSERT INTO patient_history (id, patient_id)
VALUES (201, 20),
       (202, 30);

INSERT INTO polis (id, insurance_type, serial, number, date_start, date_end, is_deprecated, smo_id, patient_history_id)
VALUES (50001, 'OMS', '0000', '000001', '2012-01-01', '2029-01-01', false, 601, 201),
       (50002, 'DMS', '0001', '000002', '2013-01-01', '2028-01-01', false, 602, 201),
       (50003, 'OMS', '0000', '000001', '2012-01-01', '2029-01-01', false, 602, 202);