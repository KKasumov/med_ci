INSERT INTO role (id, name)
VALUES (300, 'ECONOMIST');
INSERT INTO users (id, dtype, email, password, snils, first_name, last_name, patronymic,
                   birthday, gender, is_enabled, role_id)
VALUES (10, 'Employee', 'econom@yandex.ru', '$2a$12$9yMn07D/9xofijwJdmrCHeFHN4wMieItyUmGlGS2V4W3MYq16fl/K',
        '123-123-123 123', 'Galina', 'Vostrikova', 'Ivanovna', '1962-02-12', 'FEMALE', true, 300);