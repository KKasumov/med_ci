package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.university.UniversityDto;
import com.kasumov.med_ci.model.dto.user.university.UniversityDtoShort;
import com.kasumov.med_ci.model.dto.user.university.converter.UniversityDtoConverter;
import com.kasumov.med_ci.service.entity.user.UniversityService;
import com.kasumov.med_ci.model.entity.user.items.University;
import com.kasumov.med_ci.service.dto.user.UniversityDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UniversityDtoServiceImpl implements UniversityDtoService {

    private final UniversityService universityService;
    private final UniversityDtoConverter universityDtoConverter;

    @Override
    public UniversityDto saveNewUniversityDto(UniversityDtoShort universityDtoShort) {
        return universityDtoConverter.convertUniversityToUniversityDto(universityService
                .saveNewUniversity(University.builder()
                        .name(universityDtoShort.name())
                        .build()));
    }

    @Override
    public List<UniversityDto> getAllUniversities() {
        return universityService.getAllUniversities().stream()
                .map(universityDtoConverter::convertUniversityToUniversityDto)
                .toList();
    }
}
