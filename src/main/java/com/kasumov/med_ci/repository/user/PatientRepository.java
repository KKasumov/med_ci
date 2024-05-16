package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByEmail(String email);

    @Query(value = """
            SELECT *
                    FROM users u
                             LEFT JOIN patient_history h ON u.id = h.patient_id
                             LEFT JOIN polis p on h.id = p.patient_history_id
                    WHERE 1=1
                      AND u.dtype = 'Patient'
                      AND (u.first_name = :firstName or :firstName is null)
                      AND (u.last_name = :lastName or :lastName is null)
                      AND (u.gender = :gender or :gender is null)
                      AND (u.snils = :snils or :snils is null)
                      AND (p.number = :polisNumber or :polisNumber is null)        
                      LIMIT 20
            """, nativeQuery = true)
    List<Patient> findPatientByParameters(@Param("firstName") String firstName,
                                          @Param("lastName") String lastName,
                                          @Param("gender") String gender,
                                          @Param("snils") String snils,
                                          @Param("polisNumber") String polisNumber);

    @Query(value = """
            SELECT patient.email,
                   CASE WHEN
                   c.value IS NULL THEN '0'
                   ELSE c.value END chatId,
                   CONCAT_WS(' ', 'Напоминаем, что Вы записаны на завтра к доктору', doc.last_name, doc.first_name, doc.patronymic,
                            'в отделение', d.name, 'на время', t.time) message
            FROM talon t
                   LEFT JOIN patient_history ph ON t.patient_history_id = ph.id
                   LEFT JOIN users patient ON ph.patient_id = patient.id
                   LEFT JOIN user_history uh ON patient.id = uh.user_id
                   LEFT JOIN employee_history eh ON t.doctor_history_id = eh.id
                   LEFT JOIN users doc ON eh.employee_id = doc.id
                   LEFT JOIN contact c ON c.user_history_id = uh.id
                   LEFT JOIN department d on eh.department_id = d.id
            WHERE 1 = 1
                   AND CAST(t.time AS date) = CURRENT_DATE + INTERVAL '1 DAY'
            """, nativeQuery = true)
    List<String> getRemindersForPatients();
}
