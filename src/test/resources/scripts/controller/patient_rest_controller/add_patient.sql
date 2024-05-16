INSERT INTO public.role
(id, "name")
VALUES (1, 'PATIENT');

INSERT INTO public.users
(id, dtype, email, "password", snils, first_name, last_name, patronymic, birthday, gender, role_id, is_enabled)
VALUES(20, 'PATIENT', 'ivan@mail.com', '$2a$12$Qsizn.Wo0VjZM6kpaPyuYeULF0ppd1mND3389lQPChAbFMrGhpUdG', 'snils', 'first', 'last', 'patronimic', '2020-02-20', 'MALE', 1, true);

