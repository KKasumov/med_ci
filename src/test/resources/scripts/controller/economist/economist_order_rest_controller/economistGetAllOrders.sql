INSERT INTO role (id, name)
VALUES (100, 'ECONOMIST');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (20, 'Employee', 'economist@gmail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG',
        '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1998-01-07', 'MALE', '1', 100);


INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date,
                                  full_employment_status_range, director_id, io_director_id)
VALUES (2222, 'code1', 'name1', null, 'ogrn1', '2023-01-31', '2024-01-31', 10, null, null);



INSERT INTO smo (id, name, region, start_date, end_date, code)
VALUES (101, 'smo1', 'MOSCOW', '2011-01-01', '2031-01-01', '0000'),
       (102, 'smo2', 'MOSCOW', '2011-02-01', '2031-02-01', '0001');


INSERT INTO orders (id, insurance_type, comment, date, money, is_formed, is_accepted_for_payment,
                    is_payed, medical_organization_id, smo_id )
VALUES (601,'OMS','aaa' ,'2023-01-31', 500, '1', '1', '1',2222, 101 ),
       (602,'OMS',null ,'2023-02-01', 700, '1', '0', '1',2222, 101 ),
       (603,'DMS',null ,'2023-02-01', 1000, '0', '0', '0',2222, 102 );



