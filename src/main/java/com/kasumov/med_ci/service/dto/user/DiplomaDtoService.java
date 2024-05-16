package com.kasumov.med_ci.service.dto.user;


import com.kasumov.med_ci.model.dto.user.diploma.DiplomaDtoWithUniversityAndContract;
import com.kasumov.med_ci.model.dto.user.diploma.DiplomaForHiringDto;


public interface DiplomaDtoService {

    DiplomaDtoWithUniversityAndContract saveDiplomaByContract(DiplomaForHiringDto newDiplomaDto, long contractId);

}
