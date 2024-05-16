package com.kasumov.med_ci.model.entity.user;

import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Сотрудник медучреждения. В том числе врач
 */

@Entity
@Getter
@Setter
public class Employee extends User {

    /**
     * история сотрудника
     */
    @OneToOne(mappedBy = "employee")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private EmployeeHistory employeeHistory;
}
