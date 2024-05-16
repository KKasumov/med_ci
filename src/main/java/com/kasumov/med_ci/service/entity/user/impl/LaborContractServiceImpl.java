package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.dto.user.laborContract.converter.LaborContractDtoConverter;
import com.kasumov.med_ci.repository.user.LaborContractRepository;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import com.kasumov.med_ci.service.entity.user.LaborContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaborContractServiceImpl implements LaborContractService {

    private final LaborContractRepository laborContractRepository;
    private final LaborContractDtoConverter laborContractDtoConverter;

    @Override
    public Optional<LaborContract> findById(long id) {
        return laborContractRepository.findById(id);
    }

    @Override
    public LaborContract findLaborContractById(long id) {
        return laborContractRepository.findLaborContractById(id);
    }

    @Override
    public boolean existsLaborContractById(long id) {
        return laborContractRepository.existsById(id);
    }

    @Override
    public String getRoleOfOwnerLaborContract(long laborId) {
        return laborContractRepository.getRoleOfOwnerLaborContract(laborId);
    }

    @Override
    public LaborContract createLaborContractForEmployeeHiring(EmployeeForHiringDTO dto,
                                                              EmployeeHistory employeeHistory,
                                                              Diploma diploma, Position position) {
        return laborContractRepository.save(
                laborContractDtoConverter.convertToEntity(dto, employeeHistory, diploma, position));
    }

    @Override
    public Long findByPositionIdAndDate(long positionID, LocalDate startDate, LocalDate endDate) {
        return laborContractRepository.findByPositionIdAndDate(positionID, startDate, endDate);
    }
}
