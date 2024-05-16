TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE medical_organization CASCADE;
TRUNCATE TABLE department CASCADE;
TRUNCATE TABLE patient_history CASCADE;
TRUNCATE TABLE employee_history CASCADE;
TRUNCATE TABLE disease CASCADE;
TRUNCATE TABLE appeal CASCADE;
TRUNCATE TABLE visit_medical_services CASCADE;
TRUNCATE TABLE medical_service CASCADE;
TRUNCATE TABLE visit CASCADE;
TRUNCATE TABLE pay_price_of_medical_service CASCADE;

INSERT INTO role (id, name)
VALUES (300, 'DOCTOR'),
       (200, 'PATIENT');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (17, 'Doctor', 'doc17@mail.com', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K', '017', 'doc17',
        'docov17', 'docovich17 ', '1990-01-01', 'MALE', true, 300),
       (21, 'Patient', 'pat21@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '021', 'pat21',
        'pat21', 'pat21', '1990-02-02', 'MALE', true, 200),
       (22, 'Patient', 'pat22@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '022', 'pat22',
        'pat22', 'pat22', '1990-02-02', 'MALE', true, 200);
;

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date,
                                 full_employment_status_range)
VALUES (119, 'code119', 'name119', 'address119', 'ogrn119', '1990-01-01', '2030-01-01', 13.5);

INSERT INTO department (id, name, medical_organization_id)
VALUES (62, 'department1', 119);

INSERT INTO patient_history (id, patient_id)
VALUES (1, 21),
       (2, 22);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (5, 'DoctorHistory', true, 17, 62);


INSERT INTO disease (id, identifier, name, is_disabled, age_type, department_id)
VALUES (500, 'identifier0', 'name0', '0', 'ADULT', 62),
       (501, 'identifier1', 'name1', '0', 'ADULT', 62),
       (502, 'identifier2', 'name2', '0', 'ADULT', 62),
       (503, 'identifier3', 'name3', '0', 'CHILD', 62),
       (504, 'identifier4', 'caries', '0', 'ADULT', 62);


INSERT INTO appeal (id, closed_date, is_ready, insurance_type, order_id, patient_history_id, disease_id)
VALUES (300, '2023-04-07', '1', 'DMS', null, 1, 500),
       (301, '2023-03-20', '1', 'DMS', null, 1, 501),
       (302, '2023-03-10', '1', 'OMS', null, 1, 502),
       (303, '2023-03-11', '1', 'DMS', null, 2, 502),
       (304, '2023-04-08', '1', 'OMS', null, 2, 502),
       (305, '2023-04-09', '1', 'OMS', null, 2, 504);


INSERT INTO medical_service (id, identifier, name, is_disabled, department_id)
VALUES (1, '01', 'service1', FALSE, 62),
       (2, '02', 'service2', FALSE, 62),
       (3, '03', 'service3', FALSE, 62),
       (4, '04', 'cleaningToothCavity', FALSE, 62),
       (5, '05', 'nerveRemoval', FALSE, 62),
       (6, '06', 'temporaryFilling', FALSE, 62),
       (7, '07', 'temporaryFillingRemoval', FALSE, 62),
       (8, '08', 'permanentFilling', FALSE, 62);



INSERT INTO visit (id, day_of_visit, doctor_history_id, appeal_id)
VALUES (403, '2023-03-10', 5, 302),
       (402, '2023-04-04', 5, 300),
       (401, '2023-03-10', 5, 300),
       (400, '2023-02-10', 5, 300),
       (404, '2023-03-11', 5, 304),
       (405, '2023-03-20', 5, 305),
       (406, '2023-03-28', 5, 305),
       (407, '2023-04-03', 5, 305);



INSERT INTO visit_medical_services (visit_id, medical_service_id)
VALUES (403, 1),
       (400, 1),
       (400, 2),
       (401, 2),
       (402, 3),
       (404, 2),
       (405, 4),
       (405, 5),
       (405, 6),
       (406, 4),
       (406, 8),
       (407, 7),
       (407, 8);



INSERT INTO pay_price_of_medical_service (id, day_from, day_to, money, medical_service_id)
VALUES (1, '2022-12-10', null, 175, 3),
       (2, '2022-12-10', null, 150, 1),
       (3, '2022-12-10', null, 100, 2);



INSERT INTO oms_price_of_medical_service (id, day_from, day_to, yet, medical_service_id)
VALUES (1, '2022-12-10', null, 3.75, 3),
       (2, '2022-12-10', null, 1.0, 1),
       (3, '2022-12-10', null, 1.75, 2),
       (4, '2022-12-10', '2023-03-25', 2.5, 4),
       (5, '2023-03-26', null, 2.25, 4),
       (6, '2022-12-10', null, 3.75, 5),
       (7, '2022-12-10', null, 1.75, 6),
       (8, '2022-12-10', null, 1.0, 7),
       (9, '2022-12-10', '2023-03-30', 7.25, 8),
       (10, '2023-04-01', null, 6.75, 8);


INSERT INTO yet (id, price, day_from, day_to, medical_organization_id)
VALUES (1, 123.10, '2022-12-10', '2023-03-30', 119),
       (2, 130.50, '2023-04-01', null, 119);