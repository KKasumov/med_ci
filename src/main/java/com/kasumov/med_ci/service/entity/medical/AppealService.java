package com.kasumov.med_ci.service.entity.medical;

import com.kasumov.med_ci.model.entity.medical.Appeal;

public interface AppealService {

    boolean existsByDiseaseId(long diseaseId);

    boolean existsById(Long appealId);

    Appeal findWithPatient(long appealId);

    Appeal findWithVisits(long appealId);

}
