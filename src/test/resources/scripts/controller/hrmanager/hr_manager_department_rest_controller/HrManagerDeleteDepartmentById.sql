INSERT INTO role (id, name)
VALUES (700, 'HR_MANAGER'),
       (400, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (77, 'Employee', 'hr@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K',
        '777-777-777 777', 'Ivan', 'Ivanov', 'Ivanovich', '1970-01-01', 'MALE', true, 700),
       (51, 'Doctor', 'doctor51@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 51', 'Oleg', 'Sidorov', 'Andreevich', '1975-02-01', 'MALE', true, 400),
       (52, 'Doctor', 'doctor52@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 52', 'Irina', 'Petrova', 'Olegovna', '1980-08-08', 'FEMALE', true, 400),
       (53, 'Doctor', 'doctor53@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 53', 'Irina', 'Petrova', 'Olegovna', '1980-08-08', 'FEMALE', true, 400);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES
    (2222, 'code1', 'name1', null, 'ogrn1', CURRENT_TIMESTAMP, null, 20, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES
    (1111, 'name1', 'ADULT', null, null, 2222),
    (2222, 'name2', 'CHILD', null, null, 2222),
    (3333, 'name3', 'ADULT', null, null, 2222);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (11, 'DoctorHistory', true, 51, 1111),
       (22, 'DoctorHistory', true, 52, 1111),
       (33, 'DoctorHistory', true, 53, 1111);

INSERT INTO disease (id, identifier, name, is_disabled, age_type, department_id)
VALUES
    (400, 'identifier1', 'name1', false, 'CHILD', 2222),
    (401, 'identifier2', 'name2', false, 'CHILD', 2222),
    (402, 'identifier3', 'name3', false, 'CHILD', 2222);

INSERT INTO medical_service (id, identifier, name, is_disabled, department_id)
VALUES
    (1, '01', 'service1', FALSE, 3333);

INSERT INTO oms_price_of_medical_service (id, day_from, day_to, yet, medical_service_id)
VALUES
    (1, '2022-01-01', '2022-01-02', 100,1),
    (2, '2022-02-02', '2022-02-03', 150,1);

INSERT INTO pay_price_of_medical_service (id, day_from, day_to, money, medical_service_id)
VALUES
    (1, '2022-01-01', '2022-01-02', 100,1),
    (2, '2022-02-02', '2022-02-03', 150,1);

INSERT INTO building (id, physical_address, medical_organization_id)
VALUES
    (1, 'address', 2222);

INSERT INTO cabinet (id, number, name, building_id)
VALUES
    (1, 11, 'name1', 1);

INSERT INTO position (id, name, days_for_vocation, department_id, cabinet_id)
VALUES
    (1, 'name1', 12, 3333, 1),
    (2, 'name2', 11, 3333, 1);