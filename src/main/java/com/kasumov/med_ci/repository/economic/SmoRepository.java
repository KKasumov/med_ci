package com.kasumov.med_ci.repository.economic;

import com.kasumov.med_ci.model.entity.economic.Smo;
import com.kasumov.med_ci.model.enums.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmoRepository extends JpaRepository<Smo, Long> {

    Smo getSmoById(long smoId);

    @Query("""
            SELECT s 
            FROM Smo s
            WHERE (:smoRegion IS NULL OR s.region = :smoRegion) 
                AND s.name LIKE CONCAT('%', :concatPattern, '%')
            """)
    List<Smo> getSmoListByParameters(Region smoRegion, String concatPattern);
}