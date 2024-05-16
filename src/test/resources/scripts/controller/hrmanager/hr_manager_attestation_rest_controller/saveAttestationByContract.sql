INSERT INTO role (id, name)
VALUES (7014, 'HR_MANAGER'),
       (4100, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (7010, 'Employee', 'hr111@gmail.com', '$2y$10$VBOLX.i/ZNv/8JKF6qtLCOx8jPZUodN4Bft/GLNy.t5Z3MCkGfZTa',
        '577-577-577 577', 'Gleb', 'Fedorov', 'Sergeevich', '1994-02-03', 'MALE', true, 7014),
       (4100, 'Doctor', 'doctor111@gmail.com', '$2y$10$IaWERDh9lp8JJZpwOBOsieD8/DoDvtMgfuMCakinSZiXntFKAqf6G',
        '477-477-477 477', 'Nikita', 'Ivanov', 'Nikolaevich', '1976-01-07', 'MALE', true, 4100),
       (4101, 'Doctor2', 'doctor222@gmail.com', '$2y$10$M2UIS.jmOHDLD164TICJxe.XPGZE.XfxPfMygH.8/ndkZU066NZ.i',
        '774-774-774 774', 'Oleg', 'Petrov', 'Andreevich', '1967-07-01', 'MALE', true, 4100);

INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (4100, '4100', 'Городская поликлиника №105', '210-0', '1986-01-27', 210.75);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (4100, 'Первое взрослое отделение', null, null, null, 4100);

INSERT INTO position (id, name, days_for_vocation, department_id)
VALUES (4100, 'Окулист', 45, 4100);

INSERT INTO university (id, name)
VALUES (4100, 'МГУ');

INSERT INTO diploma (id, serial_number, end_date, university_id)
VALUES (4100, '12345', '2018-05-05', 4100),
       (4101, '54321', '2019-05-05', 4100),
       (4102, '53214', '2020-05-05', 4100);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (4100, 'DoctorHistory', true, 4100, 4100),
       (4101, 'DoctorHistory', true, 4101, 4100),
       (4102, 'DoctorHistory', true, 7010, 4100);

INSERT INTO labor_contract (id, start_date, end_date, part, employee_history_id, position_id, diploma_id)
VALUES (4100, '2017-06-06', '2025-06-06', 0.5, 4100, 4100, 4100), -- нормальный договор
       (4101, '2018-06-06', '2022-06-06', 0.5, 4101, 4100, 4101), -- просроченный договор
       (4102, '2019-06-06', '2026-06-06', 0.7, 4102, 4100, 4102); -- договор, не прикрепленный к доктору
