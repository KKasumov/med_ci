package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface LaborContractRepository extends JpaRepository<LaborContract, Long> {

    @Query("SELECT lb FROM LaborContract as lb " +
            "JOIN FETCH lb.employeeHistory eh " +
            "JOIN FETCH eh.employee " +
            "WHERE lb.id = :id")
    Optional<LaborContract> findById(@Param("id") long id);
    LaborContract findLaborContractById(long id);

    @Query("""
            SELECT r.name FROM Role r
            JOIN Employee emp ON r.id = emp.role.id
            JOIN EmployeeHistory emp_his ON emp.id = emp_his.employee.id
            JOIN LaborContract lc ON emp_his.id = lc.employeeHistory.id
            WHERE lc.id = :laborId
            """)
    String getRoleOfOwnerLaborContract(long laborId);

    @Query(value = """
            SELECT lc.id
            FROM LaborContract lc
            JOIN Wage w ON lc.position.id = w.position.id
            WHERE lc.endDate < :endDate
            AND :startDate < lc.startDate
            AND lc.position.id = :positionID
            """)
    Long findByPositionIdAndDate(long positionID, LocalDate startDate, LocalDate endDate);
}
