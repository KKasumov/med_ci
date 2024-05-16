INSERT INTO role (id, name)
VALUES
    (100, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Employee', 'economist@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', true, 100);

INSERT INTO medical_service (id, identifier, name, is_disabled, department_id)
VALUES
    (1, '01', 'service', FALSE, null);
