package com.kasumov.med_ci.repository.structure;

import com.kasumov.med_ci.model.entity.structure.Wage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WageRepository extends JpaRepository<Wage, Long> {
    @Query(value = """
            SELECT w.id
            FROM Wage as w
            JOIN Position p ON p.id = w.position.id
            WHERE p.id = :position
            AND :startDate <= w.dateEnd
            AND :dateEnd >= w.dateStart
            """)
    List<Long> findWagesIdByPositionAndDate(Long position, LocalDate startDate, LocalDate dateEnd);
}