package com.kasumov.med_ci.model.dto.user.identityDocument;

import com.kasumov.med_ci.model.enums.Gender;
import com.kasumov.med_ci.model.enums.IdentityDocumentType;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record IdentityDocumentDto(long id,
                                  IdentityDocumentType documentType,
                                  String serial,          //все символы заменить на # кроме последних 2
                                  String number,          //все символы заменить на # кроме последних 4
                                  String dateStart,       //format dd.MM.yyyy
                                  String firstName,
                                  String lastName,
                                  @Nullable String patronymic,
                                  String birthday,        //format dd.MM.yyyy
                                  Gender gender,
                                  boolean isDeprecated) {
}