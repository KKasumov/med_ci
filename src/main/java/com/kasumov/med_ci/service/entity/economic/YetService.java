package com.kasumov.med_ci.service.entity.economic;

import com.kasumov.med_ci.model.entity.economic.Yet;

import java.util.List;

public interface YetService {

    List<Yet> getAll();

    Yet getYetById(long yetId);

    void delete(Yet yet);

    boolean existsById(long yetId);

    Yet findYetById(long yetId);

    Yet save(Yet yet);

    List<String> getMessagesForYet();
}
