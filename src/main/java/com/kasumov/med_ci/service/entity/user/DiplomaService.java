package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.dto.user.diploma.DiplomaForHiringDto;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import com.kasumov.med_ci.model.entity.user.items.University;

public interface DiplomaService {

    Diploma addDiploma(DiplomaForHiringDto diplomaForHiringDto, University university);

    void deleteDiplomaById(long employeeId);

    boolean existsById(long diplomaId);

    boolean existsBySerialNumber(String serialNumber);

    Diploma saveDiploma(Diploma diploma);

}
