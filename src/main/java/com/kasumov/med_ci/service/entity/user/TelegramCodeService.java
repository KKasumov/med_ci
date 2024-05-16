package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.user.TelegramCode;

public interface TelegramCodeService {
    TelegramCode save(String email, long chatId);

    TelegramCode getTelegramCodeByCode(String code);

    void delete(TelegramCode telegramCode);
}
