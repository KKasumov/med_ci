package com.kasumov.med_ci.model.entity.bookkeeping.account;

import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EmployeeBankAccount extends BankAccount {

    /**
     * счет сотрудника медицинской организации
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_history_id")
    private EmployeeHistory employeeHistory;
}
