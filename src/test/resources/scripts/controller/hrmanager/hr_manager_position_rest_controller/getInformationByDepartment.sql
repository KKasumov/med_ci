INSERT INTO role (id, name)
VALUES (7000, 'HR_MANAGER');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (3000, 'Employee', 'hr@mail.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K',
        '777-777-777 777', 'Ivan', 'Ivanov', 'Ivanovich', '1970-01-01', 'MALE', '1', 7000),
       (3500, 'Employee', 'hr@mail1.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K',
        '777-777-777 666', 'Ivan1', 'Ivanov1', 'Ivanovich1', '1970-01-01', 'MALE', '1', 7000),
       (4500, 'Employee', 'hr@mail2.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K',
        '777-777-777 555', 'Ivan2', 'Ivanov2', 'Ivanovich2', '1970-01-01', 'MALE', '1', 7000);

INSERT INTO medical_organization (id, code, name, ogrn, start_date, full_employment_status_range)
VALUES (400, '1123', 'Городская поликлиника', '202-12', '1986-01-25', 223.75);

INSERT INTO department (id, name, age_type, medical_organization_id)
VALUES (40, 'Первое детское отделение', 'CHILD', 400);

INSERT INTO building (id, physical_address, medical_organization_id)
VALUES (65, 'Центральная, д.22, кв.176', 400);

insert into cabinet(id, number,name,building_id)
values (40,566,'RRR',65),
       (55,777,'456-в',65),
       (65,888,'454-a',65);

INSERT INTO position (id, name, days_for_vocation, department_id,cabinet_id)
VALUES (4000, 'Терапевт', 42, 40,40),
       (5000, 'Стоматолог',42,40,55),
       (6000, 'Техник', 42, 40, 65);

INSERT INTO university (id, name)
VALUES (4000, 'Астраханский ГМУ');

INSERT INTO diploma (id, serial_number, end_date, university_id)
VALUES (4000, '11111', '2010-06-13', 4000),
       (5000, '22222', '2010-06-13', 4000),
       (6000, '33333', '2010-06-13', 4000);

INSERT INTO employee_history (id, dtype, is_public, employee_id, department_id)
VALUES (3500, 'DoctorHistory', true, 3000, 40),
       (4500, 'DoctorHistory', true, 3500, 40),
       (5500, 'DoctorHistory', true, 4500, 40);

INSERT INTO labor_contract (id, start_date, end_date, part, employee_history_id, position_id, diploma_id)
VALUES (300, '2017-07-13', null, 0.5, 3500, 4000, 4000),
       (400, '2017-07-13', null, 0.5, 4500, 5000, 5000),
       (500, '2017-07-13', null, 0.5, 5500, 6000, 6000);

insert into wage(id,date_start,date_end,value,position_id)
values (30,'2017-07-13','2022-07-13',335,4000),
       (31,'2017-07-13','2021-07-13',500,4000),
       (32,'2017-07-13','2023-07-13',335,4000),
       (33,'2017-07-13','2022-07-13',335,5000),
       (34,'2017-07-13','2021-07-13',500,5000),
       (35,'2017-07-13','2023-07-13',400,5000),
       (36,'2017-07-13','2022-07-13',335,6000),
       (37,'2017-07-13','2021-07-13',500,6000),
       (38,'2017-07-13','2023-07-13',600,6000);


