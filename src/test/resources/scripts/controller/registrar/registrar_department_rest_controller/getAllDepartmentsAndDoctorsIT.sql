INSERT INTO role (id, name)
VALUES (100, 'REGISTRAR'),
       (200, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (20, 'Employee', 'registrar@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 20', 'Registrar', 'Registrar', 'Registrar', '1998-03-01', 'MALE', true, 100),
       (30, 'Doctor', 'doctor30@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 30', 'doctor30', 'doctor30', 'doctor30', '1990-06-25', 'MALE', true, 200),
       (40, 'Doctor', 'doctor40@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 40', 'doctor40', 'doctor40', 'doctor40', '1990-06-25', 'MALE', true, 200),
       (50, 'Doctor', 'doctor50@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 50', 'doctor50', 'doctor50', 'doctor50', '1990-06-25', 'FEMALE', true, 200),
       (60, 'Doctor', 'doctor60@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 60', 'doctor60', 'doctor60', 'doctor60', '1990-06-25', 'MALE', true, 200),
       (70, 'Doctor', 'doctor70@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 70', 'doctor70', 'doctor70', 'doctor70', '1990-06-25', 'FEMALE', true, 200),
       (80, 'Doctor', 'doctor80@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 80', 'doctor80', 'doctor80', 'doctor80', '1990-06-25', 'MALE', true, 200),
       (90, 'Doctor', 'doctor90@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 90', 'doctor90', 'doctor90', 'doctor90', '1990-06-25', 'FEMALE', true, 200),
       (100, 'Doctor', 'doctor100@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-001 00', 'doctor100', 'doctor100', 'doctor100', '1990-06-25', 'FEMALE', true, 200),
       (110, 'Doctor', 'doctor110@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-001 10', 'doctor110', 'doctor110', 'doctor110', '1990-06-25', 'FEMALE', true, 200),
       (120, 'Doctor', 'doctor120@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-001 20', 'doctor120', 'doctor120', 'doctor120', '1990-06-25', 'MALE', true, 200),
       (130, 'Doctor', 'doctor130@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-001 30', 'doctor130', 'doctor130', 'doctor130', '1990-06-25', 'FEMALE', true, 200),
       (140, 'Doctor', 'doctor140@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-001 40', 'doctor140', 'doctor140', 'doctor140', '1990-06-25', 'FEMALE', true, 200);

INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (1, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (12, 'Первое детское отделение', 'CHILD', 30, null, 1),
       (13, 'Второе детское отделение', 'CHILD', null, 60, 1),
       (14, 'Первое взрослое отделение', 'ADULT', 90, null, 1),
       (15, 'Второе взрослое отделение', 'ADULT', null, null, 1),
       (16, 'No', 'NO', null, 40, 1),
       (17, 'No2', 'NO', 30, null, 1);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (5, 'DoctorHistory', true, 30, 12),
       (6, 'DoctorHistory', true, 40, 12),
       (7, 'DoctorHistory', true, 50, 12),
       (8, 'DoctorHistory', true, 60, 13),
       (9, 'DoctorHistory', true, 70, 13),
       (10, 'DoctorHistory', true, 80, 13),
       (11, 'DoctorHistory', true, 90, 14),
       (12, 'DoctorHistory', true, 100, 14),
       (13, 'DoctorHistory', true, 110, 14),
       (14, 'DoctorHistory', true, 120, 15),
       (15, 'DoctorHistory', true, 130, 15),
       (16, 'DoctorHistory', true, 140, 15);

INSERT INTO user_history (id, user_id)
VALUES (3, 30), (4, 40), (5, 50), (6, 60), (7, 70), (8, 80), (9, 90), (10, 100), (11, 110), (12, 120), (13, 130), (14, 140);

INSERT INTO contact (id, contact_type, value, user_history_id)
VALUES (1, 'TELEGRAM', '13', 3),
       (2, 'PHONE', '23', 3),
       (3, 'TELEGRAM', '34', 4),
       (4, 'PHONE', '45', 5),
       (5, 'TELEGRAM', '56', 6),
       (6, 'PHONE', '66', 6),
       (7, 'TELEGRAM', '77', 7),
       (8, 'PHONE', '87', 7),
       (11, 'TELEGRAM', '119', 9),
       (13, 'PHONE', '139', 9),
       (14, 'TELEGRAM', '1410', 10),
       (15, 'PHONE', '1510', 10),
       (16, 'TELEGRAM', '1611', 11),
       (17, 'PHONE', '1711', 11),
       (18, 'TELEGRAM', '1812', 12),
       (19, 'PHONE', '1912', 12),
       (20, 'TELEGRAM', '2013', 13),
       (21, 'PHONE', '2113', 13),
       (22, 'TELEGRAM', '2214', 14),
       (23, 'PHONE', '2314', 14),
       (24, 'ADDRESS', 'no', 3),
       (25, 'ADDRESS', 'no', 6),
       (26, 'ADDRESS', 'no', 9),
       (27, 'ADDRESS', 'no', 12);