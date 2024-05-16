package com.kasumov.med_ci.model.entity.user;

import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Doctor - Врач
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends Employee {

    /**
     * связь с историей доктора
     **/
    @OneToOne(mappedBy = "employee")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private DoctorHistory doctorHistory;

}
