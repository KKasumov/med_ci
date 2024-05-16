INSERT INTO role (id, name)
VALUES (100, 'PATIENT'),
       (101, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (10, 'Patient', 'danil@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 03', 'Danil', 'Nesterenko', 'Ivanovich', '1998-01-07', 'MALE', true, 100),
       (20, 'Employee', 'economist@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', true, 101);

INSERT INTO smo (id, name, region, start_date, end_date, code)
VALUES (601, 'smo1', 'MOSCOW', '2011-01-01', '2031-01-01', '0000'),
       (602, 'smo2', 'NIZHNY_NOVGOROD_REGION', '2011-01-01', '2031-01-01', '0001'),
       (603, 'smo3', 'CHELYABINSK_REGION', '2011-01-01', '2031-01-01', '0002'),
       (604, 'smo4', 'CHELYABINSK_REGION', '2011-01-01', '2031-01-01', '0003');

INSERT INTO patient_history (id, patient_id)
VALUES (201, 10),
       (202, 20);

INSERT INTO polis (id, insurance_type, serial, number, date_start, date_end, is_deprecated, smo_id, patient_history_id)
VALUES (50001, 'OMS', '0000', '000001', '2012-01-01', '2029-01-01', false, 601, 201),
       (50002, 'DMS', '0001', '000002', '2013-01-01', '2028-01-01', false, 601, 201);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date,
                                  full_employment_status_range, director_id, io_director_id)
VALUES (2222, 'code1', 'Sacred Heart', null, 'ogrn1', CURRENT_TIMESTAMP, null, 20, null, null);


INSERT INTO orders (id, insurance_type, comment, date, money, is_formed, is_accepted_for_payment, is_payed,
                    medical_organization_id, smo_id)
VALUES (8001, 'OMS', null, '2030-01-01', null, false, false, false, 2222, 602);

INSERT INTO municipal_order (id, money, period, medical_organization_id)
VALUES (3333, 100500, '2030-01-01', 2222);

INSERT INTO municipal_order_for_smo (id, money, municipal_order_id, smo_id)
VALUES (10000, 100500, 3333, 603);

INSERT INTO bank (id, name, legal_address, bik, start_date, end_date)
VALUES (7777, 'NESTERENKOFF', 'PUSHKINO', '0303030323', '1999-12-12', null);

INSERT INTO bank_account (id, dtype, name, number, is_disabled, bank_id, smo_id, medical_organization_id, employee_history_id)
VALUES (9999, 'smobankaccount', null, '0123456789DEL', false, 7777, 604, null, null),
       (9998, 'smobankaccount', null, '0123456789DEL2', false, 7777, 604, null, null),
       (9997, 'smobankaccount', null, '0123456789NONDEL', false, 7777, 603, null, null);
