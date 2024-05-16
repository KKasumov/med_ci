package com.kasumov.med_ci.model.dto.structure.department;

import com.kasumov.med_ci.model.dto.user.contact.ContactDto;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForRegistrarDto;
import com.kasumov.med_ci.model.enums.AgeType;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
public record DepartmentWithDoctorsForRegistrarDto(long id,
                                                   String name,
                                                   AgeType ageType,
                                                   @Nullable String chiefDoctorFirstName,//если есть исполняющий обязанности, передать сюда его данные
                                                   @Nullable String chiefDoctorLastName,//если есть исполняющий обязанности, передать сюда его данные
                                                   @Nullable List<ContactDto> chiefContacts,//получить только контакты телефона и телеграмма
                                                   List<DoctorForRegistrarDto> doctors) {
}
