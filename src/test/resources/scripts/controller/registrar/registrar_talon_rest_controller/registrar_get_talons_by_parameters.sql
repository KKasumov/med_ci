INSERT INTO role (id, name)
VALUES (700, 'HR_MANAGER'),
       (300, 'DOCTOR'),
       (200, 'PATIENT'),
       (100, 'REGISTRAR');


INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (77, 'Employee', 'hr@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '777-777-777 777', 'Ivan', 'Ivanov', 'Ivanovich', '1970-01-01', 'MALE', true, 100),
       (14, 'Doctor', 'doc14@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '014', 'doc14', 'docov14', 'docovich14', '1990-01-01', 'MALE', true, 300),
       (19, 'Doctor', 'doc19@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '019', 'doc19', 'docov19', 'docovich19', '1990-01-01', 'MALE', true, 300),
       (18, 'Doctor', 'doc18@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '018', 'doc18', 'docov18', 'docovich18', '1990-01-01', 'MALE', true, 300),
       (16, 'Doctor', 'doc16@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '016', 'doc16', 'docov16', 'docovich16', '1990-01-01', 'MALE', true, 300),
       (17, 'Doctor', 'doc17@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '017', 'doc17', 'docov17', 'docovich17', '1990-01-01', 'MALE', true, 300),
       (13, 'Doctor', 'doc13@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '013', 'doc13', 'docov13', 'docovich13', '1990-01-01', 'MALE', true, 300),
       (21, 'Patient', 'pat21@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '021', 'pat21', 'pat21', 'pat21', '1990-02-02', 'MALE', true, 200),
       (22, 'Patient', 'pat22@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '022', 'pat22', 'pat22', 'pat22', '1990-02-02', 'MALE', true, 200);

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range)
VALUES(119, 'code119', 'ГБУЗ Пензенский областной клинический центр специализированных видов медицинской помощи', 'address119', 'ogrn119', '1990-01-01', '2030-01-01', 13.5);

INSERT INTO department (id, name, medical_organization_id, age_type)
VALUES (60, 'Инфекционное отделение', 119, 'ADULT'),
       (61, 'Хирургическое отделение', 119, 'CHILD'),
       (62, 'Терапевтический корпус', 119, 'ADULT');

INSERT INTO patient_history (id, patient_id)
VALUES (10, 21),
       (11, 22);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (130, 'DoctorHistory', true, 13, 60),
       (131, 'DoctorHistory', true, 14, 60),
       (132, 'DoctorHistory', true, 18, 61),
       (133, 'DoctorHistory', true, 16, 61),
       (134, 'DoctorHistory', true, 17, 61);

INSERT INTO talon (id, time, patient_history_id, doctor_history_id)
VALUES (10, '2023-05-26 04:05:06.000', null, 130),
       (11, '2023-05-26 04:20:06.000', 11, 130),
       (12, '2023-05-26 04:40:06.000', null, 130),
       (13, '2023-05-26 05:05:06.000', null, 131),
       (14, '2023-05-26 05:20:06.000', 10, 131),
       (15, '2023-05-26 05:40:06.000', null, 131),
       (16, '2023-05-26 06:05:06.000', null, 131),
       (17, '2023-05-27 04:05:06.000', null, 131),
       (18, '2023-05-27 04:20:06.000', null, 130),
       (19, '2023-05-27 04:40:06.000', 11, 131),
       (20, '2023-05-27 03:15:06.000', null, 132),
       (21, '2023-05-28 03:45:06.000', null, 132),
       (22, '2023-05-28 04:05:06.000', null, 132),
       (23, '2023-05-28 04:25:06.000', 10, 132),
       (24, '2023-05-28 04:55:06.000', null, 133),
       (25, '2023-05-28 05:05:06.000', null, 132),
       (26, '2023-05-29 04:05:06.000', null, 133),
       (27, '2023-05-29 04:30:06.000', null, 133),
       (28, '2023-05-29 04:55:06.000', null, 130),
       (29, '2023-05-29 06:30:06.000', 11, 133),
       (30, '2023-05-30 02:10:06.000', null, 133),
       (31, '2023-05-30 02:35:06.000', null, 130),
       (32, '2023-05-30 02:55:06.000', 10, 133);

