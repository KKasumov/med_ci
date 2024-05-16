package com.kasumov.med_ci.model.dto.user.identityDocument.converter;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import com.kasumov.med_ci.util.DateConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class IdentityDocumentConverter {
    private final DateConvertor dateConvertor;

    public String reverseStringData(String str) {
        String str1[] = str.split("-");
        str = str1[2] + "." + str1[1] + "." + str1[0];
        return str;
    }

    public String replaceCharacters(String str, int len) {
        if (str.length() <= len) {
            len = 0;
        }
        return "#".repeat(str.length() - len) + str.substring(str.length() - len);
    }

    public IdentityDocumentDto entityToIdentityDocumentDto(IdentityDocument identityDocument) {
        return IdentityDocumentDto.builder()
                .id(identityDocument.getId())
                .documentType(identityDocument.getDocumentType())
                .serial(replaceCharacters(identityDocument.getSerial(),2))
                .number(replaceCharacters(identityDocument.getNumber(),2))
                .dateStart(reverseStringData(String.valueOf(identityDocument.getDateStart())))
                .firstName(identityDocument.getFirstName())
                .lastName(identityDocument.getLastName())
                .patronymic(identityDocument.getPatronymic())
                .birthday(reverseStringData(String.valueOf(identityDocument.getBirthday())))
                .gender(identityDocument.getGender())
                .isDeprecated(identityDocument.isDeprecated())
                .build();
    }

    public IdentityDocument newIdentityDocumentDtoToEntity(NewIdentityDocumentDto dto) {
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