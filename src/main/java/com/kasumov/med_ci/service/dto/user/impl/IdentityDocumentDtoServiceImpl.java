package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.identityDocument.converter.IdentityDocumentConverter;
import com.kasumov.med_ci.service.entity.user.IdentityDocumentService;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import com.kasumov.med_ci.service.dto.user.IdentityDocumentDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IdentityDocumentDtoServiceImpl implements IdentityDocumentDtoService {
    private final IdentityDocumentService identityDocumentService;
    private final IdentityDocumentConverter identityConverter;

    @Override
    @Transactional
    public List<IdentityDocumentDto> saveNewIdentityByPatient(Patient patient, NewIdentityDocumentDto dto) {
        identityDocumentService.findByUserId(patient.getId())
                .forEach(identityDocument -> identityDocument.setDeprecated(true));
        IdentityDocument identityDocument = identityConverter.newIdentityDocumentDtoToEntity(dto);
        identityDocument.setUserHistory(patient.getUserHistory());
        identityDocumentService.save(identityDocument);
        return identityDocumentService.findByUserId(patient.getId()).stream()
                .map(identityConverter::entityToIdentityDocumentDto)
                .sorted(Comparator.comparing(IdentityDocumentDto::id).reversed())
                .toList();
    }
}
