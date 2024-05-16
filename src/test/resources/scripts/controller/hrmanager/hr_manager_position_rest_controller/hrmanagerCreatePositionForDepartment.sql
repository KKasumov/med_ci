INSERT INTO role (id, name)
VALUES (300, 'HR_MANAGER'),
       (400, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (30, 'Employee', 'hrmanager@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Arkady', 'Parovozov', 'Chuh-Chuh', '1990-06-18', 'MALE', true, 300),
       (400, 'Doctor', 'doctor51@email.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 51', 'Oleg', 'Sidorov', 'Andreevich', '1975-02-01', 'MALE', true, 400);

INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (4000, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (4000, 'Первое детское отделение', 'CHILD', 400, null, 4000);

insert into building(id, physical_address, medical_organization_id)
values (40, '555-fer', 4000);

insert into cabinet(id, number, name, building_id)
values (5, 400, 'Pediatrician', 40),
       (6, 500, 'Dentist', 40),
       (7, 600, 'Surgery', 40);

INSERT INTO position (id, name, days_for_vocation, department_id)
VALUES (1001, 'Терапевт', 42, 4000);

INSERT INTO university (id, name)
VALUES (4000, 'Астраханский ГМУ');

INSERT INTO diploma (id, serial_number, end_date, university_id)
VALUES (4000, '11111', '2010-06-13', 4000);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (4000, 'DoctorHistory', true, 30, 4000);

INSERT INTO labor_contract (id, start_date, end_date, part, employee_history_id, position_id, diploma_id)
VALUES (4000, '2017-07-13', null, 0.5, 4000, 1001, 4000);

INSERT INTO attestation (id, date_from, date_to, number, university_id, labor_contract_id)
VALUES (4000, '2017-07-13', '2022-06-13', '444-333-222', 4000, 4000);

INSERT INTO equipment (id, name, inventory_number, price, purchase_date, installation_date, disposal_date, position_id)
VALUES (10, 'Ultrasound', '2042OT', 14000, '2022-06-13', '2022-06-21', null, 1001),
       (11, 'Ultrasound', '2507OT', 140000, '2022-06-13', '2022-06-21', null, null);

INSERT INTO wage (id, date_start, date_end, value, position_id)
VALUES (400, '2022-06-13', null, 34000, 1001);


