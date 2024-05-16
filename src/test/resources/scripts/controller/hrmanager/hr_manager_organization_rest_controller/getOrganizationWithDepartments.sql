INSERT INTO role (id, name)
VALUES (300, 'HR_MANAGER'),
       (500, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (30, 'Employee', 'hrmanager@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Arkady', 'Parovozov', 'Chuh-Chuh', '1990-06-18', 'MALE', true, 300),
       (50, 'Doctor', 'doctorcox50@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 30', 'Percival', 'Cox', 'Ulysses', '1990-06-25', 'MALE', true, 500),
       (51, 'Doctor', 'iochiefdoctor51@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 40', 'iochiefdoctor51', 'iochiefdoctor51', 'iochiefdoctor51', '1990-06-25', 'MALE', true, 500),
       (52, 'Doctor', 'doctorhouse52@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 50', 'Gregory', 'House', 'chiefdoctor52', '1990-06-25', 'MALE', true, 500),
       (53, 'Doctor', 'iochiefdoctor53@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 60', 'iochiefdoctor53', 'iochiefdoctor53', 'iochiefdoctor53', '1990-06-25', 'MALE', true, 500),
       (54, 'Doctor', 'doctor54@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 70', 'doctor54', 'doctor54', 'doctor54', '1990-06-25', 'FEMALE', true, 500),
       (55, 'Doctor', 'doctor55@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 80', 'doctor55', 'doctor55', 'doctor55', '1990-06-25', 'MALE', true, 500),
       (56, 'Doctor', 'doctor56@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 90', 'doctor56', 'doctor56', 'doctor56', '1990-06-25', 'FEMALE', true, 500),
       (57, 'Doctor', 'doctor57@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-001 00', 'doctor57', 'doctor57', 'doctor57', '1990-06-25', 'FEMALE', true, 500),
       (58, 'Doctor', 'doctor58@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-001 10', 'doctor58', 'doctor58', 'doctor58', '1990-06-25', 'FEMALE', true, 500),
       (59, 'Doctor', 'dirdoctor59@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-001 20', 'dirdoctor59', 'dirdoctor59', 'dirdoctor59', '1990-06-25', 'MALE', true, 500);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date,
                                  full_employment_status_range, director_id, io_director_id)
VALUES (1, '1123', 'Городская поликлиника', 'Chelyabinsk', '202-12', '2010-09-09', '2099-09-09', 223.75, 59, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (1001, 'Первое детское отделение', 'CHILD', 50, null, 1),
       (1002, 'Второе детское отделение', 'CHILD', null, 51, 1),
       (1003, 'Первое взрослое отделение', 'ADULT', 52, null, 1),
       (1004, 'Второе взрослое отделение', 'ADULT', null, 53, 1);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (5, 'DoctorHistory', true, 50, 1001),
       (6, 'DoctorHistory', true, 51, 1002),
       (7, 'DoctorHistory', true, 52, 1003),
       (8, 'DoctorHistory', true, 53, 1004),
       (9, 'DoctorHistory', true, 54, 1001),
       (10, 'DoctorHistory', true, 55, 1001),
       (11, 'DoctorHistory', true, 56, 1002),
       (12, 'DoctorHistory', true, 57, 1003),
       (13, 'DoctorHistory', true, 58, 1003),
       (14, 'DoctorHistory', true, 59, 1004);
