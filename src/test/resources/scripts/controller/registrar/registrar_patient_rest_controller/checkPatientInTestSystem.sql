INSERT INTO role (id, name)
VALUES (200, 'REGISTRAR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                   role_id)
VALUES (30, 'Employee', 'registrar@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 01', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 200);
