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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * диплом подтверждающий квалификацию
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diploma")
public class Diploma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * серийный номер документа
     */
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    /**
     * дата окончания обучения
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * связь с договором в котором используется квалификация
     */
    @OneToOne(mappedBy = "diploma")
    private LaborContract laborContracts;

    /**
     * образовательное учреждение
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

}
