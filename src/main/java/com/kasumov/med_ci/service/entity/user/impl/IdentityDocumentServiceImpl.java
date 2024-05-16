package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.identityDocument.converter.IdentityDocumentConverter;
import com.kasumov.med_ci.repository.user.IdentityDocumentRepository;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import com.kasumov.med_ci.service.entity.user.IdentityDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IdentityDocumentServiceImpl implements IdentityDocumentService {

    private final IdentityDocumentRepository identityDocumentRepository;
    private final IdentityDocumentConverter identityDocumentConverter;

    @Override
    public List<IdentityDocument> findByUserId(Long id) {
        return identityDocumentRepository.getIdentityDocumentsByUserId(id);
    }

    @Override
    public IdentityDocument save(IdentityDocument identityDocument) {
        return identityDocumentRepository.save(identityDocument);
    }

    @Override
    public IdentityDocument saveIdentityDocumentForOffer(UserHistory userHistory,
                                                         NewIdentityDocumentDto dto) {
        IdentityDocument identityDocument = identityDocumentConverter.newIdentityDocumentDtoToEntity(dto);
        identityDocument.setUserHistory(userHistory);
        return identityDocumentRepository.save(identityDocument);
    }
}