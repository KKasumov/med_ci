INSERT INTO role (id, name)
VALUES (1, 'PATIENT'),
       (4, 'DOCTOR'),
       (2, 'ECONOMIST'),
       (3, 'REGISTRAR');

INSERT INTO users (id, dtype, email, "password", snils, first_name, last_name, patronymic, birthday, gender, role_id,
                   is_enabled)
VALUES (40, 'Doctor', 'doctor40@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '242352345',
        'Doctor40', 'Doctor40', 'Doctor40', '2020-02-20', 'MALE', 4, true),
       (10, 'Patient', 'patient10@mail.ru', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '242352345',
        'Patient10', 'Patient10', 'Patient10', '2020-02-20', 'MALE', 1, true),
       (20, 'Doctor', 'doctor20@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '242352345',
        'Doctor20', 'Doctor20', 'Doctor20', '2020-02-20', 'MALE', 4, true),
       (30, 'Employee', 'registrar@mail.ru', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '242352345', 'Restrar30', 'Restrar30', 'Restrar30', '2020-02-20', 'MALE', 3, true),
       (50, 'Employee', 'economist50@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '12345678', 'Economist50', 'Economist50', 'Economist50', '2020-02-20', 'MALE', 2, true);


INSERT INTO medical_organization (id, code, "name", legal_address, ogrn, start_date, end_date,
                                  full_employment_status_range, director_id, io_director_id)
VALUES (1, 'ds', 'Hospital', 'asd', 'asd', '2020-02-20', '2020-02-20', 13.00, 30, 30);


INSERT INTO department (id, "name", age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (1, 'Хирургия', 'ADULT', 20, 20, 1),
       (2, 'Терапевтическое', 'ADULT', 40, 40, 1);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id, add_talon_auto)
VALUES (1, 'DoctorHistory', true, 20, 2, false),
       (2, 'DoctorHistory', true, 40, 2, false),
       (3, 'DoctorHistory', true, 30, 1, true);

INSERT INTO patient_history (id, patient_id)
VALUES (10, 10);

INSERT INTO talon (id, "time", patient_history_id, doctor_history_id)
VALUES (300, '1999-01-08 04:05:06.000', NULL, 1),
       (500, '1999-01-08 04:05:06.000', NULL, 3),
       (400, '1999-01-08 04:05:06.000', 10, 2),
       (20, '1999-01-08 04:05:06.000', 10, 2),
       (10, '1999-01-08 04:05:06.000', 10, 1);
