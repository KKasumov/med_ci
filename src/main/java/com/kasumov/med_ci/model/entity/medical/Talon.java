package com.kasumov.med_ci.model.entity.medical;

import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import com.kasumov.med_ci.model.entity.user.history.PatientHistory;
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
import java.time.LocalDateTime;

/**
 * Талон на прием к врачу
 * талон удаляется при явке пациента, и у пациента создается посещение
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "talon")
public class Talon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * дата и время записи на прием
     */
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    /**
     * связь талона с пациентом
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_history_id")
    private PatientHistory patientHistory;

    /**
     * связь талона с доктором
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_history_id", nullable = false)
    private DoctorHistory doctorHistory;

}
