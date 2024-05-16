package com.kasumov.med_ci.model.entity.user.history;

import com.kasumov.med_ci.model.entity.medical.Appeal;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.items.Polis;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class PatientHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * связь с пациентом
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /**
     * талоны на которые записан пациент
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patientHistory")
    private Set<Talon> talons;

    /**
     * обращения пациента
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patientHistory")
    private Set<Appeal> appeals;

    /**
     * полисы
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patientHistory")
    private Set<Polis> polises;

    public PatientHistory(Patient patient) {
        this.patient = patient;
    }

}
