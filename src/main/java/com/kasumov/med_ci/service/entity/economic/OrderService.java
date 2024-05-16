package com.kasumov.med_ci.service.entity.economic;

import com.kasumov.med_ci.model.entity.economic.Order;
import com.kasumov.med_ci.model.enums.InsuranceType;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrdersByParameters(InsuranceType insuranceType,
                                         Boolean isFormed,
                                         Boolean isAcceptedForPayment,
                                         Boolean isPayed,
                                         Long smoId,
                                         LocalDate startDate,
                                         LocalDate endDate);
}
