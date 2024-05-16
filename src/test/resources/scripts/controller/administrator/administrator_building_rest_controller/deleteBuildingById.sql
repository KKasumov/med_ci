INSERT INTO role (id, name)
VALUES (4014, 'ADMIN'),
       (6010, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (5567, 'Employee', 'admin11@gmail.com', '$2y$10$KwNNqdhuYWJmZ7K5Q36Sp.6qzmsY4Z4umYDsQTmOrZbY6wNDnq8D6',
        '575-575-575 575', 'Nikita', 'Nikitin', 'Andreevich', '1993-03-03', 'MALE', true, 4014),
       (5568, 'Doctor', 'docсtor@gmail.com', '$2y$10$UEctNaXiiv.ShL3J77HiGePFECVd7PB5Vnv/qt1TQ.aGjeyrGcrYC',
        '576-576-576 576', 'German', 'Alexeev', 'Grigorievich', '1983-06-06', 'MALE', true, 6010);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date,
                                  full_employment_status_range, director_id, io_director_id)
VALUES (2244, '2244', 'Мед. учреждение №265', 'Tomsk',  '224-4', '1990-03-19', '2100-07-07', 224.4, 5568, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES
    (10001, 'Первое взрослое отделение', null, null, null, 2244);

INSERT INTO building (id, physical_address, medical_organization_id)
VALUES (111, 'Пушкина, д.78, кв.245', 2244), --здание, у которого есть кабинеты и они используются
       (112, 'Гоголя, д.25, кв.142', 2244), --здание, у которого есть кабинеты, но они не используются
       (113, 'Михалкова, д.48, кв.78', 2244); --здание, у которого нет кабинетов

INSERT INTO cabinet (id, number, name, building_id)
VALUES (11, 406, 'Травматолог', 111),
       (12, 305, 'Венеролог', 111),
       (13, 203, 'Гинеколог', 112),
       (14, 301, 'Стоматолог', 112);

INSERT INTO position (id, name, days_for_vocation, department_id, cabinet_id)
VALUES (1111, 'Глава травматологического департамента', 30, 10001, 11),
       (1112, 'Глава венерологического департамента', 23, 10001, 12);