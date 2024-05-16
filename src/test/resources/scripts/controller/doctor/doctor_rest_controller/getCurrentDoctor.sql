INSERT INTO role (id, name)
VALUES (300, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic, birthday, gender, is_enabled, role_id)
VALUES (10, 'Doctor', 'arkady@mail.com', '$2a$12$2pXeyXObgaP1GXYZxPSHu./DGhIAlJCMB08DEPC/gJaRArCtwayTe', '000-000-000 00', 'Arkady', 'Parovozov', 'Chuh-Chuh', '1990-06-18', 'MALE', true, 300);

