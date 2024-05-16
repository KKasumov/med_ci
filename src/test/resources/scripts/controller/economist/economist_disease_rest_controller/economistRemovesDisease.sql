INSERT INTO role (id, name)
VALUES
    (100, 'PATIENT'),
    (101, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Patient', 'gena@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 03', 'Gennady', 'Gennadyev', 'Gennadyevich', '1998-01-07', 'MALE', true, 100),
    (20, 'Patient', 'peter@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 02', 'Peter', 'Petrov', 'Petrovich', '1990-06-25', 'MALE', true, 100),
    (30, 'Employee', 'economist@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', true, 101);

INSERT INTO patient_history (id, patient_id)
VALUES
    (200, 10),
    (201, 20);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES
    (2222, 'code1', 'name1', null, 'ogrn1', CURRENT_TIMESTAMP, null, 20, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES
    (1111, 'name1', null, null, null, 2222);

INSERT INTO disease (id, identifier, name, is_disabled, age_type, department_id)
VALUES
    (400, 'identifier1', 'name1', false, 'ADULT', null), --удаляемый и затем проверка на несуществующее заболевание
    (401, 'identifier2', 'name2', false, 'ADULT', 1111), --занят отделением
    (403, 'identifier4', 'name4', false, 'ADULT', null), --занят обращением
    (405, 'identifier6', 'name6', false, 'ADULT', 1111), --занят обращением и отделением
    (406, 'identifier7', 'name7', false, 'ADULT', null), --удаляемый
    (407, 'identifier8', 'name8', false, 'ADULT', null); --удаляемый


INSERT INTO appeal (id, closed_date, is_ready, insurance_type, order_id, patient_history_id, disease_id)
VALUES
    (300, '2023-06-10', true, 'oms', null, 200, 401),
    (302, '2023-08-20', true, 'oms', null, 200, 403),
    (303, '2023-09-25', true, 'oms', null, 201, 405);
