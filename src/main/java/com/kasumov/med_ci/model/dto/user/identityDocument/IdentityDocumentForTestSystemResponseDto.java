package com.kasumov.med_ci.model.dto.user.identityDocument;

import com.kasumov.med_ci.model.enums.IdentityDocumentType;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record IdentityDocumentForTestSystemResponseDto(String serial,
                                                       String number,
                                                       String lastName,
                                                       String firstName,
                                                       @Nullable String patronymic,
                                                       String address,
                                                       String birthday,
                                                       String startOfDocument,
                                                       String endOfDocument,
                                                       IdentityDocumentType documentType) {
}
