package com.kasumov.med_ci.model.entity.medical;


import com.kasumov.med_ci.model.entity.economic.price.OmsPriceOfMedicalService;
import com.kasumov.med_ci.model.entity.economic.price.PayPriceOfMedicalService;
import com.kasumov.med_ci.model.entity.structure.Department;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import java.util.List;
import java.util.Set;

/**
 * MedicalService - Медицинская услуга
 * есть прейскурант всех услуг которые оказывает ЛПУ.
 * У каждой услуги есть уникальный идентификатор, например K321101.
 * Услугу может вкл/выкл экономист при помощи поля isDisabled.
 * Экономист создает список услуг которые не привязаны к отделению.
 * Заведующий связывает услуги со своим отделением, чтобы врачи ими пользовались.
 */

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_service")
public class MedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * уникальный идентификатор услуги
     */
    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    /**
     * название услуги
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * флаг - разрешено ли врачам отделения использовать услугу
     */
    @Column(name = "is_disabled", nullable = false)
    private boolean isDisabled;

    /**
     * отделение которое может оказывать услугу
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;



    /**
     * уцены на медицинскую услуги по ОМС
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalService", cascade = CascadeType.REMOVE)
    private Set<OmsPriceOfMedicalService> omsPriceOfMedicalServices;

    /**
     * уцены на медицинскую услуги по ДМС
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalService", cascade = CascadeType.REMOVE)
    private Set<PayPriceOfMedicalService> payPriceOfMedicalServices;

    /**
     * услуга может использоваться в разных посещениях
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "medicalServices")
    private List<Visit> visits;

}
