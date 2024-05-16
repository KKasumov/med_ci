package com.kasumov.med_ci.model.dto.structure.department;

import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DepartmentWithTalonsDto {
    private long id;
    private String name;
    private AgeType ageType;
    @Nullable
    private String chiefDoctorFirstName;
    @Nullable
    private String chiefDoctorLastName;
    private List<TalonDto> talons;
}