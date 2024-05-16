package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;

import java.util.List;

public interface IdentityDocumentService {
    List<IdentityDocument> findByUserId(Long id);

    IdentityDocument save(IdentityDocument identityDocument);

    IdentityDocument saveIdentityDocumentForOffer(UserHistory userHistory,
                                                  NewIdentityDocumentDto employeeForHiringDTO);
}