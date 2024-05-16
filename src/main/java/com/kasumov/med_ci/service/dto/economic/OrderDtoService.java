package com.kasumov.med_ci.service.dto.economic;

import com.kasumov.med_ci.model.dto.economic.order.OrderDto;
import com.kasumov.med_ci.model.enums.InsuranceType;

import java.time.LocalDate;
import java.util.List;

public interface OrderDtoService {

    List<OrderDto> getAllOrdersByParameters(InsuranceType insuranceType,
                                            Boolean isFormed,
                                            Boolean isAcceptedForPayment,
                                            Boolean isPayed,
                                            Long smoId,
                                            LocalDate startDate,
                                            LocalDate endDate);
}
