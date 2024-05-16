package com.kasumov.med_ci.repository.structure;

import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.enums.AgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("""
            SELECT d
            FROM Department d
                LEFT JOIN FETCH d.chiefDoctor
                LEFT JOIN FETCH d.ioChiefDoctor
            WHERE d.ageType IN :ageType
            """)
    List<Department> findDepartmentsWithChiefByAgeType(Set<AgeType> ageType);


    @Query("""
            SELECT d
            FROM Department d
            WHERE d.ageType IN :ageType AND d.id = :departmentId
            """)
    Department findDepartmentByIdAndAgeType(Long departmentId, Set<AgeType> ageType);

    Department findDepartmentById(Long departmentId);

    @Query("""
            SELECT d 
            FROM Department d
            WHERE d.ageType IN :ageType 
                AND d.name LIKE CONCAT('%', :pattern, '%')
            """)
    List<Department> getAllDepartmentsByParameters(Set<AgeType> ageType, String pattern);

    @Query(value = """
            SELECT d.chiefDoctor
            FROM Department d
            WHERE d.id = :departmentId
            """)
    Doctor getChiefDoctorByDepartmentId(long departmentId);

    @Query(value = """
            SELECT d.ioChiefDoctor
            FROM Department d
            WHERE d.id = :departmentId
            """)
    Doctor getIOChiefDoctorByDepartmentId(long departmentId);

    @Query(value = """
            SELECT d
            FROM Department d
            WHERE d.id = :departmentId
            """)
    Department getDepartmentById(long departmentId);

    @Query(value = """
            SELECT d
            FROM Department d
            WHERE d.id = :departmentId
            """)
    Department getInformationByDepartment(long departmentId);



}

