INSERT INTO role (id, name)
VALUES
    (100, 'PATIENT'),
    (101, 'ECONOMIST'),
    (102, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Patient', 'gena@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 03', 'Gennady', 'Gennadyev', 'Gennadyevich', '1998-01-07', 'MALE', '1', 100),
    (20, 'Patient', 'peter@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 02', 'Peter', 'Petrov', 'Petrovich', '1990-06-25', 'MALE', '1', 100),
    (30, 'Employee', 'economist@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', '1', 101),
    (40, 'Employee', 'doctor@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Neivan', 'Ivanov', 'Ivanovich', '1998-03-04', 'MALE', '1', 102);

INSERT INTO patient_history (id, patient_id)
VALUES
    (200, 10),
    (201, 20);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES
    (2222, 'code1', 'name1', null, 'ogrn1', CURRENT_TIMESTAMP, null, 20, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES
    (1111, 'name1', null, null, null, 2222),
    (2222, 'name1', null, null, null, 2222);

INSERT INTO medical_service (id, identifier, name, is_disabled, department_id)
VALUES
    (40, '01', 'service1', '0', null), --удаляемый
    (41, '02', 'service2', '0', 1111), --занят отделением
    (42, '03', 'service3', '0', null); --занят посещением

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (5, 'DoctorHistory', '1', 30, 1111),
       (6, 'DoctorHistory', '1', 40, 2222);

INSERT INTO disease (id, identifier, name, is_disabled, age_type, department_id)
VALUES
    (500, 'identifier1', 'name1', '0', 'ADULT', null),
    (501, 'identifier2', 'name2', '0', 'ADULT', 1111),
    (502, 'identifier4', 'name4', '0', 'ADULT', 2222);

INSERT INTO appeal (id, closed_date, is_ready, insurance_type, order_id, patient_history_id, disease_id)
VALUES
    (300, '2023-01-10', '1', 'oms', null, 200, 500),
    (302, '2023-08-20', '1', 'oms', null, 200, 501),
    (303, '2023-08-10', '1', 'oms', null, 201, 502);

INSERT INTO visit (id, day_of_visit, doctor_history_id, appeal_id)
VALUES
     (400, '2023-08-10', 6, 303);

INSERT INTO visit_medical_services (visit_id, medical_service_id)
VALUES (400, 42);

