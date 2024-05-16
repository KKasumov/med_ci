package com.kasumov.med_ci.repository.medical;

import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.enums.AgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TalonRepository extends JpaRepository<Talon, Long> {

    @Query(value = """
            SELECT t
            FROM Talon t
                LEFT JOIN FETCH t.patientHistory AS h
                LEFT JOIN FETCH h.patient
            WHERE t.doctorHistory.id = :doctorHistoryId
                AND CAST(t.time AS date) = CURRENT_DATE
            """)
    List<Talon> getTalonsByDoctorHistoryForToday(long doctorHistoryId);

    @Modifying
    @Query("""
            DELETE FROM Talon t
            WHERE t.doctorHistory IN
                (SELECT dh
                FROM DoctorHistory dh
                JOIN dh.employee e
                WHERE e.id = :doctorId)
            AND t.patientHistory IS NULL
            AND t.time >= :timeStart
            AND t.time <= :timeEnd
            """)
    void deleteFreeTalonsByDoctorInCertainTime(LocalDateTime timeStart, LocalDateTime timeEnd, long doctorId);


    @Query("""
            SELECT t
            FROM Talon t
                JOIN t.doctorHistory dh
            WHERE dh.employee.id = :doctorId
                AND t.time >= :timeStart
                AND t.time <= :timeEnd
            """)
    List<Talon> getTalonByDoctorCertainTime(LocalDateTime timeStart, LocalDateTime timeEnd, long doctorId);

    @Query("""
            SELECT t
            FROM Department d
                JOIN DoctorHistory dh ON dh.department.id = d.id
                JOIN Talon t ON t.doctorHistory.id = dh.id
                    JOIN FETCH t.doctorHistory AS docHistory
                    JOIN FETCH docHistory.employee
                    LEFT JOIN FETCH t.patientHistory AS patHistory
                    LEFT JOIN FETCH  patHistory.patient
            WHERE d.id = :departmentId
                AND t.time BETWEEN :start AND :end
            ORDER BY t.id
            """)
    List<Talon> findByDepartmentIdAndDateTime(long departmentId, LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT t
            FROM Talon t
                JOIN FETCH t.doctorHistory dh
                LEFT JOIN FETCH t.patientHistory AS patHistory
                LEFT JOIN FETCH patHistory.patient
            WHERE dh.employee.id = :doctorId
                AND t.time BETWEEN :start AND :end
            """)
    List<Talon> findByDoctorHistoryAndDateBetween(long doctorId, LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT t
            FROM Talon t
                JOIN t.doctorHistory dh ON dh.id = t.doctorHistory.id
                JOIN dh.employee e ON e.id = dh.employee.id
                LEFT JOIN FETCH t.patientHistory
            WHERE t.id = :talonId
                AND e.id = :doctorId
            """)
    Talon getTalonByIdAndDoctorIdWithDoctor(Long talonId, Long doctorId);

    @Query(value = """
            SELECT t
            FROM Talon t
                JOIN FETCH t.doctorHistory AS d
                JOIN FETCH t.patientHistory AS h
                JOIN FETCH d.employee
                JOIN FETCH h.patient
            WHERE h.patient.id = :patientId
            """)
    List<Talon> findAllByPatientHistory(long patientId);

    @Query("""
            SELECT t
            FROM Talon t
                JOIN FETCH t.doctorHistory AS dh
                JOIN FETCH dh.employee AS e
                LEFT JOIN FETCH t.patientHistory AS ph
                LEFT JOIN FETCH ph.patient AS p
            WHERE t.id = :talonId
                AND e.id = :doctorId
            """)
    Talon getTalonByIdAndDoctorId(Long talonId, Long doctorId);

    @Query(value = """
            SELECT t
            FROM Talon t
            WHERE t.doctorHistory.department.id = :departmentId
                AND t.time = :time
                AND t.patientHistory IS NULL
            """)
    List<Talon> findTalonByDepartmentAndTime(long departmentId, LocalDateTime time);

    @Query("""
            SELECT t
            FROM Talon t
                JOIN t.doctorHistory dh
                LEFT JOIN FETCH t.patientHistory ph
                    LEFT JOIN FETCH ph.patient AS patient
                        LEFT JOIN FETCH patient.userHistory
            WHERE dh.employee.id = :doctorId
                AND t.time >= :timeStart
                AND t.time <= :timeEnd
            """)
    List<Talon> getTalonByDoctorCertainTimeWithPatient(LocalDateTime timeStart, LocalDateTime timeEnd, long doctorId);

    @Query("""
            SELECT t
            FROM Department d
                JOIN DoctorHistory dh ON dh.department.id = d.id
                JOIN Talon t ON t.doctorHistory.id = dh.id
                    JOIN FETCH t.doctorHistory AS docHistory
                    JOIN FETCH docHistory.employee
                    LEFT JOIN FETCH t.patientHistory AS patHistory
                    LEFT JOIN FETCH  patHistory.patient
            WHERE d.id = :departmentId
                AND patHistory.id IS NULL
                AND t.time BETWEEN :start AND :end
            ORDER BY t.time
            """)
    List<Talon> findFreeTalonByDepartmentIdAndDateTime(long departmentId, LocalDateTime start, LocalDateTime end);

    @Query("""
             SELECT t
             FROM Talon t
             JOIN FETCH t.doctorHistory dh
             JOIN FETCH dh.department d
             JOIN FETCH dh.employee
            LEFT JOIN FETCH t.patientHistory ph
            LEFT JOIN FETCH ph.patient patient
             WHERE t.id = :talonId
                 """)
    Talon findTalonByIdWithPatientHistoryAndDepartmentAndDoctor(long talonId);

    @Modifying
    @Query("""
            delete
            FROM Talon t
            WHERE t.time < :time
            """)
    Integer deleteTalonsByTime(LocalDateTime time);

    @Modifying
    @Query("""
            update Talon t
            SET t.patientHistory = :patientHistoryId
            where t.id = :talonId
            """)
    void updateTalonPatientHistory(Long patientHistoryId, Long talonId);

    @Query("""
            SELECT t
            FROM Talon t
                JOIN DoctorHistory dh ON dh.id = t.doctorHistory.id
                JOIN Department d ON dh.department.id = d.id
            WHERE d.id = :departmentId
                AND dh.employee.id = :doctorId
                AND t.time BETWEEN :start AND :end
                AND d.ageType = :ageType
            ORDER BY t.time
            """)
    List<Talon> findTalonsByParameters(long departmentId, Long doctorId, LocalDateTime start, LocalDateTime end,
                                       AgeType ageType);
    @Query("""
            SELECT t
            FROM Talon t
                JOIN DoctorHistory dh ON dh.id = t.doctorHistory.id
                JOIN Department d ON dh.department.id = d.id
            WHERE d.id = :departmentId
                AND dh.employee.id = :doctorId
                AND t.time BETWEEN :start AND :end
                AND d.ageType = :ageType
                AND t.patientHistory.id is null
            ORDER BY t.time
            """)
    List<Talon> findFreeTalonsByParameters(long departmentId, Long doctorId, LocalDateTime start, LocalDateTime end,
                                       AgeType ageType);
    @Query("""
            SELECT t
            FROM Talon t
                JOIN DoctorHistory dh ON dh.id = t.doctorHistory.id
                JOIN Department d ON dh.department.id = d.id
            WHERE d.id = :departmentId
                AND dh.employee.id = :doctorId
                AND t.time BETWEEN :start AND :end
            ORDER BY t.time
            """)
    List<Talon> findTalonsByParametersNoAge(long departmentId, Long doctorId, LocalDateTime start, LocalDateTime end);
    @Query("""
            SELECT t
            FROM Talon t
                JOIN DoctorHistory dh ON dh.id = t.doctorHistory.id
                JOIN Department d ON dh.department.id = d.id
            WHERE d.id = :departmentId
                AND dh.employee.id = :doctorId
                AND t.time BETWEEN :start AND :end
                AND t.patientHistory.id is null
            ORDER BY t.time
            """)
    List<Talon> findFreeTalonsByParametersNoAge(long departmentId, Long doctorId, LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT t
            FROM Talon t
                JOIN PatientHistory ph ON ph.id = t.patientHistory.id
                JOIN Patient p ON p.id = ph.patient.id
            WHERE p.id = :patientId
                 """)
    List<Talon> findPatientTalonsAssigned(long patientId);

    @Query("""
    select t from Talon t
        join EmployeeHistory eh on eh.id = t.doctorHistory.id
        join Department d on d.id = eh.department.id
            where (:departmentId is null or d.id = :departmentId)
            and (t.time between :dateStart and :dateEnd)
            and (((:ageType is null and d.ageType in (:adult, :child)) or (d.ageType = :ageType)))
            and ((:isFree = true and (t.patientHistory is null))
                                or (:isFree = false and (t.patientHistory is null or t.patientHistory is not null)))
                    group by t, d.id order by d.id asc
    """)
    List<Talon> getTalonByDepartment(Long departmentId,
                                     LocalDateTime dateStart, LocalDateTime dateEnd,
                                     AgeType ageType, AgeType adult,
                                     AgeType child, boolean isFree);

    @Query("""
    select t from Talon t
        join EmployeeHistory eh on eh.id = t.doctorHistory.id
        join Department d on d.id = eh.department.id
        where (:doctorId is null or eh.employee.id = :doctorId)
        and (t.time between :dateStart and :dateEnd) 
        and ((:isFree = true and (t.patientHistory is null))
                                or (:isFree = false and (t.patientHistory is null 
                                    or t.patientHistory is not null)))
        group by t, d.id order by d.id asc
    """)
    List<Talon> getTalonByDoctor(Long doctorId, LocalDateTime dateStart,
                                 LocalDateTime dateEnd, boolean isFree);

    @Query("""
     select count(t.id) from Talon as t
        join EmployeeHistory eh on eh.id = t.doctorHistory.id
        join Department d on d.id = eh.department.id
            where (:departId is null or d.id = :departId)
                and (t.time between :dateStart and :dateEnd)
                and ((:ageType is null and d.ageType in (:ADULT, :CHILD) or (d.ageType = :ageType)))
                and (((:isFree = true and (t.patientHistory is null))
                                or (:isFree = false and (t.patientHistory is null or t.patientHistory is not null))))
        group by d.id order by d.id asc
    """)
    List<Long> getCountByDepart(Long departId, AgeType ADULT, AgeType CHILD,
                                AgeType ageType, boolean isFree, LocalDateTime dateStart,
                                LocalDateTime dateEnd);

    @Query("""
    select count(t.id) from Talon as t
        join EmployeeHistory eh on eh.id = t.doctorHistory.id
        join Department d on d.id = eh.department.id
            where (eh.employee.id = :doctorId) 
            and (t.time between :dateStart and :dateEnd) 
            and ((:isFree = true and (t.patientHistory is null)) or 
                            (:isFree = false and (t.patientHistory is null or t.patientHistory is not null))) 
                group by d.id order by d.id asc
    """)
    List<Long> getCountByDoctor(Long doctorId, LocalDateTime dateStart,
                                LocalDateTime dateEnd, boolean isFree);


    @Query("""
    select t from Talon as t 
        join fetch t.doctorHistory as doc
        join fetch doc.department as dep
            where t.id = :talonId
    """)
    Talon findByIdWithDocHistAndDep(Long talonId);
}