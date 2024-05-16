INSERT INTO role (id, name)
VALUES
    (101, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (30, 'Employee', 'economist@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', true, 101);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES
    (2222, 'code1', 'name1', null, 'ogrn1', CURRENT_TIMESTAMP, null, 20, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES
    (1111, 'name1', 'ADULT', null, null, 2222),
    (2222, 'name2', 'CHILD', null, null, 2222),
    (3333, 'name3', 'NO', null, null, 2222);

INSERT INTO disease (id, identifier, name, is_disabled, age_type, department_id)
VALUES
    (400, 'identifier1', 'name1', false, 'ADULT', null),
    (401, 'identifier2', 'name2', false, 'ADULT', 1111),
    (402, 'identifier4', 'disease1', false, 'ADULT', null),
    (403, 'identifier6', 'disease2', false, 'ADULT', 1111),
    (404, 'name7', 'disease3', false, 'ADULT', 1111),
    (405, 'name8', 'disease4', false, 'CHILD', 2222);
