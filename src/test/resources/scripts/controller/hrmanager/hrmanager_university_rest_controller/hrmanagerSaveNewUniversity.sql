INSERT INTO role (id, name)
VALUES
    (700, 'HR_MANAGER');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Employee', 'hr@mail.com', '$2y$10$.SBSrGpgbx0lXNGVh5XMMe6859EAKuompJXSSna/Mw//XL5wsNF36', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', true, 700);

--pass = 123
