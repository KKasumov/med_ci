package com.kasumov.med_ci.model.dto.economic.order.converter;

import com.kasumov.med_ci.model.dto.economic.smo.converter.SmoDtoConverter;
import com.kasumov.med_ci.model.entity.economic.Order;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.economic.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDtoConverter {

    private final DateConvertor dateConvertor;
private final SmoDtoConverter smoDtoConverter;
    public OrderDto convertOrderToOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .insuranceType(order.getInsuranceType())
                .date(dateConvertor.localDateToString(order.getDate()))
                .money(order.getMoney())
                .isFormed(order.isFormed())
                .isAcceptedForPayment(order.isAcceptedForPayment())
                .isPayed(order.isPayed())
                .smo(smoDtoConverter.smoToSmoDto(order.getSmo()))
                .build();
    }

}