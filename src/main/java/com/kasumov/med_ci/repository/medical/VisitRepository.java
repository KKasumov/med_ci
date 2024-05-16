package com.kasumov.med_ci.repository.medical;

import com.kasumov.med_ci.model.dto.medical.visit.VisitMedicalServiceNativeDto;
import com.kasumov.med_ci.model.entity.medical.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    boolean existsByMedicalServicesId(long medicalServiceId);

    @Query(nativeQuery = true, value = """
                SELECT v.id AS visitId,
                       v.day_of_visit AS dayOfVisit,
                       dt.id AS docId,
                       dt.first_name AS docFirstName,
                       dt.last_name AS docLastName,
                       dt.patronymic AS docPatronymic,
                       array_to_string(array_agg(CONCAT(CAST(m.id AS text), ',', m.identifier, ',', m.name)), '|') AS medicalServiceDtoNative,
                       SUM(COALESCE(y.price * opms.yet, 0)) AS visitTotalPrice
                FROM visit v
                    JOIN visit_medical_services vms ON v.id = vms.visit_id
                    JOIN employee_history eh ON v.doctor_history_id = eh.id
                    JOIN users dt ON eh.employee_id = dt.id
                    JOIN medical_service m ON vms.medical_service_id = m.id
                    JOIN oms_price_of_medical_service opms ON opms.medical_service_id = m.id
                    JOIN medical_service ms ON opms.medical_service_id = ms.id
                    JOIN department dep ON ms.department_id = dep.id
                    JOIN medical_organization mo ON dep.medical_organization_id = mo.id
                    LEFT JOIN yet y ON mo.id = y.medical_organization_id AND y.day_from <= v.day_of_visit AND (y.day_to IS NULL OR y.day_to >= v.day_of_visit)
                WHERE v.id = :visitId
                    AND opms.day_from <= v.day_of_visit
                    AND (opms.day_to IS NULL OR opms.day_to >= v.day_of_visit)
                GROUP BY v.id, v.day_of_visit, dt.id, dt.first_name, dt.last_name, dt.patronymic
            """)
    VisitMedicalServiceNativeDto findWithMedicalServicesForOms(long visitId);

    @Query(nativeQuery = true, value = """
                SELECT v.id AS visitId,
                       v.day_of_visit AS dayOfVisit,
                       dt.id AS docId,
                       dt.first_name AS docFirstName,
                       dt.last_name AS docLastName,
                       dt.patronymic AS docPatronymic,
                       array_to_string(array_agg(CONCAT(CAST(m.id AS text), ',', m.identifier, ',', m.name)), '|') AS medicalServiceDtoNative,
                       SUM(COALESCE(ppms.money, 0)) AS visitTotalPrice
                  FROM visit v
                  JOIN visit_medical_services vms ON v.id = vms.visit_id
                  JOIN employee_history eh ON v.doctor_history_id = eh.id
                  JOIN users dt ON eh.employee_id = dt.id
                  JOIN medical_service m ON vms.medical_service_id = m.id
                  JOIN pay_price_of_medical_service ppms ON ppms.medical_service_id = m.id
                 WHERE v.id = :visitId
                   AND ppms.day_from <= v.day_of_visit
                   AND (ppms.day_to IS NULL OR ppms.day_to >= v.day_of_visit)
                 GROUP BY v.id, v.day_of_visit, dt.id, dt.first_name, dt.last_name, dt.patronymic
            """)
    VisitMedicalServiceNativeDto findWithMedicalServicesForDms(long visitId);
}
