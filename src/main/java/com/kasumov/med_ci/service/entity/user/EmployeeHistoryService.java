package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;

public interface EmployeeHistoryService {

    EmployeeHistory saveEmployeeHistory(Position position, Employee employee);
}
