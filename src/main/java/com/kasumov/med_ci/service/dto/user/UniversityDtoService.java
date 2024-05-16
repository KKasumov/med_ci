package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.university.UniversityDto;
import com.kasumov.med_ci.model.dto.user.university.UniversityDtoShort;

import java.util.List;

public interface UniversityDtoService {

    UniversityDto saveNewUniversityDto(UniversityDtoShort universityDtoShort);
    List<UniversityDto> getAllUniversities();

}
