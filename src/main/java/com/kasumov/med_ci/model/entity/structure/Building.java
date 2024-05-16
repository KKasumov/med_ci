package com.kasumov.med_ci.model.entity.structure;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Строение
 * Юридическое лицо может иметь на балансе здания.
 * Здание должно иметь физический адрес но кабинетов в нем может не быть - например это хоз. постройка или гараж.
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * физический адрес здания
     */
    @Column(name = "physical_address", nullable = false)
    private String physicalAddress;

    /**
     * организация которой принадлежит здание
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id", nullable = false)
    private MedicalOrganization medicalOrganization;


    /**
     * в здании могут быть кабинеты, а могут и отсутствовать.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building", cascade = CascadeType.REMOVE)
    private Set<Cabinet> cabinets;

}
