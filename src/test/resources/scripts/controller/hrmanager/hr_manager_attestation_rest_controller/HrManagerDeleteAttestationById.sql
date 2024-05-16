INSERT INTO role (id, name)
VALUES (7000, 'HR_MANAGER'),
       (4000, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (7000, 'Employee', 'hr@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K',
        '777-777-777 777', 'Ivan', 'Ivanov', 'Ivanovich', '1970-01-01', 'MALE', '1', 7000),
       (4000, 'Doctor', 'doctor51@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 51', 'Oleg', 'Sidorov', 'Andreevich', '1975-02-01', 'MALE', '1', 4000);


INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (4000, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (4000, 'Первое детское отделение', 'CHILD', 7000, null, 4000);

INSERT INTO position (id, name, days_for_vocation, department_id)
VALUES (4000, 'Терапевт', 42, 4000);

INSERT INTO university (id, name)
VALUES (4000, 'Астраханский ГМУ');

INSERT INTO diploma (id, serial_number, end_date, university_id)
VALUES (4000, '11111', '2010-06-13', 4000);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (4000, 'DoctorHistory', true, 4000, 4000);


INSERT INTO labor_contract (id, start_date, end_date, part, employee_history_id, position_id, diploma_id)
VALUES (4000, '2017-07-13', null, 0.5, 4000, 4000, 4000);


INSERT INTO attestation (id, date_from, date_to, number, university_id, labor_contract_id)
VALUES (4000, '2017-07-13', '2022-06-13', '444-333-222', 4000, 4000);

