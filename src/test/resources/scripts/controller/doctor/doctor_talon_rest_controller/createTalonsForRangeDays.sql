INSERT INTO role (id, name)
VALUES (100, 'PATIENT'),
       (300, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (21, 'Patient', 'patient1@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 01', 'Ivan1', 'Ivanov1', 'Ivanovich', '1990-09-25', 'MALE', true, 100),
       (22, 'Patient', 'patient2@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 02', 'Ivan2', 'Ivanov2', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
       (23, 'Patient', 'patient3@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 03', 'Ivan3', 'Ivanov3', 'Ivanovich', '1990-04-25', 'MALE', true, 100),
       --доктор который исполняет обязанности заведующего
       (52, 'Doctor', 'doctor52@email.com', '$2a$12$q56lux/Q6dB5KdcUp.jM7eTaQ66y/Yeb8dZxvNAjcpHvhT18jKJ.6',
        '000-000-000 52', 'Irina1', 'Petrova1', 'Olegovna', '1980-08-08', 'FEMALE', true, 300),
       (53, 'Doctor', 'doctor53@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 53', 'Irina2', 'Petrova2', 'Olegovna', '1980-08-08', 'FEMALE', true, 300);

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date,
                                 full_employment_status_range, director_id, io_director_id)
VALUES (1, '700', 'med', 'address', '000', '2010-09-09', '2025-09-09', 223.75, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (1, 'Surgery', 'ADULT', null, 53, 1),
       (2, 'Therapy', 'CHILD', null, null, 1),
       (3, 'Economic', 'NO', null, null, 1);

INSERT INTO employee_history(dtype, id, is_public, department_id, employee_id)
VALUES ('DoctorHistory', 3, true, 1, 52),
       ('DoctorHistory', 5, true, 2, 53);

INSERT INTO patient_history(id, patient_id)
VALUES (1, 21),
       (2, 22),
       (3, 23);

INSERT INTO talon(id, time, doctor_history_id, patient_history_id)
VALUES (5000, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 9:00:0' DAY TO SECOND, 3, 1),
       (6000, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 10:00:0' DAY TO SECOND, 3, 2),

       (7000, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 10:00:0' DAY TO SECOND, 5, null),
       (8000, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 11:00:0' DAY TO SECOND, 5, 3),

       (9000, (CURRENT_DATE + INTERVAL '1 DAY'):: timestamp + INTERVAL '10 HOURS', 5, 1);