package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdentityDocumentRepository extends JpaRepository<IdentityDocument, Long> {

    @Query("select iddoc from IdentityDocument iddoc where iddoc.userHistory.user.id=:id")
    List<IdentityDocument> getIdentityDocumentsByUserId(Long id);
}
