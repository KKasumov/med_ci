package com.kasumov.med_ci.model.entity.user.history;

import com.kasumov.med_ci.model.entity.bookkeeping.account.EmployeeBankAccount;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import com.kasumov.med_ci.model.entity.user.items.Salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * флаг который разрешает отображать талоны сотрудника, если он доктор, для записи через интернет
     */
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    /**
     * связь с сотрудником
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /**
     * трудовой договор
     */
    @OneToOne(mappedBy = "employeeHistory")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private LaborContract laborContract;

    /**
     * отделение в котором трудится сотрудник
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;



    /**
     * счета в банке для получения ЗП
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeHistory")
    private Set<EmployeeBankAccount> bankAccount;

    /**
     * выплаты сотруднику
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeHistory")
    private Set<Salary> salaries;

    public EmployeeHistory(Long id,
                           boolean isPublic,
                           Employee employee,
                           LaborContract laborContract,
                           Department department) {
        this.id = id;
        this.isPublic = isPublic;
        this.employee = employee;
        this.laborContract = laborContract;
        this.department = department;
    }
}
