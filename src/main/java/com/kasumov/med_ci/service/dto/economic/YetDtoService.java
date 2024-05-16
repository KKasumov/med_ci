package com.kasumov.med_ci.service.dto.economic;

import com.kasumov.med_ci.model.dto.economic.yet.YetDto;

import java.util.List;

public interface YetDtoService {
    List<YetDto> getAllYet();
    YetDto editYetDto(YetDto yetDto);
}
