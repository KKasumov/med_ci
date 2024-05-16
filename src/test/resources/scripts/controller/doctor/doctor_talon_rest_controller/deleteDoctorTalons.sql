INSERT INTO role (id, name)
VALUES (100, 'DOCTOR'),
       (200, 'PATIENT');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (20, 'Doctor', 'doctor20@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 20', 'Doctor20', 'Doctor20', 'Doctor20', '1990-06-25', 'MALE', true, 100),
       (30, 'Patient', 'patient30@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 30', 'patient30', 'patient30', 'patient30', '1990-06-25', 'MALE', true, 200),
       (40, 'Patient', 'patient40@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 40', 'patient40', 'patient40', 'patient40', '1990-06-25', 'MALE', true, 200),
       (50, 'Doctor', 'doctor50@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 50', 'doctor50', 'doctor50', 'doctor50', '1990-06-25', 'MALE', true, 100);

INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (1, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, medical_organization_id)
VALUES (12, 'Первое детское отделение', 'CHILD', 1);

INSERT INTO patient_history (id, patient_id)
VALUES (1, 30),
       (2, 40);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (5, 'DoctorHistory', true, 20, 12),
       (6, 'DoctorHistory', true, 50, 12);

INSERT INTO talon (id, time, patient_history_id, doctor_history_id)
VALUES (22, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 08:00:00', 1, 5),
       (23, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 09:00:00', null, 5),
       (24, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 10:00:00', null, 5),
       (25, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 11:00:00', 1, 5),
       (26, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 12:00:00', 1, 5),
       (27, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '4 09:00:00', 1, 5),
       (28, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '4 12:00:00', null, 5),
       (29, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '28 13:00:00', null, 5),
       (30, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '30 14:00:00', null, 5),
       (31, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 08:00:00', 1, 6),
       (32, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 09:00:00', null, 6),
       (33, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '30 10:00:00', null, 6);