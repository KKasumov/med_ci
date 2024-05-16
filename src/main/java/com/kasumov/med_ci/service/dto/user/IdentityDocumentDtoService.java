package com.kasumov.med_ci.service.dto.user;


import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.entity.user.Patient;

import java.util.List;

public interface IdentityDocumentDtoService {
    List<IdentityDocumentDto> saveNewIdentityByPatient(Patient patient, NewIdentityDocumentDto newIdentityDocumentDto);

}
