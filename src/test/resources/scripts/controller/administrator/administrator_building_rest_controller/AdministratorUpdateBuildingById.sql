INSERT INTO role (id, name)
VALUES (1, 'ADMIN');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (7000, 'Employee', 'admin@mail.com', '$2a$12$ijadL2SE3mcmAmbJocgLi.8qn3EuixmJLX4rRNhXRbEiAjZQCAqr2',
        '000-000-000 00', 'Admin', 'Adminov', 'Adminovich', '1988-03-23', 'MALE', true, 1);

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date,
                                 full_employment_status_range, director_id, io_director_id)
VALUES (3000, '300', 'med1', 'address1', '001', '2010-09-09', '2025-09-09', 223.75, null, null),
       (4000, '400', 'med2', 'address2', '002', '2011-09-09', '2026-09-09', 220.75, null, null);

INSERT INTO building(id, physical_address, medical_organization_id)
VALUES (2000, 'building address', 3000),
       (2001, 'building address1', 4000);

INSERT INTO cabinet(id, number, name, building_id)
VALUES (5000, 100, 'ЛОР', 2000),
       (5001, 101, 'Хирург', 2000),
       (5002, 102, 'Терапевт', 2000),
       (5003, 103, 'Кардиолог', 2000),
       (6000, 100, 'ЛОР1', 2001);


