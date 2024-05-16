package com.kasumov.med_ci.model.entity.structure;


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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Лицензии на оказание определенного типа услуг
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "license")
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название лицензии.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * номер лицензии.
     */
    @Column(name = "number", nullable = false)
    private String number;

    /**
     * Дата начала действия лицензии.
     */
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    /**
     * Дата окончания действия лицензии.
     */
    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    /**
     * Организация которой выдана лицензия.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id", nullable = false)
    private MedicalOrganization medicalOrganization;

}
