package com.kasumov.med_ci.service.dto.medical;
import com.kasumov.med_ci.model.dto.medical.appeal.AppealForPatientNativeFullDto;

public interface AppealDtoService {
    AppealForPatientNativeFullDto getFullInfoAboutAppeal(Long appealId);
}
