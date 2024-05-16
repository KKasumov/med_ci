package com.kasumov.med_ci.service.entity.economic;

import com.kasumov.med_ci.model.entity.economic.Smo;
import com.kasumov.med_ci.model.enums.Region;

import java.util.List;

public interface SmoService {

    Smo getSmoById(long smoId);

    List<Smo> getAll();

    List<Smo> getSmoListByParameters(Region region, String pattern);

    boolean existsById(long smoId);

    void delete(Smo smo);
}