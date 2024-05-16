package com.kasumov.med_ci.model.entity.structure;


import com.kasumov.med_ci.model.entity.user.items.Equipment;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Должность
 * в каждом отделении свои должности
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * название должности
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * отпускные дни положенные сотруднику за 1 календарный год
     * у врачей 42, у остальных 28
     */
    @Column(name = "days_for_vocation", nullable = false)
    private int daysForVocation;

    /**
     * принадлежность к конкретному отделению
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    /**
     * Кабинет в котором находится сотрудник занимаемый должность.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cabinet_id")
    private Cabinet cabinet;

    /**
     * действующие оклады
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position")
    private Set<Wage> wages;

    /**
     * договоры связанные с этой должностью
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position")
    private Set<LaborContract> laborContracts;

    /**
     * вверенное оборудование
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position")
    private Set<Equipment> equipments;

}
