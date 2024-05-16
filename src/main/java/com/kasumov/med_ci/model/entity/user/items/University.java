package com.kasumov.med_ci.model.entity.user.items;


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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * образовательное учреждение.
 * Сотрудники где-то учились и проходят аттестации.
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "university")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название организации
     */
    @Column(name = "name", nullable = false)
    private String name;



    /**
     * дипломы
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "university")
    private Set<Diploma> diplomas;

    /**
     * аттестации
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "university")
    private Set<Attestation> attestations;
}
