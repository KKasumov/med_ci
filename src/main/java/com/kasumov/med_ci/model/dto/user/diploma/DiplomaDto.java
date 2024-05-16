package com.kasumov.med_ci.model.dto.user.diploma;

import com.kasumov.med_ci.model.dto.user.university.UniversityDto;
import lombok.Builder;

@Builder
public record DiplomaDto(Long id,
                         String serial,
                         String endDate,
                         UniversityDto universityDto) {
}
