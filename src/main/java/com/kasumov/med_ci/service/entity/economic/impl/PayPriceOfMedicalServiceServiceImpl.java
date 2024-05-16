package com.kasumov.med_ci.service.entity.economic.impl;

import com.kasumov.med_ci.repository.economic.PayPriceOfMedicalServiceRepository;
import com.kasumov.med_ci.model.entity.economic.price.PayPriceOfMedicalService;
import com.kasumov.med_ci.service.entity.economic.PayPriceOfMedicalServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayPriceOfMedicalServiceServiceImpl implements PayPriceOfMedicalServiceService {
    private final PayPriceOfMedicalServiceRepository payPriceOfMedicalServiceRepository;
    @Override
    public PayPriceOfMedicalService save(PayPriceOfMedicalService payPriceOfMedicalService) {
        return payPriceOfMedicalServiceRepository.save(payPriceOfMedicalService);
    }
}
