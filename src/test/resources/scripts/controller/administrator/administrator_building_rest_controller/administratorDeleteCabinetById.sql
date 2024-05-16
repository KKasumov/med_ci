insert into role (id, name)
values (900, 'ADMIN');

insert into users(id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled,
                  role_id)
values (500, 'Employee', 'Ivan@mail.com', '$2a$12$lJfTqChBPtNq.MaNLv6pNO6lkdPL1HL0JSSXUiBKTsDWYW5ChtylW',
        '000-000-000 00', 'Ivan', 'Ivnov', 'Ivanovich', '1987-08-15', 'MALE', true, 900);

insert into medical_organization(id, code, name, ogrn, start_date, full_employment_status_range)
values (70, '65656', 'Okulist', '65655fdf', '1990-01-01', 13.5);


insert into building(id, physical_address, medical_organization_id)
values (40, '555-fer', 70);

insert into cabinet(id, number, name, building_id)
values (5, 400, 'Pediatrician', 40),
       (6, 500, 'Dentist', 40),
       (7, 600, 'Surgery', 40);

INSERT INTO department (id, name, age_type, chief_doctor_id, io_chief_doctor_id, medical_organization_id)
VALUES (12, 'Первое детское отделение', 'CHILD', null, null, 70);

INSERT INTO position (id, name, days_for_vocation, department_id, cabinet_id)
VALUES (200, 'Head of Therapeutic Department', 21, 12, 5),
       (300, 'Head of the surgical department', 30, 12, 5);

