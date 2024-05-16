package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.TelegramCodeRepository;
import com.kasumov.med_ci.util.Generator;
import com.kasumov.med_ci.model.entity.user.TelegramCode;
import com.kasumov.med_ci.service.entity.user.TelegramCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TelegramCodeServiceImpl implements TelegramCodeService {

    private final TelegramCodeRepository telegramCodeRepository;
    private final Generator generator;

    @Value("6")
    private int randomCodeLength;

    @Value("1")
    private int expirationPeriod;

    @Override
    public TelegramCode save(String email, long chatId) {
        return telegramCodeRepository.save(
                TelegramCode.builder()
                        .email(email)
                        .code(generator.getRandomString(randomCodeLength))
                        .chatId(chatId)
                        .expirationDate(LocalDateTime.now().plusHours(expirationPeriod))
                        .build());
    }

    @Override
    public TelegramCode getTelegramCodeByCode(String code) {
        return telegramCodeRepository.findByCode(code);
    }

    @Override
    public void delete(TelegramCode telegramCode) {
        telegramCodeRepository.delete(telegramCode);
    }
}
