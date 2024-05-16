INSERT INTO role (id, name)
VALUES (500, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (50, 'Employee', 'arnold@mail.com', '$2a$12$AuIlC5NrTKy7Xe/c1Yg/teO8.s5lF4R/64DOMnClGYRNSt0MKQNkK', '500-000-000 00', 'Arnold', 'Schwarzenegger', 'Gustav', '1947-07-30', 'MALE', '1', 500);

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date,full_employment_status_range, director_id, io_director_id)
VALUES (1, '700', 'med', 'address', '000', '2023-01-01', '2029-02-01', 223.75, null, null);

INSERT INTO yet (id, price, day_from, day_to,medical_organization_id)
VALUES (600, '100', '2023-01-01', '2023-02-01',1),
       (601, '200', '2023-01-01', '2023-03-02',1);




