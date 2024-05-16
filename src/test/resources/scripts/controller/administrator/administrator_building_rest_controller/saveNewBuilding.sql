INSERT INTO role (id, name)
VALUES (3035, 'ADMIN'),
       (5010, 'DOCTOR');

INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (5565, 'Employee', 'admin@gmail.com', '$2y$10$KwNNqdhuYWJmZ7K5Q36Sp.6qzmsY4Z4umYDsQTmOrZbY6wNDnq8D6',
        '565-565-565 565', 'Vlad', 'Nikolaev', 'Sergeevich', '1995-02-02', 'MALE', true, 3035),
       (5566, 'Doctor', 'docс@gmail.com', '$2y$10$UEctNaXiiv.ShL3J77HiGePFECVd7PB5Vnv/qt1TQ.aGjeyrGcrYC',
        '556-556-556 556', 'Nikita', 'Vasiliev', 'Fedorovich', '1990-01-06', 'MALE', true, 5010);

INSERT INTO medical_organization (id, code, name, legal_address, ogrn, start_date, end_date,
                                  full_employment_status_range, director_id, io_director_id)
VALUES (2243, '2243', 'Городская больница №97', 'Omsk',  '224-3', '1985-04-21', '2100-06-06', 224.3, 5566, null);
