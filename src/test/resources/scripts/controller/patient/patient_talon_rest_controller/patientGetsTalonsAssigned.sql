INSERT INTO role (id, name)
VALUES (100, 'PATIENT'),
       (200, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (20, 'Patient', 'ivan@mail.com', '$2y$10$.SBSrGpgbx0lXNGVh5XMMe6859EAKuompJXSSna/Mw//XL5wsNF36',
        '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '2000-03-01', 'MALE', true, 100),
       (21, 'Patient', 'ivan1@mail.com', '$2y$10$.SBSrGpgbx0lXNGVh5XMMe6859EAKuompJXSSna/Mw//XL5wsNF36',
        '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '2000-03-01', 'MALE', true, 100),
       (22, 'Patient', 'ivan2@mail.com', '$2y$10$.SBSrGpgbx0lXNGVh5XMMe6859EAKuompJXSSna/Mw//XL5wsNF36',
        '000-000-000 00', 'IvanP', 'Ivanov', 'Ivanovich', '2009-03-01', 'MALE', true, 100),
       (30, 'Doctor', 'sanyaPUSCH@mail.com', '$2y$10$.SBSrGpgbx0lXNGVh5XMMe6859EAKuompJXSSna/Mw//XL5wsNF36',
        '000-000-000 00', 'Alexander', 'Pushkin', 'Sergeevich', '1990-06-25', 'MALE', true, 200);

INSERT INTO medical_organization(id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (1, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, medical_organization_id)
values (15, 'Первое взрослое отделение', 'ADULT', 1);

INSERT INTO patient_history(id, patient_id)
VALUES (1, 20),
       (2, 21),
       (3, 22);


INSERT INTO employee_history(id, dtype, is_public, employee_id, department_id)
values (5, 'DoctorHistory', true, 30, 15);

INSERT INTO talon(id, time, patient_history_id, doctor_history_id)
VALUES (1, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 7:00:0' DAY TO SECOND, 1, 5),
       (2, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 8:00:00', 2, 5),
       (3, date_trunc('day', CURRENT_DATE + INTERVAL '15 8:00:0' DAY TO SECOND), null, 5),
       (4, CAST(CURRENT_DATE AS TIMESTAMP) + interval '0 8:00:0' DAY TO SECOND, 1, 5),
       (5, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 8:00:00', null, 5),
       (6, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 8:00:00', 1, 5),
       (7, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 8:00:00', 3, 5);




--pass 123