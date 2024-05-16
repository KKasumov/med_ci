package com.kasumov.med_ci.model.entity.user.history;

import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.medical.Visit;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DoctorHistory extends EmployeeHistory {

    /**
     * талоны доктора
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctorHistory")
    private List<Talon> talons;

    /**
     * все посещения на которых лечил доктор
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctorHistory")
    private List<Visit> visits;

    @Column(name = "add_talon_auto", nullable = false)
    private boolean addTalonAuto = false;

    @Builder()
    public DoctorHistory(Long id,
                         boolean isPublic,
                         Employee employee,
                         LaborContract laborContract,
                         Department department,
                         boolean addTalonAuto) {
        super(id, isPublic, employee, laborContract, department);
        this.addTalonAuto = addTalonAuto;
    }
}
