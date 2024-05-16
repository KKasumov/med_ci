package com.kasumov.med_ci.model.entity.medical;

import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * Посещение
 * Это каждый раз, когда пациент приходит на прием к врачу в рамках обращения, а тот оказывает услуги.
 */

@Entity
@Getter
@Setter
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * дата посещения
     */
    @Column(name = "day_of_visit", nullable = false)
    private LocalDate dayOfVisit;

    /**
     * доктор, который принял пациента
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_history_id", nullable = false)
    private DoctorHistory doctorHistory;

    /**
     * связь с обращением по заболеванию
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appeal_id", nullable = false)
    private Appeal appeal;

    /**
     * услуги, которые были оказаны пациенту в рамках этого посещения
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "visit_medical_services",
            joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_service_id"))
    private List<MedicalService> medicalServices;

}
