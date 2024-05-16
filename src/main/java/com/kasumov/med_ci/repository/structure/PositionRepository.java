package com.kasumov.med_ci.repository.structure;

import com.kasumov.med_ci.model.entity.structure.Position;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("SELECT p FROM Position p "
            + "JOIN FETCH p.department"
            + " WHERE p.id = :id")
    Optional<Position> findById(@NotNull @Param("id") Long id);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0
            THEN FALSE ELSE TRUE END
            FROM Position p
            WHERE p.cabinet.id
            IN :cabinetsId
            """)
    boolean isExistPositionByCabinet(List<Long> cabinetsId);

    @Query(value = """
            SELECT p
            FROM Position p
            join p.department dep
            where dep.id = :departmentId
            """)
    List<Position> getPositionByDepartmentId(long departmentId);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0
            THEN FALSE ELSE TRUE END
            FROM Position p
            JOIN Cabinet c
            ON p.cabinet.id = c.id
            WHERE c.building.id = :buildingId
            """)
    boolean isExistPositionByBuilding(long buildingId);

    Optional<Position> findPositionById(Long id);
}
