package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.Diploma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DiplomaRepository extends JpaRepository<Diploma, Long> {

    @Modifying
    @Query(value = "DELETE FROM Diploma d WHERE d.id = :diplomaId")
    void deleteDiplomaById(long diplomaId);

    boolean existsBySerialNumber(String serialNumber);
}
