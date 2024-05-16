INSERT INTO role (id, name)
VALUES (1000, 'PATIENT'),
       (2000, 'DOCTOR'),
       (3000, 'REGISTRAR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, role_id,
                   is_enabled)
VALUES (1000, 'Patient', 'patient1000@mail.ru', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '242352345', 'PatientID1000', 'PatientID1000', 'PatientID1000', '2020-02-20', 'MALE', 1000, true),
       (1001, 'Patient', 'patient1001@mail.ru', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '242352345', 'PatientID1001', 'PatientID1001', 'PatientID1001', '2020-02-20', 'MALE', 1000, true),
       (2000, 'Doctor', 'doctor@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '242352345',
        'Doctor', 'Doctor', 'Doctor', '2020-02-20', 'MALE', 2000, true),
       (3000, 'Employee', 'registrar@mail.ru', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '242352345', 'Registrator', 'Registrator', 'Registrator', '2020-02-20', 'MALE', 3000, true);


INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date,
                                  full_employment_status_range, director_id, io_director_id)
VALUES (1, 'H-code', 'Hospital', 'Astra', 'city', '2020-02-20', '2020-02-20', 13.00, 3000, 3000);


INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (1, 'Приемное', 'ADULT', 2000, 2000, 1),
       (2, 'Терапевтическое', 'ADULT', 2000, 2000, 1);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id, add_talon_auto)
VALUES (1, 'DoctorHistory', true, 2000, 1, true);

INSERT INTO patient_history (id, patient_id)
VALUES (1, 1000),
       (2, 1001);

INSERT INTO talon (id, time, patient_history_id, doctor_history_id)
VALUES (1, '2023-05-26 04:05:06.000', null, 1),
       (2, '2023-05-26 04:05:06.000', 2, 1);
