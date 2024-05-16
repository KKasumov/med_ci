INSERT INTO role (id, name)
VALUES
    (1, 'PATIENT'),
    (2, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Patient', 'patient1@mail.com', '$2a$12$hHcSYcBjLpyAVqRYMiwjpOT/X3uYNsTvy.UocDsiQcr.4UBVrBUny', '000-000-000 03', 'f_patient1', 'l_patient1', 'p_patient1', '1998-01-07', 'FEMALE', true, 1),
    (20, 'Patient', 'patient2@mail.com', '$2a$12$JvBCVKVP/9ntWyaSWmwGWOcnDYDazYIiEJrdk9UUHKqWwKOg11NC6', '000-000-000 00', 'f_patient2', 'l_patient2', 'p_patient2', '1990-06-25', 'MALE', true, 1),
    (30, 'Patient', 'patient3@mail.com', '$2a$12$atqBUjOnoqZZCdF.1zQBVuUw/rDt8ypwdP0pGebu/xbQb6oPUvrvS', '000-000-000 00', 'f_patient3', 'l_patient3', 'p_patient3', '1990-07-25', 'MALE', true, 1),
    (40, 'Doctor', 'doctor1@mail.com', '$2a$12$q56lux/Q6dB5KdcUp.jM7eTaQ66y/Yeb8dZxvNAjcpHvhT18jKJ.6', '000-000-000 01', 'f_doctor1', 'l_doctor1', 'p_doctor1', '1990-07-26', 'MALE', true, 2),
    (50, 'Doctor', 'doctor2@mail.com', '$2a$12$xlbZF90xRTgq9k6QBXyHEu/dD41d2/W6rJEOqvbWa5O6Mw6t.CG1m', '000-000-000 02', 'f_doctor2', 'l_doctor2', 'p_doctor2', '1990-07-26', 'FEMALE', true, 2);


INSERT INTO patient_history (id, patient_id)
VALUES
    (101, 10),
    (201, 20),
    (301, 30);


INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES
    (22, 'code1', 'name1', null, 'ogrn1', '1990-10-1', null, 20, null, null);


INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES
    (11, 'name1', null, null, null, 22);


INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES
    (21, 'DoctorHistory', true, 40, 11),
    (22, 'DoctorHistory', true, 50, 11);


INSERT INTO talon (id, time, patient_history_id, doctor_history_id)
VALUES
    (111,'2023-03-02 16:49', 101, 21),
    (222,'2023-03-02 16:49', 201, 22),
    (333,'2023-03-02 16:49', null, 21);