INSERT INTO role (id, name)
VALUES
    (100, 'PATIENT'),
    (200, 'REGISTRAR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES
    (10, 'Patient', 'ivan2023@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', false, 100),
    (20, 'Patient', 'ivan@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (30, 'Patient', 'elena@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '123-456-789 12', 'Elena', 'Ivanova', 'Ivanovna', '1995-07-13', 'FEMALE', true, 100),
    (40, 'Employee', 'registrar@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 01', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 200),
    (50, 'Patient', 'ivan2@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (51, 'Patient', 'ivan3@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (52, 'Patient', 'ivan4@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (53, 'Patient', 'ivan5@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (54, 'Patient', 'ivan6@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (55, 'Patient', 'ivan7@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (56, 'Patient', 'ivan8@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (57, 'Patient', 'ivan9@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (58, 'Patient', 'ivan10@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (59, 'Patient', 'ivan11@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (60, 'Patient', 'ivan12@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (61, 'Patient', 'ivan13@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (62, 'Patient', 'ivan14@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (63, 'Patient', 'ivan15@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (64, 'Patient', 'ivan16@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (65, 'Patient', 'ivan17@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (66, 'Patient', 'ivan18@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (67, 'Patient', 'ivan19@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (68, 'Patient', 'ivan20@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (69, 'Patient', 'ivan21@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (70, 'Patient', 'ivan22@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (71, 'Patient', 'ivan23@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (72, 'Patient', 'ivan24@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (73, 'Patient', 'ivan25@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100),
    (74, 'Patient', 'ivan26@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', '000-000-000 00', 'Ivan', 'Ivanov', 'Ivanovich', '1990-06-25', 'MALE', true, 100);


INSERT INTO smo (id, name, region, start_date, end_date, code)
VALUES
    (601, 'smo1','MOSCOW', '2011-01-01', '2031-01-01', '0000'),
    (602, 'smo2','NIZHNY_NOVGOROD_REGION', '2011-01-01', '2031-01-01', '0001');

INSERT INTO patient_history (id, patient_id)
VALUES (200, 10),
       (201, 20),
       (202, 40);

INSERT INTO polis (id, insurance_type, serial, number, date_start, date_end, is_deprecated, smo_id, patient_history_id)
VALUES (500, 'OMS', '7111', '887188', '2012-01-01', '2029-01-01', false, 601, 200),
       (501, 'OMS', '6254', '123456', '2012-01-01', '2029-01-01', false, 601, 201),
       (502, 'DMS', '3245', '567812', '2013-01-01', '2028-01-01', false, 602, 201),
       (503, 'OMS', '9845', '326712', '2012-01-01', '2029-01-01', false, 602, 202);