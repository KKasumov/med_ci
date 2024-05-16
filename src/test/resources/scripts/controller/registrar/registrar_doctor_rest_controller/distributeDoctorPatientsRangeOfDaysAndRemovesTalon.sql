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
       (150, 'Patient', 'patient150@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'patient150', 'patient150', 'patient150', '1990-06-25', 'MALE', false, 100),
       (160, 'Patient', 'patient160@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'patient160', 'patient160', 'patient160', '1990-06-25', 'FEMALE', false, 100),
       (170, 'Patient', 'patient170@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'patient170', 'patient170', 'patient170', '1990-06-25', 'MALE', false, 100),
       (180, 'Patient', 'patient180@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'patient180', 'patient180', 'patient180', '1990-06-25', 'MALE', false, 100),
       (190, 'Patient', 'patient190@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'patient190', 'patient190', 'patient190', '1990-06-25', 'FEMALE', false, 100),
       (200, 'Patient', 'patient200@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'patient200', 'patient200', 'patient200', '1990-06-25', 'MALE', false, 100),
       (210, 'Patient', 'patient210@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'patient210', 'patient210', 'patient210', '1990-06-25', 'MALE', false, 100),
       (220, 'Patient', 'patient220@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'patient220', 'patient220', 'patient220', '1990-06-25', 'FEMALE', false, 100);


INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (1, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (12, 'Первое детское отделение', 'CHILD', null, null, 1),
       (13, 'Второе детское отделение', 'CHILD', null, null, 1),
       (14, 'Первое взрослое отделение', 'ADULT', null, null, 1);

INSERT INTO patient_history (id, patient_id)
VALUES
    (150, 150),
    (160, 160),
    (170, 170),
    (180, 180),
    (190, 190),
    (200, 200),
    (210, 210),
    (220, 220);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (5, 'DoctorHistory', true, 30, 12),
       (6, 'DoctorHistory', true, 40, 12),
       (7, 'DoctorHistory', true, 50, 12),
       (8, 'DoctorHistory', true, 60, 13),
       (9, 'DoctorHistory', true, 70, 14);

INSERT INTO talon (id, time, patient_history_id, doctor_history_id)
VALUES
    (1, date_trunc('minute', CURRENT_DATE + interval '0 10:00:0' DAY TO SECOND), 150, 5),
    (2, date_trunc('minute', CURRENT_DATE + interval '0 10:30:0' DAY TO SECOND), 160, 5),
    (3, date_trunc('minute', CURRENT_DATE + interval '2 11:00:0' DAY TO SECOND), 170, 5),
    (4, date_trunc('minute', CURRENT_DATE + interval '3 11:30:0' DAY TO SECOND), 180, 5),
    (5, date_trunc('minute', CURRENT_DATE + interval '4 12:00:0' DAY TO SECOND), 190, 5),
    (6, date_trunc('minute', CURRENT_DATE + interval '5 12:30:0' DAY TO SECOND), 200, 5),
    (7, date_trunc('minute', CURRENT_DATE + interval '14 13:00:0' DAY TO SECOND), 210, 5),
    (8, date_trunc('minute', CURRENT_DATE + interval '14 15:00:0' DAY TO SECOND), 220, 5),
    (9, date_trunc('minute', CURRENT_DATE + interval '14 16:00:0' DAY TO SECOND), 220, 5),
    (10, date_trunc('minute', CURRENT_DATE + interval '14 14:00:0' DAY TO SECOND), null, 5),

    (101, date_trunc('minute', CURRENT_DATE + interval '0 10:00:0' DAY TO SECOND), 200, 6),
    (102, date_trunc('minute', CURRENT_DATE + interval '0 10:30:0' DAY TO SECOND), null, 6),
    (103, date_trunc('minute', CURRENT_DATE + interval '2 11:00:0' DAY TO SECOND), null, 6),
    (104, date_trunc('minute', CURRENT_DATE + interval '3 11:30:0' DAY TO SECOND), 190, 6),
    (105, date_trunc('minute', CURRENT_DATE + interval '4 12:00:0' DAY TO SECOND), null, 6),
    (106, date_trunc('minute', CURRENT_DATE + interval '5 12:30:0' DAY TO SECOND), null, 6),
    (107, date_trunc('minute', CURRENT_DATE + interval '14 13:00:0' DAY TO SECOND), null, 6),

    (201, date_trunc('minute', CURRENT_DATE + interval '14 15:00:0' DAY TO SECOND), null, 7),

    (301, date_trunc('minute', CURRENT_DATE + interval '0 10:00:0' DAY TO SECOND), null, 8),
    (302, date_trunc('minute', CURRENT_DATE + interval '0 10:30:0' DAY TO SECOND), null, 8),
    (303, date_trunc('minute', CURRENT_DATE + interval '2 11:00:0' DAY TO SECOND), null, 8),
    (304, date_trunc('minute', CURRENT_DATE + interval '3 11:30:0' DAY TO SECOND), null, 8),
    (305, date_trunc('minute', CURRENT_DATE + interval '4 12:00:0' DAY TO SECOND), null, 8),
    (306, date_trunc('minute', CURRENT_DATE + interval '5 12:30:0' DAY TO SECOND), null, 8),
    (307, date_trunc('minute', CURRENT_DATE + interval '14 13:00:0' DAY TO SECOND), null, 8),
    (308, date_trunc('minute', CURRENT_DATE + interval '14 15:00:0' DAY TO SECOND), null, 8),
    (309, date_trunc('minute', CURRENT_DATE + interval '14 16:00:0' DAY TO SECOND), null, 8);
