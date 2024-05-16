INSERT INTO role (id, name)
VALUES
    (700, 'HR_MANAGER');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Employee', 'hr@mail.com', '$2y$10$.SBSrGpgbx0lXNGVh5XMMe6859EAKuompJXSSna/Mw//XL5wsNF36', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', true, 700);

INSERT INTO university (id, name)
VALUES
    (1, 'name1'),
    (2, 'name2'),
    (3, 'name3'),
    (4, 'name4');


--pass = 123