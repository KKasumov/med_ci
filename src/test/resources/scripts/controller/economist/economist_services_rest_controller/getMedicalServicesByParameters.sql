INSERT INTO role
(id, name)
VALUES (1, 'PATIENT'),
       (2, 'ECONOMIST');

INSERT INTO users
(id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, role_id, is_enabled)
VALUES (20, 'Patient', 'ivan@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', 'snils',
        'first', 'last', 'patronimic', '2020-02-20', 'MALE', 1, true),
       (40, 'Patient', 'ivan1@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', 'snils',
        'first', 'last', 'patronimic', '2020-02-20', 'MALE', 1, true),
       (60, 'Patient', 'ivan2@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '0123456789',
        'Elena', 'Egorova', 'patronomic', '2020-02-20', 'MALE', 2, true),
       (30, 'Employee', 'economist@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', 'aaaaaa',
        'sdsasd', 'vfvfvf', 'Ivanovich', '2020-02-20', 'MALE', 2, true);

INSERT INTO medical_organization
(id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES (2, 'cxcxc', 'zxc', 'asas', 'dasd', '2000-02-20', '2020-02-20', 43.00, 60, 20),
       (1, 'ds', 'asd', 'asd', 'asd', '2020-02-20', '2020-02-20', 13.00, 30, 30);

INSERT INTO department
(id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (1, 'name', 'CHILD', 20, 20, 1),
       (20, 'mmmm', 'ADULT', 30, 30, 2),
       (2, 'mmmm', 'ADULT', 60, 40, 1),
       (30, 'nonono', 'NO', 30, 20, 1);

INSERT INTO medical_service
(id, identifier, name, is_disabled, department_id)
VALUES (1, 'Хирургия', 'Аппендицит', true, 1),
       (2, 'Терапевт', 'Насморк', true, 2),
       (5, 'ЛОР', 'Нос', true, 1),
       (10, 'Венеролог', 'Вена', true, 20),
       (20, 'Бухгалтерия', 'Бухгалтер', true, 30);


