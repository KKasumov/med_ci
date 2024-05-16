package com.kasumov.med_ci.model.dto.user.doctor;

import com.kasumov.med_ci.model.dto.user.contact.ContactDto;
import com.kasumov.med_ci.model.enums.Gender;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
public record DoctorForRegistrarDto(long id,
                                    String firstName,
                                    String lastName,
                                    String patronymic,
                                    Gender gender,
                                    @Nullable List<ContactDto> contacts)   //получить только контакты телефона и телеграмма
{
}
