package com.kasumov.med_ci.model.entity.user.items;

import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *  выплата сотруднику за все его обязанности в определенный период времени.
 *  чаще всего это календарный месяц
 */

@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * начало периода оплаты труда
     */
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    /**
     * окончание периода оплаты труда
     */
    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    /**
     * оклад по договору за отработанные дни
     * todo придумать формулу расчета
     */
    @Column(name = "labor_contract_pay")
    private BigDecimal laborContactPay;

    /**
     * платежи за отпуск и(или) больничный.
     * todo придумать формулу расчета
     */
    @Column(name = "vacation_pay")
    private BigDecimal vacationPay;

    /**
     * премиальная выплата.
     * выдается на усмотрение руководителя
     */
    @Column(name = "bonus_pay")
    private BigDecimal bonusPay;

    /**
     * сотрудник
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_history_id", nullable = false)
    private EmployeeHistory employeeHistory;
}