package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.identityDocument.converter.IdentityDocumentConverter;
import com.kasumov.med_ci.model.dto.user.patient.PatientDocsDto;
import com.kasumov.med_ci.model.dto.user.patient.converter.PatientDocsDtoConverter;
import com.kasumov.med_ci.model.dto.user.polis.PolisDto;
import com.kasumov.med_ci.model.dto.user.polis.converter.PolisDtoConverter;
import com.kasumov.med_ci.service.entity.user.IdentityDocumentService;
import com.kasumov.med_ci.service.entity.user.PolisService;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.service.dto.user.DocumentsDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DocumentsDtoServiceImpl implements DocumentsDtoService {
    private final IdentityDocumentService identityDocumentService;
    private final PatientDocsDtoConverter patientDocsDtoConverter;
    private final IdentityDocumentConverter identityDocumentConverter;
    private final PolisDtoConverter polisDtoConverter;
    private final PolisService polisService;

    @Override
    public PatientDocsDto getPatientDocs(Patient patient) {
        return patientDocsDtoConverter
                .entityToPatientDocsDto(patient, getIdentityDocumentDtos(patient.getId()), getPolisDtos(patient.getId()));
    }

    private List<IdentityDocumentDto> getIdentityDocumentDtos(Long patientId) {
        return identityDocumentService.findByUserId(patientId)
                .stream()
                .map(identityDocumentConverter::entityToIdentityDocumentDto)
                .toList();
    }

    private List<PolisDto> getPolisDtos(Long id) {
        return polisService.getPolisesByPatientId(id)
                .stream()
                .map(polisDtoConverter::entityToPatientDto)
                .toList();
    }
}