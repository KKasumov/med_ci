INSERT INTO role (id, name)
VALUES (100, 'PATIENT'),
       (300, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (21, 'Patient', 'patient1@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 01', 'Ivan1', 'Ivanov1',  'Ivanovich',  '1990-09-25', 'MALE', true, 100),
       (22, 'Patient', 'patient2@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 02', 'Ivan2', 'Ivanov2',  'Ivanovich',  '1990-06-25', 'MALE', true, 100),
       (41, 'Doctor',  'doctor41@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 41', 'Oleg',  'Olegov',   'Andreevich', '1975-02-01', 'MALE', true, 300),
       (42, 'Doctor',  'doctor42@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 42', 'Oleg2', 'Sidorov2', 'Andreevich', '1975-01-01', 'MALE', true, 300);

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES (1, '700', 'med', 'address', '000', CURRENT_TIMESTAMP, null, 20, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (1, 'Surgery', 'ADULT', null, null, 1);

INSERT INTO employee_history(dtype, id, is_public, department_id, employee_id)
VALUES ('DoctorHistory', 1, true, 1, 41),
       ('DoctorHistory', 2, true, 1, 42);

INSERT INTO patient_history(id, patient_id)
VALUES (1, 21),
       (2, 22);

INSERT INTO talon(id, time, doctor_history_id, patient_history_id)
VALUES (1, date_trunc('minute', CURRENT_DATE + interval '0 7:00:0' DAY TO SECOND), 1, 1),
       (2, date_trunc('minute', CURRENT_DATE + interval '0 8:00:0' DAY TO SECOND), 1, null),

       (3, date_trunc('minute', CURRENT_DATE + INTERVAL '1 8:00:0' DAY TO SECOND), 2, null),
       (4, date_trunc('minute', CURRENT_DATE + INTERVAL '1 9:00:0' DAY TO SECOND), 2, null),

       (5, date_trunc('minute', CURRENT_DATE + INTERVAL '3 9:00:0' DAY TO SECOND), 1, 1),
       (6, date_trunc('minute', CURRENT_DATE + INTERVAL '6 10:00:0' DAY TO SECOND), 1, 2),

       (7, date_trunc('minute', CURRENT_DATE + INTERVAL '10 10:00:0' DAY TO SECOND), 2, null),
       (8, date_trunc('minute', CURRENT_DATE + INTERVAL '15 11:00:0' DAY TO SECOND), 2, 2),

       (9, date_trunc('minute', CURRENT_DATE + INTERVAL '20 10:00:0' DAY TO SECOND), 1, null),
       (10, date_trunc('minute', CURRENT_DATE + INTERVAL '30 13:00:0' DAY TO SECOND), 1, 1);
