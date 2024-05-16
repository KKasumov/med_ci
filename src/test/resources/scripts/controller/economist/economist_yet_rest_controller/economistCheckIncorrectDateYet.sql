INSERT INTO role (id, name)
VALUES (500, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (50, 'Employee', 'arnold@mail.com', '$2a$12$AuIlC5NrTKy7Xe/c1Yg/teO8.s5lF4R/64DOMnClGYRNSt0MKQNkK', '500-000-000 00', 'Arnold', 'Schwarzenegger', 'Gustav', '1947-07-30', 'MALE', '1', 500);

INSERT INTO medical_organization(id, code, name, legal_address, ogrn, start_date, end_date,full_employment_status_range, director_id, io_director_id)
VALUES (1, '700', 'med', 'address', '000', '2023-01-01', '2029-02-01', 223.75, null, null);

-- Создаем объекты типа Yet с пересекающимися датами
INSERT INTO yet (id, price, day_from, day_to,medical_organization_id)
VALUES (600, '100', '2022-01-01', '2022-01-05',1),
       (601, '200', '2022-01-04', '2022-03-01',1);

-- Создаем объекты типа Yet с разрывом между датами
INSERT INTO yet (id, price, day_from, day_to,medical_organization_id)
VALUES (602, '300', '2022-01-01', '2022-01-05',1),
       (603, '400', '2022-01-10', '2022-03-01',1);

-- Создаем объекты типа Yet с полностью пересекающимися датами
INSERT INTO yet (id, price, day_from, day_to,medical_organization_id)
VALUES (604, '500', '2022-01-01', '2022-03-01',1),
       (605, '500', '2022-02-01', '2022-04-01',1);

-- Создаем объекты типа Yet с частичным наложением дат
INSERT INTO yet (id, price, day_from, day_to,medical_organization_id)
VALUES (606, '700', '2022-01-01', '2022-03-01',1),
       (607, '800', '2022-02-15', '2022-04-01',1);

-- Создаем объекты типа Yet с разрывом между датами и отсутствием наложений
INSERT INTO yet (id, price, day_from, day_to,medical_organization_id)
VALUES (608, '900', '2022-01-01', '2022-01-05',1),
       (609, '1000', '2022-01-07', '2022-03-01',1);

-- Создаем объекты типа Yet с непересекающимися датами
INSERT INTO yet (id, price, day_from, day_to,medical_organization_id)
VALUES (610, '1100', '2022-01-01', '2022-01-05',1),
       (611, '1200', '2022-01-06', '2022-01-09',1),
       (612, '1300', '2022-01-10', '2022-03-01',1);

-- Создаем объекты типа Yet с нулевой датой окончания
INSERT INTO yet (id, price, day_from, day_to, medical_organization_id)
VALUES (613, '1400', '2022-01-01', null , 1),
       (614, '1500', '2022-03-01', null , 1);

-- Создаем объекты типа Yet с полностью пересекающимися датами и разными ценами
INSERT INTO yet (id, price, day_from, day_to, medical_organization_id)
VALUES (615, '1600', '2022-01-01', '2022-03-01', 1),
       (616, '1700', '2022-01-01', '2022-03-01', 1);

-- Создаем объекты типа Yet c одинаковыми датами начала и конца
INSERT INTO yet (id, price, day_from, day_to, medical_organization_id)
VALUES (617, '1800', '2022-01-01', '2022-01-01', 1),
       (618, '1900', '2022-02-01', '2022-02-01', 1);


-- Создаем объекты типа Yet с датой начала, большей даты окончания
INSERT INTO yet (id, price, day_from, day_to, medical_organization_id)
VALUES (619, '1700', '2022-03-01', '2022-01-01', 1),
       (620, '1800', '2022-04-01', '2022-02-01', 1);

-- Создаем объекты типа Yet с датами, которые равны друг другу
INSERT INTO yet (id, price, day_from, day_to, medical_organization_id)
VALUES (621, '1900', '2022-01-01', '2022-01-01', 1),
       (622, '2000', '2022-01-01', '2022-01-01', 1);

-- Создаем объекты типа Yet с датами, выходящими за рамки разумного
INSERT INTO yet (id, price, day_from, day_to, medical_organization_id)
VALUES (623, '2100', '1800-01-01', '2200-12-31', 1),
       (624, '2200', '2500-01-01', '3200-12-31', 1);
