INSERT INTO role (id, name)
VALUES (7000, 'HR_MANAGER'),
       (4000, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (8000, 'Employee', 'hr@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K',
        '777-777-777 777', 'Ivan', 'Ivanov', 'Ivanovich', '1970-01-01', 'MALE', '1', 7000),
       (4000, 'Doctor', 'doctor51@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 51', 'Oleg', 'Sidorov', 'Andreevich', '1975-02-01', 'MALE', '1', 4000),
       (3000, 'Doctor', 'doctor52@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 51', 'Oleg1', 'Sidorov1', 'Andreevich1', '1975-02-01', 'MALE', '1', 4000),
       (4500, 'Doctor', 'doctor54@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 51', 'Oleg4', 'Sidorov4', 'Andreevich4', '1975-02-01', 'MALE', '1', 4000),
       (3500, 'Doctor', 'doctor53@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 53', 'Oleg3', 'Sidorov3', 'Andreevich3', '1975-02-01', 'MALE', '1', 4000);

INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (45, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (55, 'Первое детское отделение', 'CHILD', 4000, null, 45),
       (60, 'Второе детское отделение', 'CHILD', 4500, null, 45);

INSERT INTO position (id, name, days_for_vocation, department_id)
VALUES (65, 'Терапевт', 42, 55);

INSERT INTO university (id, name)
VALUES (75, 'Астраханский ГМУ');

INSERT INTO diploma (id, serial_number, end_date, university_id)
VALUES (85, '11111', '2010-06-13', 75);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (95, 'DoctorHistory', true, 4000, 55),
       (96, 'DoctorHistory', true, 3000, 55),
       (97, 'DoctorHistory', true, 3500, 55),
       (98, 'DoctorHistory', true, 4500, 60);

INSERT INTO labor_contract (id, start_date, end_date, part, employee_history_id, position_id, diploma_id)
VALUES (105, '2017-07-13', CURRENT_DATE, 0.5, 95, 65, 85),
       (106, '2017-07-13', CURRENT_DATE, 0.5, 96, 65, 85),
       (107, '2017-07-13', CURRENT_DATE, 0.5, 97, 65, 85),
       (108, '2017-07-13', CURRENT_DATE, 0.5, 98, 65, 85);

INSERT INTO attestation (id, date_from, date_to, number, university_id, labor_contract_id)
VALUES (114, '2017-07-13', CURRENT_DATE - 1, '444-333-666', 75, 105),
       (115, '2017-07-13', CURRENT_DATE - 1, '444-333-222', 75, 105),
       (116, '2017-07-13', CURRENT_DATE, '444-333-221', 75, 106),
       (117, '2017-07-13', CURRENT_DATE + 16, '444-333-223', 75, 107),
       (118, '2017-07-13', CURRENT_DATE + 16, '444-333-226', 75, 107),
       (119, '2017-07-13', CURRENT_DATE + 29, '444-333-228', 75, 108);
