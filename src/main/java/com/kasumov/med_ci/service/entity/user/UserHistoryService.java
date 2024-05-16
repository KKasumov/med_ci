package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;

public interface UserHistoryService {

    UserHistory save(UserHistory userHistory);

    UserHistory saveForEmployee(Employee employee);
}
