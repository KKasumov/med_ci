package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.user.items.Polis;
import java.util.List;
public interface PolisService {
    List<Polis> getPolisesByPatientId(Long patientId);
    Polis save(Polis polis);
    List<Polis> getAllPatientPolis(long id);
}