package com.kasumov.med_ci.model.entity.user;

import com.kasumov.med_ci.model.entity.user.history.PatientHistory;
import com.kasumov.med_ci.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.springframework.lang.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

/**
 * Patient - Пациент.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends User {

    /**
     * история пациента
     */
    @OneToOne(mappedBy = "patient")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private PatientHistory patientHistory;


    @Builder
    public Patient(String email, String password, String snils, String firstName, String lastName,
                   @Nullable String patronymic, LocalDate birthday, Gender gender, Role role){
        super(email, password, snils, firstName, lastName, patronymic, birthday, gender, role);
    };
}
