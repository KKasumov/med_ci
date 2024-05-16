INSERT INTO role (id, name)
VALUES (2000, 'HR_MANAGER'),
       (3000, 'DOCTOR'),
       (4000, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (2500, 'Employee', 'hr@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K',
        '777-777-777 777', 'Ivan', 'Ivanov', 'Ivanovich', '1988-03-23', 'MALE', '1', 2000),
       (3500, 'Doctor', 'doctor@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 51', 'Oleg', 'Sidorov', 'Andreevich', '1975-02-01', 'MALE', '1', 3000);

INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (5000, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (6000, 'Первое детское отделение', 'CHILD', 3500, null, 5000);

INSERT INTO building(id, physical_address, medical_organization_id)
VALUES (9000, 'Москва', 5000);

INSERT INTO cabinet(id, number, name, building_id)
VALUES (8000, 10, 'Терапевт', 9000),
       (8001, 11, 'ЛОР', 9000),
       (8002, 12, 'Окулист', 9000);

INSERT INTO position (id, name, days_for_vocation, department_id, cabinet_id)
VALUES (10000, 'Терапевт', 42, 6000, 8000),
       (10001, 'ЛОР', 42, 6000, 8001),
       (10002, 'Окулист', 42, 6000, 8002);

INSERT INTO wage (id, date_start, date_end, value, position_id)
VALUES (11000, '2021-01-01', '2021-01-31', 5000.00, 10000),
       (11001, '2021-02-01', '2021-02-28', 4000.00, 10000),
       (11002, '2021-03-01', null, 3000.00, 10000),
       (11005, '2021-01-01', null, 3000.00, 10001);

INSERT INTO employee_history(id, dtype, is_public, employee_id, department_id)
VALUES (101, 'DoctorHistory', true, 3500, 6000);

INSERT INTO university(id, name)
VALUES (50, 'АГМА');

INSERT INTO diploma(id, serial_number, end_date, university_id)
VALUES (500, '2312-1A-421', '2010-08-01', 50);

INSERT INTO labor_contract(id, start_date, end_date, part, employee_history_id, position_id, diploma_id)
VALUES (100, '2021-02-15', '2021-02-20', 1.75, 101, 10000, 500),
       (101, '2021-04-15', '2021-05-15', 1.75, 101, 10000, 500);