INSERT INTO role (id, name)
VALUES
    (100, 'PATIENT'),
    (200, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Patient', 'gena@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 03', 'Gennady', 'Gennadyev', 'Gennadyevich', '1998-01-07', 'MALE', true, 100),
    (20, 'Patient', 'ivan@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (30, 'Doctor', 'doctor@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 01', 'Peter', 'Petrov', 'Petrovich', '1990-07-26', 'MALE', true, 200),
    (40, 'Doctor', 'doctor2@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 02', 'Vasily', 'Petrov', 'Petrovich', '1990-07-26', 'MALE', true, 200);


INSERT INTO patient_history (id, patient_id)
VALUES
    (101, 10),
    (102, 20);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES
    (2222, 'code1', 'name1', null, 'ogrn1', CURRENT_TIMESTAMP, null, 20, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES
    (1111, 'name1', null, null, null, 2222);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES
    (201, 'DoctorHistory', true, 30, 1111),
    (202, 'DoctorHistory', true, 40, 1111);

INSERT INTO talon (id, time, patient_history_id, doctor_history_id)
VALUES (404, date_trunc('minute', CURRENT_DATE + interval '0 0:30:0' DAY TO SECOND), null, 201), --+
       (405, date_trunc('minute', CURRENT_DATE - interval '1 0:30:0' DAY TO SECOND), null, 201),
       (406, date_trunc('minute', CURRENT_DATE - interval '0 2:30:0' DAY TO SECOND), 102, 201),
       (407, date_trunc('minute', CURRENT_DATE + interval '1 0:30:0' DAY TO SECOND), 101, 202),
       (408, date_trunc('minute', CURRENT_DATE + interval '0 9:30:0' DAY TO SECOND), 101, 201), --+
       (409, date_trunc('minute', CURRENT_DATE + interval '0 14:30:0' DAY TO SECOND), 102, 202),
       (410, date_trunc('minute', CURRENT_DATE + interval '7 0:30:0' DAY TO SECOND), 102, 202),
       (411, date_trunc('minute', CURRENT_DATE + interval '0 20:30:0' DAY TO SECOND), 102, 201), --+
       (412, date_trunc('minute', CURRENT_DATE + interval '0 6:30:0' DAY TO SECOND), 101, 202),
       (413, date_trunc('minute', CURRENT_DATE - interval '0 0:30:0' DAY TO SECOND), 102, 202),
       (414, date_trunc('minute', CURRENT_DATE - interval '4 0:30:0' DAY TO SECOND), null, 202),
       (415, date_trunc('minute', CURRENT_DATE - interval '0 8:30:0' DAY TO SECOND), null, 202),
       (416, date_trunc('minute', CURRENT_DATE + interval '8 0:30:0' DAY TO SECOND), 101, 202),
       (417, date_trunc('minute', CURRENT_DATE + interval '9 0:30:0' DAY TO SECOND), 102, 201);