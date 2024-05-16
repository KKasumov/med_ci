INSERT INTO role (id, name)
VALUES (300, 'DOCTOR'),
       (200, 'PATIENT'),
       (100,'REGISTRAR');


INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (777, 'Employee', 'rg@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '777-777-777 777', 'Ivan', 'Ivanov', 'Ivanovich', '1970-01-01', 'MALE', true, 100),
       (19, 'Doctor', 'doc19@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '019', 'doc19', 'docov19', 'docovich19', '1990-01-01', 'MALE', true, 300),
       (17, 'Doctor', 'doc17@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '017', 'doc17', 'docov17', 'docovich17', '1990-01-01', 'MALE', true, 300),
       (21, 'Patient', 'pat21@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '021', 'pat21', 'pat21', 'pat21', '1990-02-02', 'MALE', true, 200),
       (22, 'Patient', 'pat22@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '022', 'pat22', 'pat22', 'pat22', '1990-02-02', 'MALE', true, 200),
       (23, 'Patient', 'pat23@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '023', 'pat23', 'pat23', 'pat23', '1990-02-02', 'MALE', true, 200),
       (24, 'Patient', 'pat24@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '024', 'pat24', 'pat24', 'pat24', '1990-02-02', 'MALE', true, 200);

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range)
VALUES(119, 'code119', 'name119', 'address119', 'ogrn119', '1990-01-01', '2030-01-01', 13.5);

INSERT INTO department (id, name, medical_organization_id)
VALUES (62, 'department1', 119);

INSERT INTO patient_history (id, patient_id)
VALUES (1, 21),
       (2, 22),
       (3, 23),
       (4, 24);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (130, 'DoctorHistory', true, 17, 62),
       (131, 'DoctorHistory', true, 19, 62);

INSERT INTO talon (id, time, patient_history_id, doctor_history_id)
VALUES (222, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 08:00:00', 1, 130),
       (234, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 11:00:00', 2, 130),
       (235, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '0 15:00:00', null, 130),
       (223, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 09:00:00', null, 130),
       (224, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 10:00:00', null, 130),
       (225, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 11:00:00', 1, 130),
       (226, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 12:00:00', 3, 130),
       (227, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '4 09:00:00', 1, 130),
       (228, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '4 12:00:00', null, 130),
       (236, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '6 07:00:00', 4, 130),
       (229, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '28 13:00:00', null, 130),
       (237, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '30 14:00:00', 2, 130),
       (230, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '30 14:00:00', null, 130),
       (231, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '7 08:00:00', 1, 131),
       (232, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '2 09:00:00', null, 131),
       (233, CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '30 10:00:00', null, 131);
