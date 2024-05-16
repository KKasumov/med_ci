package com.kasumov.med_ci.model.dto.user.identityDocument.converter;

import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import com.kasumov.med_ci.util.DateConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdentityDocumentDtoConverter {

    private final DateConvertor dateConvertor;

    public IdentityDocument convertIdentityDocomentFromNewIdentityDocumentDto(NewIdentityDocumentDto dto) {

        return IdentityDocument.builder()
                .documentType(dto.documentType())
                .serial(dto.serial())
                .number(dto.number())
                .dateStart(dateConvertor.stringToLocalDate(dto.dateStart()))
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .patronymic(dto.patronymic())
                .birthday(dateConvertor.stringToLocalDate(dto.birthday()))
                .gender(dto.gender())
                .isDeprecated(false)
                .build();
    }
}
