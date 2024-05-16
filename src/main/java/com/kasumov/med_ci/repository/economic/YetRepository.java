package com.kasumov.med_ci.repository.economic;

import com.kasumov.med_ci.model.entity.economic.Yet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YetRepository extends JpaRepository<Yet, Long> {

    Yet getYetById(long yetId);

    @Query(value = """
                   SELECT y
                   FROM Yet y
                   WHERE y.id = :yetId
                   """)
    Yet findYetById(long yetId);
}
