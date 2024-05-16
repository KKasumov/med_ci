package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.enums.AgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
            SELECT d
            FROM Doctor d
                JOIN d.employeeHistory eh
                JOIN FETCH d.doctorHistory dh
                JOIN dh.talons t
                JOIN dh.department dep
            WHERE t.patientHistory IS NULL
                AND t.time <= :scope
                AND dep.ageType = :ageType
            """)
    List<Doctor> getFreeDoctorsByAgeTypeAndScope(LocalDateTime scope, AgeType ageType);

    @Query("""
            SELECT d
            FROM Doctor d
                JOIN FETCH d.doctorHistory dh
                JOIN FETCH dh.department dep
            WHERE dep.ageType IN :ageType
            """)
    List<Doctor> getDoctorsWithDepartmentsByAgeType(Set<AgeType> ageType);


    @Query("""
            SELECT CASE WHEN COUNT(d) > 0
            THEN false ELSE true END
            FROM Doctor d
                JOIN d.employeeHistory eh
                JOIN eh.laborContract lc
            WHERE d.id = :docId
                AND (lc.endDate IS NULL
                OR lc.endDate > current_date)
            """)
    boolean isExistDoctorByLaborContractValid(long docId);

    @Query
            ("""
                    SELECT d
                    FROM Doctor d
                    JOIN FETCH d.role
                    WHERE d.id = :doctorId
                    """)
    Doctor getDoctorWithRole(long doctorId);

    @Query("""
            SELECT d
            FROM Doctor AS d
                LEFT JOIN FETCH d.doctorHistory AS doc_history
                LEFT JOIN FETCH doc_history.talons
                LEFT JOIN FETCH doc_history.employee AS emp
                    LEFT JOIN FETCH emp.userHistory AS emp_his
            WHERE d.id = :doctorId
            """)
    Doctor findDoctorByIdWithTalons(long doctorId);

    @Query("""
            SELECT distinct d from Doctor d
                join d.doctorHistory dh
                join dh.laborContract lc
                join dh.department dp
                join lc.attestations att
            where dp.id = :departmentId
                and att.dateTo <= :day
                and lc.endDate >= :now
                
            """)

    List<Doctor> getDoctorAllByEndAttestation(LocalDate now,LocalDate day,long departmentId);

    @Query("""
            SELECT distinct d from Doctor d
                join d.doctorHistory dh
                join dh.laborContract lc
                join dh.department dp
                join lc.attestations att
            where att.dateTo <= :day
                and lc.endDate >= :now
                
            """)

    List<Doctor> getDoctorAllByEndAttestationNoDepartmentId(LocalDate now,LocalDate day);
}
