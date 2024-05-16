package com.kasumov.med_ci.model.dto.user.patient;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.polis.PolisDto;
import lombok.Builder;

import java.util.List;
@Builder
public record PatientDocsDto (
        String snils,       //все символы заменить на # кроме последних 4
        List<IdentityDocumentDto> identityDocuments,
        List<PolisDto> polises
){
}
