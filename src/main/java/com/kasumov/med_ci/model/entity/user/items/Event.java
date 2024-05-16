package com.kasumov.med_ci.model.entity.user.items;


import com.kasumov.med_ci.model.enums.EventLevel;
import com.kasumov.med_ci.model.enums.EventType;
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
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * событие.
 * бывает благодарность или взыскание и три уровня
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * тип события
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    /**
     * уровень - низкий, средний, высокий
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "event_level", nullable = false)
    private EventLevel eventLevel;

    /**
     * номер события внесенный в книгу событий
     */
    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    /**
     * дата внесения события
     */
    @Column(name = "date", nullable = false)
    private LocalDate date;

    /**
     * текст.
     * Например: "этим приказом сий холоп приговаривается к двадцати плетям"
     */
    @Column(name = "value", nullable = false)
    private String value;

    /**
     * связь с трудовым договором
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "labor_contract_id", nullable = false)
    private LaborContract laborContract;

}
