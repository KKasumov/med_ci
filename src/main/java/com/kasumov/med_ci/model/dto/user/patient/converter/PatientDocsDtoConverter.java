package com.kasumov.med_ci.model.dto.user.patient.converter;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.patient.PatientDocsDto;
import com.kasumov.med_ci.model.dto.user.polis.PolisDto;
import com.kasumov.med_ci.model.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PatientDocsDtoConverter {

    public PatientDocsDto entityToPatientDocsDto(User entity,
                                                 List<IdentityDocumentDto> identityDocumentList,
                                                 List<PolisDto> polisDto) {

        return PatientDocsDto.builder()
                .snils(entity.getSnils())
                .identityDocuments(sortedIdentityDocumentList(identityDocumentList))
                .polises(polisDto)
                .build();
    }

    private List<IdentityDocumentDto> sortedIdentityDocumentList(List<IdentityDocumentDto> identityDocumentList) {
        return identityDocumentList.stream()
                .sorted(Comparator.comparing(IdentityDocumentDto::id).reversed())
                .toList();
    }
}
