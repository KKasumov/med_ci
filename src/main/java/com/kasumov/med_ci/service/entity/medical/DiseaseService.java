package com.kasumov.med_ci.service.entity.medical;

import com.kasumov.med_ci.model.entity.medical.Disease;

import java.util.List;

public interface DiseaseService {

    boolean existsById(long diseaseId);

    void deleteById(long diseaseId);

    Disease findByIdWithDepartment(long diseaseId);
    Disease save(Disease disease);
    List<Disease> getDiseaseListByParameters(String pattern, Long departmentId);

}
