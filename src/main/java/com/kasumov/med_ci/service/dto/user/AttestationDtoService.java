package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.attestation.AttestationDtoWithUniversityAndContract;
import com.kasumov.med_ci.model.dto.user.attestation.NewAttestationDto;

public interface AttestationDtoService {

    AttestationDtoWithUniversityAndContract saveAttestationByContract(NewAttestationDto newAttestationDto, long contractId);
}
