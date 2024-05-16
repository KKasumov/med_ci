INSERT INTO role (id, name)
VALUES (100, 'HR_MANAGER'),
       (101, 'DOCTOR'),
       (102, 'DIRECTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (10, 'Employee', 'employee@mail.com', '$2a$12$bne09gQ3iWNENHOWNgFji.3H7XBqcBHv./xqulIbPs0lOntYUGSg.', '000-000-000 03', 'Gennady', 'Gennadyev', 'Gennadyevich', '1998-01-07', 'MALE', true, 100),
       (20, 'Doctor', 'doctor20@mail.com', '$2a$12$bne09gQ3iWNENHOWNgFji.3H7XBqcBHv./xqulIbPs0lOntYUGSg.', '000-000-000 20', 'Doctor20', 'Doctor20', 'Doctor20', '1990-06-25', 'MALE', true, 101),
       (30, 'Doctor', 'director@mail.com', '$2a$12$bne09gQ3iWNENHOWNgFji.3H7XBqcBHv./xqulIbPs0lOntYUGSg.', '000-000-000 20', 'Pavel', 'Pavlov', 'Ivanovich', '1962-06-25', 'MALE', true, 102),
       (40, 'Doctor', 'doctor40@mail.com', '$2a$12$bne09gQ3iWNENHOWNgFji.3H7XBqcBHv./xqulIbPs0lOntYUGSg.', '000-000-000 20', 'Petr', 'Ivanov', 'Vladimirovich', '1956-06-25', 'MALE', true, 101),
       (50, 'Doctor', 'doctor50@mail.com', '$2a$12$bne09gQ3iWNENHOWNgFji.3H7XBqcBHv./xqulIbPs0lOntYUGSg.', '000-000-000 20', 'Philipp', 'Voroncov', 'Olegovich', '1972-08-13', 'MALE', true, 101),
       (60, 'Doctor', 'doctor60@mail.com', '$2a$12$bne09gQ3iWNENHOWNgFji.3H7XBqcBHv./xqulIbPs0lOntYUGSg.', '000-000-000 20', 'Maxim', 'Kopanev', 'Sergeevich', '1985-09-23', 'MALE', true, 101),
       (70, 'Doctor', 'doctor70@mail.com', '$2a$12$bne09gQ3iWNENHOWNgFji.3H7XBqcBHv./xqulIbPs0lOntYUGSg.', '000-000-000 20', 'Ivan', 'Petrov', 'Andreevich', '1976-07-28', 'MALE', true, 101);


INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (1, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (12, 'Первое детское отделение', 'CHILD', null, 20, 1),
       (13, 'Взрослое отделение', 'ADULT', 50, 60, 1),
       (14, 'Первое взрослое отделение', 'ADULT', null, null, 1);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (5, 'DoctorHistory', true, 20, 12),
       (6, 'DoctorHistory', true, 40, 12),
       (7, 'DoctorHistory', true, 50, 13),
       (8, 'DoctorHistory', true, 60, 13),
       (9, 'DoctorHistory', true, 70, 14);

INSERT INTO position (id, name, days_for_vocation, department_id)
VALUES (1, 'Терапевт', 42, 13);

INSERT INTO university (id, name)
VALUES (1, 'tgma');

INSERT INTO diploma (id, serial_number, end_date, university_id)
VALUES (1, '11111', '2010-06-13', 1);



INSERT INTO labor_contract (id, start_date, end_date, part, employee_history_id, position_id, diploma_id)
VALUES (10, '2018-06-13', null, 0.5, 9, 1, 1 );