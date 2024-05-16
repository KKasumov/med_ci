INSERT INTO role (id, name)
VALUES (1, 'PATIENT');

INSERT INTO users
(id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, role_id, is_enabled)
VALUES (20, 'Patient', 'ivan@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', 'snils',
        'first', 'last', 'patronimic', '2020-02-20', 'MALE', 1, true),
       (40, 'Patient', 'ivan1@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', 'snils',
        'first', 'last', 'patronimic', '2020-02-20', 'MALE', 1, true);

INSERT INTO user_history (id, user_id)
VALUES (1, 20),
       (2, 40);

INSERT INTO identity_document
(id, document_type, serial, number, date_start, first_name, last_name, patronymic, birthday, gender, is_deprecated,
 user_history_id)
VALUES (1, 'PASSPORT', '0123456789', '9876543210', '2020-12-20', 'Name', 'Last', 'patro', '2020-12-20', 'MALE', false,
        1),
       (2, 'PASSPORT', '0123456789', '9876543210', '2020-12-20', 'Name1', 'Last', 'patro', '2020-12-20', 'MALE', true,
        1);

INSERT INTO patient_history (id, patient_id)
VALUES (1, 20),
       (2, 40);

INSERT INTO smo (id, name, region, start_date, end_date, code)
VALUES (1, 'name', 'MOSCOW', '2020-02-20', '2020-02-20', '0000'),
       (2, 'name', 'MOSCOW_REGION', '2020-03-20', '2020-03-20', '0001');

INSERT INTO polis
(id, insurance_type, serial, number, date_start, date_end, smo_id, is_deprecated, patient_history_id)
VALUES (1, 'OMS', '1234567890', '0987654321', '2020-02-20', '2020-02-20', 1, false, 1),
       (2, 'OMS', '1234567890', '0987654321', '2020-02-20', '2020-02-20', 2, true, 1);