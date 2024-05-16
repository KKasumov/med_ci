INSERT INTO role (id, name)
VALUES
    (100, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Employee', 'economist@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', true, 100);

INSERT INTO medical_service (id, identifier, name, is_disabled, department_id)
VALUES
    (1, '01', 'service', FALSE, null);

INSERT INTO oms_price_of_medical_service (id, day_from, day_to, yet, medical_service_id)
VALUES
    (2, '2020-12-10', '2022-12-20', 100,1),
    (3, '2020-12-10', '2022-12-20', 100,1);


INSERT INTO pay_price_of_medical_service (id, day_from, day_to, money, medical_service_id)
VALUES
    (2, '2022-12-10', '2022-12-20', 100,1),
    (3, '2022-12-10', null, 100,1);

