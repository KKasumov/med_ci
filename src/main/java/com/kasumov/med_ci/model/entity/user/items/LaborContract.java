package com.kasumov.med_ci.model.entity.user.items;

import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * трудовой договор
 * это документ на основании которого любой сотрудник ведет трудовую деятельность.
 * сотрудник может заключать только один трудовой договор
 * сотрудник может занимать от 0.25 до 1.75 ставок (кратно 0.25)
 *
 * PS: знаю что в жизни не так, но реальность будет сложна для исполнения
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "labor_contract")
public class LaborContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * дата начала действия договора.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * дата окончания действия договора.
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * ставка сотрудника по конкретному договору.
     * от 0.25 до 1.75 кратна 0.25
     */
    @Column(name = "part", nullable = false)
    private BigDecimal part;

    /**
     * сотрудник
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_history_id", nullable = false )
    private EmployeeHistory employeeHistory;

    /**
     * должность которую занимает сотрудник
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    /**
     * диплом который позволяет сотруднику занимать эту должность
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diploma_id", nullable = false )
    private Diploma diploma;



    /**
     * отпуска сотрудника связанные с данным трудовым договором
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "laborContract")
    private Set<Vacation> vacations;

    /**
     * аттестации. подтверждающие квалификацию
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "laborContract")
    private Set<Attestation> attestations;

    /**
     * события во время работы сотрудника (благодарности и взыскания на текущей должности)
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "laborContract")
    private Set<Event> events;
}
