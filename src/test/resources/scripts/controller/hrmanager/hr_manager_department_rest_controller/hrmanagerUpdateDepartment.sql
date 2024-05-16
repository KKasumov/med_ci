INSERT INTO role (id, name)
VALUES (700, 'HR_MANAGER'),
       (400, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (77, 'Employee', 'hr@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K',
        '777-777-777 777', 'Ivan', 'Ivanov', 'Ivanovich', '1970-01-01', 'MALE', '1', 700),
       (51, 'Doctor', 'doctor51@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 51', 'Oleg', 'Sidorov', 'Andreevich', '1975-02-01', 'MALE', '1', 400),
       (52, 'Doctor', 'doctor52@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 52', 'Irina', 'Petrova', 'Olegovna', '1980-08-08', 'FEMALE', '1', 400),
       (53, 'Doctor', 'doctor53@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 53', 'Irina', 'Petrova', 'Olegovna', '1980-08-08', 'FEMALE', '1', 400);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES
    (2222, 'code1', 'name1', null, 'ogrn1', CURRENT_TIMESTAMP, null, 20, null, null),
    (4444, 'code2', 'name2', null, 'ogrn2', CURRENT_TIMESTAMP, null, 20, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES
    (1111, 'name1', 'ADULT', null, null, 2222),
    (2222, 'name2', 'CHILD', null, null, 2222),
    (3333, 'name3', 'ADULT', null, null, 2222),
    (4444, 'name4', 'ADULT', null, null, 4444);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (11, 'DoctorHistory', '1', 51, 1111),
       (22, 'DoctorHistory', '1', 52, 1111),
       (33, 'DoctorHistory', '1', 53, 1111);

INSERT INTO disease (id, identifier, name, is_disabled, age_type, department_id)
VALUES
    (400, 'identifier1', 'name1', '0', 'CHILD', 2222),
    (401, 'identifier2', 'name2', '0', 'CHILD', 2222),
    (402, 'identifier3', 'name3', '0', 'CHILD', 2222);

INSERT INTO medical_service (id, identifier, name, is_disabled, department_id)
VALUES
    (1, '01', 'service1', '0', 3333);
