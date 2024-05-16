package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.UserHistoryRepository;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.service.entity.user.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHistoryServiceImpl implements UserHistoryService {

    private final UserHistoryRepository userHistoryRepository;

    @Override
    public UserHistory save(UserHistory userHistory) {
        return userHistoryRepository.save(userHistory);
    }

    @Override
    public UserHistory saveForEmployee(Employee employee) {
        UserHistory userHistory = new UserHistory();
        userHistory.setUser(employee);
        return userHistoryRepository.save(userHistory);
    }
}
