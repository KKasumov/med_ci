package com.kasumov.med_ci.service.entity.economic.impl;

import com.kasumov.med_ci.model.entity.economic.Order;
import com.kasumov.med_ci.model.enums.InsuranceType;
import com.kasumov.med_ci.repository.economic.OrderRepository;
import com.kasumov.med_ci.service.entity.economic.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrdersByParameters(InsuranceType insuranceType,
                                                Boolean isFormed,
                                                Boolean isAcceptedForPayment,
                                                Boolean isPayed,
                                                Long smoId,
                                                LocalDate startDate,
                                                LocalDate endDate) {
        return orderRepository.getAllOrdersByParameters(insuranceType, isFormed, isAcceptedForPayment, isPayed, smoId,
                startDate, endDate);
    }


}



