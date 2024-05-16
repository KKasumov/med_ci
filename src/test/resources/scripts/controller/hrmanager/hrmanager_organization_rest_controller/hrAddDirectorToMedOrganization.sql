INSERT INTO role (id, name)
VALUES (700, 'HR_MANAGER'),
 (400, 'DIRECTOR'),
 (300, 'DOCTOR'),
 (500, 'CHIEF_DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (77, 'Employee', 'hr@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '777-777-777 777', 'Ivan', 'Ivanov', 'Ivanovich', '1970-01-01', 'MALE', true, 700),
 (14, 'Doctor', 'doc14@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '014', 'doc14', 'docov14', 'docovich14', '1990-01-01', 'MALE', true, 500),
 (19, 'Doctor', 'doc19@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '019', 'doc19', 'docov19', 'docovich19', '1990-01-01', 'MALE', true, 400),
 (18, 'Doctor', 'doc18@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '018', 'doc18', 'docov18', 'docovich18', '1990-01-01', 'MALE', true, 400),
 (16, 'Doctor', 'doc16@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '016', 'doc16', 'docov16', 'docovich16', '1990-01-01', 'MALE', true, 300),
 (17, 'Doctor', 'doc17@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '017', 'doc17', 'docov17', 'docovich17', '1990-01-01', 'MALE', true, 300),
 (13, 'Doctor', 'doc13@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '013', 'doc13', 'docov13', 'docovich13', '1990-01-01', 'MALE', true, 300),
 (12, 'Doctor', 'doc12@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '012', 'doc12', 'docov12', 'docovich12', '1990-01-01', 'MALE', true, 300);

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range)
VALUES(119, 'code119', 'name119', 'address119', 'ogrn119', '1990-01-01', '2030-01-01', 13.5);
INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id)
VALUES(116, 'code116', 'name116', 'address116', 'ogrn116', '1990-01-01', '2030-01-01', 13.5, 18);
INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES(117, 'code117', 'name117', 'address117', 'ogrn117', '1990-01-01', '2030-01-01', 13.5, 18, 17);

INSERT INTO department (id, name, medical_organization_id)
VALUES (60, 'department1', 116),
 (61, 'department1', 117),
 (62, 'department1', 119);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (130, 'DoctorHistory', true, 12, 60),
(131, 'DoctorHistory', true, 13, 61),
(132, 'DoctorHistory', true, 14, 62),
(133, 'DoctorHistory', true, 16, 60),
(134, 'DoctorHistory', true, 17, 61),
(135, 'DoctorHistory', true, 18, 62),
(136, 'DoctorHistory', true, 19, 60);


INSERT INTO position (id, name, days_for_vocation, department_id)
VALUES (100, 'position1', 42, 60),
 (101, 'position2', 42, 61),
 (102, 'position3', 42, 62);

INSERT INTO university (id, name)
VALUES (1, 'University1');

INSERT INTO diploma (id, serial_number, end_date, university_id)
VALUES (30, 'diploma1', '2090-01-01', 1),
 (31, 'diploma2', '2090-01-01', 1),
 (32, 'diploma1', '2090-01-01', 1),
 (33, 'diploma2', '2090-01-01', 1),
 (34, 'diploma1', '2090-01-01', 1),
 (35, 'diploma2', '2090-01-01', 1);

INSERT INTO labor_contract (id, start_date, end_date, part, employee_history_id, Position_id, diploma_id)
VALUES (160, '2010-01-01', '2090-01-01', 1.0, 130, 100, 30),
 (161, '2010-01-01', '2090-01-01', 1.0, 131, 101, 31),
 (162, '2010-01-01', '2090-01-01', 1.0, 134, 101, 32),
 (163, '2010-01-01', '2090-01-01', 1.0, 135, 102, 33),
 (164, '2010-01-01', '2090-01-01', 1.0, 136, 100, 34);