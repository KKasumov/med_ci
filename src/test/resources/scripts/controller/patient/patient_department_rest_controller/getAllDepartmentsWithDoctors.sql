INSERT INTO role (id, name)
VALUES (100, 'PATIENT'),
       (200, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (20, 'Patient', 'ivan@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '2006-03-01', 'MALE', true, 100),
       (30, 'Doctor', 'sanyaPUSCH@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Alexander', 'Pushkin', 'Sergeevich', '1990-06-25', 'MALE', true, 200),
       (40, 'Doctor', 'bunin@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Ivan', 'Bunin', 'Alekseevich', '1990-06-25', 'MALE', true, 200),
       (50, 'Doctor', 'anna@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Anna', 'Akhmatova', 'Andreevna', '1990-06-25', 'FEMALE', true, 200),
       (60, 'Doctor', 'petr@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Petr', 'Petrov', 'Petrovich', '1990-06-25', 'MALE', true, 200),
       (70, 'Doctor', 'marishka@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Marina', 'Tsvetaeva', 'Ivanovna', '1990-06-25', 'FEMALE', true, 200),
       (80, 'Doctor', 'ivan222@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovih', '1990-06-25', 'MALE', true, 200),
       (90, 'Doctor', 'anna202@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Anna', 'Annova', 'Andreevna', '1990-06-25', 'FEMALE', true, 200),
       (100, 'Doctor', 'marina@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Marina', 'Marinova', 'Ivanovna', '1990-06-25', 'FEMALE', true, 200);

INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (1, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, medical_organization_id)
VALUES (12, 'Первое детское отделение', 'CHILD', 1),
       (13, 'Второе детское отделение', 'CHILD', 1),
       (14, 'Третье детское отделение', 'CHILD', 1),
       (15, 'Первое взрослое отделение', 'ADULT', 1);

INSERT INTO patient_history (id, patient_id)
VALUES (1, 20);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (5, 'DoctorHistory', true, 30, 12),
       (6, 'DoctorHistory', true, 40, 12),
       (7, 'DoctorHistory', true, 50, 12),
       (8, 'DoctorHistory', true, 60, 12),
       (9, 'DoctorHistory', true, 70, 13),
       (10, 'DoctorHistory', true, 80, 14),
       (11, 'DoctorHistory', true, 90, 14),
       (12, 'DoctorHistory', true, 100, 15);

INSERT INTO talon (id, time, patient_history_id, doctor_history_id)
VALUES (22, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 0:00:00', null, 5),
       (23, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 0:00:00', null, 6),
       (24, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '20 0:00:00', null, 7),
       (25, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 0:00:00', 1, 8),
       (26, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 0:00:00', null, 9),
       (27, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 0:00:00', 1, 10),
       (28, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '20 0:00:00', null, 11),
       (29, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 0:00:00', null, 12);