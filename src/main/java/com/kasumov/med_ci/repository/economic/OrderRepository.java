package com.kasumov.med_ci.repository.economic;

import com.kasumov.med_ci.model.entity.economic.Order;
import com.kasumov.med_ci.model.enums.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT o
        FROM Order o
            JOIN o.smo s
        WHERE (:insuranceType IS NULL OR o.insuranceType = :insuranceType)
          AND (:isFormed IS NULL OR o.isFormed = :isFormed)
          AND (:isAcceptedForPayment IS NULL OR o.isAcceptedForPayment = :isAcceptedForPayment)
          AND (:isPayed IS NULL OR o.isPayed = :isPayed)
          AND (:smoId IS NULL OR o.smo.id = :smoId)
          AND o.date BETWEEN :startDate AND :endDate
        """)
    List<Order> getAllOrdersByParameters(InsuranceType insuranceType,
                                         Boolean isFormed,
                                         Boolean isAcceptedForPayment,
                                         Boolean isPayed,
                                         Long smoId,
                                         LocalDate startDate,
                                         LocalDate endDate);
}