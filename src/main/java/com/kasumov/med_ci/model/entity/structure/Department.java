package com.kasumov.med_ci.model.entity.structure;

import com.kasumov.med_ci.model.entity.medical.Disease;
import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
import com.kasumov.med_ci.model.enums.AgeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import java.util.Set;

/**
 * Отделение организации - структурное подразделение в котором работают сотрудники
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название отделения
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * возраст с которым работает отделение: взрослые или дети
     */
    @Column(name = "age_type")
    @Enumerated(EnumType.STRING)
    private AgeType ageType;

    /**
     * заведующий отделением.
     */
    @OneToOne
    @JoinColumn(name = "chief_doctor_id")
    private Doctor chiefDoctor;

    /**
     * исполняющий обязанности заведующего.
     */
    @OneToOne
    @JoinColumn(name = "io_chief_doctor_id")
    private Doctor ioChiefDoctor;

    /**
     * принадлежность к медицинской организации
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id", nullable = false)
    private MedicalOrganization medicalOrganization;



    /**
     * все сотрудники в отделении
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private Set<EmployeeHistory> employeeHistories;

    /**
     * должности определенные в отделении
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department", cascade = CascadeType.REMOVE)
    private Set<Position> positions;

    /**
     * заболевания, которые может лечить это отделение
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private Set<Disease> diseases;

    /**
     * медицинские услуги которые оказывает отделение
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department", cascade = CascadeType.REMOVE)
    private Set<MedicalService> medicalServices;

}
