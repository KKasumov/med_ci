package com.kasumov.med_ci.model.entity.medical;


import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.enums.AgeType;
import lombok.Getter;
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
import javax.persistence.Table;
import java.util.Set;

/**
 * Disease Заболевание
 * Экономист создает заболеваний, которые лечит ЛПУ.
 * Заведующий отделением наполняет список заболеваний своего отделения из свободных заболеваний
 * У каждого заболевания есть уникальный идентификатор, например A04
 * есть какая-то взаимосвязь - при определенных заболеваниях можно использовать определенные услуги.
 * Но ее знают только врачи
 */

@Entity
@Getter
@Setter
@Table(name = "disease")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * идентификатор заболевания
     */
    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    /**
     * название заболевания
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * флаг - заблокировано ли заболевание от врачей
     */
    @Column(name = "is_disabled")
    private boolean isDisabled;

    /**
     * возраст с которым работает отделение: взрослые или дети
     */
    @Column(name = "age_type")
    @Enumerated(EnumType.STRING)
    private AgeType ageType;

    /**
     * отделение которое лечит это заболевание
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;



    /**
     * обращения по заболеванию
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "disease")
    private Set<Appeal> appeals;

}
