INSERT INTO role (id, name)
VALUES (100, 'PATIENT'),
       (200, 'REGISTRAR'),
       (300, 'DOCTOR'),
       (400, 'CHIEF_DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (21, 'Patient', 'patient1@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 01', 'Ivan1', 'Ivanov1', 'Ivanovich', '1990-09-25', 'MALE', true, 100),
       (22, 'Patient', 'patient2@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 02', 'Ivan2', 'Ivanov2', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
       (23, 'Patient', 'patient3@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 03', 'Ivan3', 'Ivanov3', 'Ivanovich', '1990-04-25', 'MALE', true, 100),
       --регистратор
       (31, 'Employee', 'registrar@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 11', 'Ivan5', 'Ivanov5', 'Ivanovich', '1993-01-01', 'MALE', true, 200),
       --заведующие
       (41, 'Doctor', 'doctor41@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 41', 'Oleg1', 'Sidorov1', 'Andreevich', '1975-02-01', 'MALE', true, 400),
       (42, 'Doctor', 'doctor42@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 42', 'Oleg2', 'Sidorov2', 'Andreevich', '1975-01-01', 'MALE', true, 400),
       --доктор который исполняет обязанности заведующего
       (51, 'Doctor', 'doctor51@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 51', 'Oleg3', 'Sidorov3', 'Andreevich', '1975-02-01', 'MALE', true, 400),
       --доктора
       (52, 'Doctor', 'doctor52@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 52', 'Irina1', 'Petrova1', 'Olegovna', '1980-08-08', 'FEMALE', true, 300),
       (53, 'Doctor', 'doctor53@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 53', 'Irina2', 'Petrova2', 'Olegovna', '1980-08-08', 'FEMALE', true, 300);

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date,
                                 full_employment_status_range, director_id, io_director_id)
VALUES (1, '700', 'med', 'address', '000', '2010-09-09', '2025-09-09', 223.75, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (1, 'Surgery', 'ADULT', 41, 51, 1),
       (2, 'Therapy', 'CHILD', 42, null, 1),
       (3, 'Economic', 'NO', null, null, 1);

INSERT INTO employee_history(dtype, id, is_public, department_id, employee_id)
VALUES ('DoctorHistory', 1, true, 1, 41),
       ('DoctorHistory', 2, true, 1, 51),
       ('DoctorHistory', 3, true, 1, 52),
       ('DoctorHistory', 4, true, 2, 42),
       ('DoctorHistory', 5, true, 2, 53);

INSERT INTO patient_history(id, patient_id)
VALUES (1, 21),
       (2, 22),
       (3, 23);

INSERT INTO talon(id, time, doctor_history_id, patient_history_id)
VALUES (1, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 7:00:0' DAY TO SECOND, 4, 1),
       (2, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 8:00:0' DAY TO SECOND, 4, null),

       (3, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 8:00:0' DAY TO SECOND, 2, null),
       (4, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 9:00:0' DAY TO SECOND, 2, null),

       (5, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 9:00:0' DAY TO SECOND, 3, 1),
       (6, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 10:00:0' DAY TO SECOND, 3, 2),

       (7, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 10:00:0' DAY TO SECOND, 5, null),
       (8, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 11:00:0' DAY TO SECOND, 5, 3),

       (9, (CURRENT_DATE + INTERVAL '1 DAY')::timestamp + INTERVAL '10 HOURS', 5, null),
       (10, (CURRENT_DATE + INTERVAL '1 DAY')::timestamp + INTERVAL '10 HOURS', 3, null);
