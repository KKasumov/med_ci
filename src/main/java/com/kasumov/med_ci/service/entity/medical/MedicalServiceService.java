package com.kasumov.med_ci.service.entity.medical;

import com.kasumov.med_ci.model.entity.medical.MedicalService;
import org.springframework.lang.Nullable;

import java.util.List;


public interface MedicalServiceService {
    MedicalService save(MedicalService medicalService);

    boolean existByIdentifier(String identifier);

    MedicalService findByIdWithDepartment(Long id);

    List<MedicalService> getMedicalServicesByParameters(String pattern, @Nullable Long departmentId);

    void deleteById(long medicalServiceId);

    boolean existsById(long medicalServiceId);
}
