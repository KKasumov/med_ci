package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.Role;
import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;

public interface DoctorHistoryService {

    DoctorHistory saveDoctorHistory(Role role, Position position, Employee employee);
}
