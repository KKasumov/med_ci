package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;

import java.time.LocalDate;
import java.util.Optional;


public interface LaborContractService {

    LaborContract createLaborContractForEmployeeHiring(EmployeeForHiringDTO employeeForHiringDTO,
                                                       EmployeeHistory employeeHistory,
                                                       Diploma diploma, Position position);

    Optional<LaborContract> findById(long id);

    LaborContract findLaborContractById(long id);

    boolean existsLaborContractById(long id);

    String getRoleOfOwnerLaborContract(long laborId);

    Long findByPositionIdAndDate(long positionID, LocalDate startDate, LocalDate endDate);
}
