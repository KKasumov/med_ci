INSERT INTO role (id, name)
VALUES (3000, 'HR_MANAGER'),
       (5000, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (5555, 'Employee', 'hr@gmail.com', '$2y$10$WRcZVLx7GoTzUY7hxc49vOaYthFIjjSd2KX10Xf7ACQ9LAsWXVuJW',
        '555-555-555 555', 'Daniil', 'Litvishko', 'Andreevich', '2000-01-01', 'MALE', true, 3000),
    (5556, 'Doctor', 'doc@gmail.com', '$2y$10$VKw2aPGw7O7tvdkUdbUJMuTLKashNSMq8//V6aPF8N1eVdzRbPDm.',
     '556-556-556 556', 'Misha', 'Ogorodnikov', 'Sergeevich', '1985-02-02', 'MALE', true, 5000);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date,
                                  full_employment_status_range, director_id, io_director_id)
VALUES (2242, '2242', 'Городская поликлиника №127', 'Moscow', '224-2', '1990-05-20', '2100-05-05', 224.2, 5556, null);

INSERT INTO building (id, physical_address, medical_organization_id)
VALUES (1, 'Центральная, д.22, кв.176', 2242),
       (2, 'Гагарина, д.18, кв.122', 2242),
       (3, 'Щурова, д.1, кв.8', 2242);

INSERT INTO cabinet (id, number, name, building_id)
VALUES (1, 204, 'Рентген', 1),
       (2, 402, 'Терапевт', 1),
       (3, 103, 'Невролог', 2);