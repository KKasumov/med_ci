package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.polis.NewPolisDto;
import com.kasumov.med_ci.model.dto.user.polis.PolisDto;
import com.kasumov.med_ci.model.entity.user.Patient;

import java.util.List;

public interface PolisDtoService {
    List<PolisDto> getAllPatientPolis(Long patientId);
    List<PolisDto> saveNewPolisByPatient(Patient patient, NewPolisDto newPolisDto);
}
