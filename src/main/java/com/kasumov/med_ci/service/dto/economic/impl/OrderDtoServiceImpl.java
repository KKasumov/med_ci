package com.kasumov.med_ci.service.dto.economic.impl;

import com.kasumov.med_ci.service.entity.economic.OrderService;
import com.kasumov.med_ci.model.dto.economic.order.OrderDto;
import com.kasumov.med_ci.model.dto.economic.order.converter.OrderDtoConverter;
import com.kasumov.med_ci.model.enums.InsuranceType;
import com.kasumov.med_ci.service.dto.economic.OrderDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDtoServiceImpl implements OrderDtoService {
    private final OrderDtoConverter orderDtoConverter;
    private final OrderService orderService;

    @Override
    public List<OrderDto> getAllOrdersByParameters(InsuranceType insuranceType,
                                                   Boolean isFormed,
                                                   Boolean isAcceptedForPayment,
                                                   Boolean isPayed,
                                                   Long smoId,
                                                   LocalDate startDate,
                                                   LocalDate endDate) {
        return orderService.getAllOrdersByParameters(insuranceType, isFormed, isAcceptedForPayment, isPayed, smoId,
                        startDate, endDate).stream()
                .map(orderDtoConverter::convertOrderToOrderDto)
                .toList();
    }

}
