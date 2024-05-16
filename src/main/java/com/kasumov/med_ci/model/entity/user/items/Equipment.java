package com.kasumov.med_ci.model.entity.user.items;


import com.kasumov.med_ci.model.entity.structure.Position;
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
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Оборудование.
 * Под оборудованием подразумевается материальная ценность которая стоит на балансе в учреждении.
 * Она должна числиться на учреждении и у нее должен быть ответсвенный сотрудник, но могут быть моменты когда его нет.
 * Примером будет стоматологическое кресло стоматолога, машина водителя, компьютер экономиста.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * название оборудования
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * инвентаризационный номер оборудования
     */
    @Column(name = "inventory_number", unique = true, nullable = false)
    private String inventoryNumber;

    /**
     * цена обрудования при покупке
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /**
     * дата покупки оборудования
     */
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    /**
     * дата установки оборудования
     */
    @Column(name = "installation_date")
    private LocalDate installationDate;

    /**
     * дата утилизации оборудования
     */
    @Column(name = "disposal_date")
    private LocalDate disposalDate;

    /**
     * ссылка на должность которой положено это оборудование
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

}