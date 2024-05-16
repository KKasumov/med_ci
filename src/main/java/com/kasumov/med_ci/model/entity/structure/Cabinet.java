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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Кабинет
 * находится в здании и в нем может находиться оборудование которое стоит на балансе и работать сотрудники
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cabinet")
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * номер кабинета
     */
    @Column(name = "number", nullable = false)
    private int number;

    /**
     * название кабинета
     */
    @Column(name = "name")
    private String name;

    /**
     * здание в состав которого входит кабинет
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;



    /**
     * должности которые тут должны иметь рабочие места
     **/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cabinet")
    private Set<Position> positions;

}
