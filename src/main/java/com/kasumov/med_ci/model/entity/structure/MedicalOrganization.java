package com.kasumov.med_ci.model.entity.structure;


import com.kasumov.med_ci.model.entity.bookkeeping.account.OrganizationBankAccount;
import com.kasumov.med_ci.model.entity.economic.Yet;
import com.kasumov.med_ci.model.entity.economic.MunicipalOrder;
import com.kasumov.med_ci.model.entity.user.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * организация
 * Это юридическое лицо
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medical_organization")
public class MedicalOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * Название организации
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * юридический адрес
     */
    @Column(name = "legal_address")
    private String legalAddress;

    /**
     * ОГРН - основной государственный регистрационный номер
     */
    @Column(name = "ogrn", unique = true, nullable = false)
    private String ogrn;

    /**
     * дата начала деятельности юридического лица
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * дата окончания деятельности юридического лица
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * общее количество ставок которым располагает организация
     * Например, может быть максимум 223.75 ставок кратно 0.25
     */
    @Column(name = "full_employment_status_range", nullable = false)
    private BigDecimal fullEmploymentStatusRange;

    /**
     * главный врач
     */
    @OneToOne
    @JoinColumn(name = "director_id")
    private Doctor director;

    /**
     * исполняющий обязанности главного врача.
     */
    @OneToOne
    @JoinColumn(name = "io_director_id")
    private Doctor ioDirector;



    /**
     * отделения организации
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalOrganization")
    private Set<Department> departments;

    /**
     * счета в банках
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalOrganization")
    private Set<OrganizationBankAccount> organizationBankAccounts;

    /**
     * лицензии на осуществление медицинской деятельности.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalOrganization")
    private Set<License> licenses;

    /**
     * муниципальные заказы выделенные для организации
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalOrganization")
    private Set<MunicipalOrder> municipalOrders;

    /**
     * уеты организации
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalOrganization")
    private Set<Yet> yets;

}
