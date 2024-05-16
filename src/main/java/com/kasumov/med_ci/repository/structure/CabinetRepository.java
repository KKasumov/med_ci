package com.kasumov.med_ci.repository.structure;

import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CabinetRepository extends JpaRepository<Cabinet, Long> {

    @Query("""
            SELECT c
            FROM Cabinet c
            JOIN c.building b
            WHERE b = :building
            AND c.id IN :cabinetsId
            """)
    List<Cabinet> findByIdInBuilding(Building building, List<Long> cabinetsId);

    @Query("""
            SELECT c
            FROM Cabinet AS c
            """)
    List<Cabinet> getAllCabinets();

    Cabinet findCabinetById(Long cabinetId);

    @Query("""
            SELECT c
            FROM Cabinet c
            JOIN FETCH c.building
            WHERE c.id = :cabinetId
            """)
    Cabinet getCabinetWithBuildingById(Long cabinetId);

    Optional<Cabinet> findById(Long id);
}
