INSERT INTO role (id, name)
VALUES
    (1, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Employee', 'economist@mail.com', '$2a$12$ijadL2SE3mcmAmbJocgLi.8qn3EuixmJLX4rRNhXRbEiAjZQCAqr2', '000-000-000 00', 'f_name', 'l_name', 'p_name', '1998-01-07', 'MALE', true, 1);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date, full_employment_status_range, director_id, io_director_id)
VALUES
    (1111, 'code1', 'name1', null, 'ogrn1', CURRENT_TIMESTAMP, null, 10, null, null);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES
    (1, 'name1', 'CHILD' , null, null, 1111),
    (2, 'name2', 'CHILD' , null, null, 1111),
    (3, 'name2', 'ADULT' , null, null, 1111),
    (4, 'name4', 'NO' , null, null, 1111);