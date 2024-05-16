TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE patient_history CASCADE;
TRUNCATE TABLE medical_organization CASCADE;
TRUNCATE TABLE department CASCADE;
TRUNCATE TABLE medical_service CASCADE;
TRUNCATE TABLE employee_history CASCADE;
TRUNCATE TABLE disease CASCADE;
TRUNCATE TABLE appeal CASCADE;
TRUNCATE TABLE visit CASCADE;
TRUNCATE TABLE visit_medical_services CASCADE;


-- Удаляем все связанные записи из таблицы visit_medical_services
DELETE FROM visit_medical_services
WHERE medical_service_id = 42;

-- Удаляем строку из таблицы medical_service
DELETE FROM medical_service
WHERE id = 42;






