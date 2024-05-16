package com.kasumov.med_ci.repository.structure;

import com.kasumov.med_ci.model.entity.structure.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query("""
            SELECT b
            FROM Building b
            where b.id = :id
            """)
    Building getBuildingById(Long id);

    @Query("""
            SELECT DISTINCT b
            FROM Building AS b
                LEFT JOIN FETCH b.cabinets
            """)
    List<Building> getBuildingListWithCabinets();

    @Query("""
            SELECT b
            FROM Building b
            WHERE b.id = :buildingId
            AND (
            EXISTS (
            SELECT c.id
            FROM Cabinet c
            WHERE c.id IN (:cabinetsId)
            AND c.building = b
            )
            OR NOT EXISTS (
            SELECT c.id
            FROM Cabinet c
            WHERE c.id IN (:cabinetsId)
            ))
            """)
    Building getCabinetInBuildingById(long buildingId, List<Long> cabinetsId);


    @Query("""
            SELECT b
            FROM Building AS b
            WHERE b.physicalAddress = :physicalAddress
           """)
    Building getBuildingByPhysicalAddress(String physicalAddress);

    Building findBuildingById(long id);
}