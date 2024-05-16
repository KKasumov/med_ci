insert into role (id, name)
values (900,'ADMIN');

insert into users(id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
values (500,'Employee','Dmintriy@mail.com','$2a$12$UhZsPhNb4ACuguWv3VXA3evc2SkMOrCewFU8/NTkVJ.W9pYVZOlDy','000-000-000 00', 'Dmitriy', 'Samoylenko', 'Ivanovich', '1992-09-05', 'MALE', true, 900);

insert into medical_organization(id,code,name,ogrn,start_date,full_employment_status_range)
values (70,'65656','Okulist','65655fdf','1990-01-01',13.5);

insert into building(id,physical_address,medical_organization_id)
values (40,'555-fer',70),
       (50,'666-der',70);

insert into cabinet(id,number,name,building_id)
values (5,400,'Bers',40);


