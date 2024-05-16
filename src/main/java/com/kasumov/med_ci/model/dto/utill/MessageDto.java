package com.kasumov.med_ci.model.dto.utill;

import lombok.Builder;
import org.springframework.lang.Nullable;


@Builder
public record MessageDto (String nameDepartment,
                          String nameDoctor,
                          String surnameDoctor,
                          @Nullable
                          String patronymicDoctor,
                          String time) {

}
