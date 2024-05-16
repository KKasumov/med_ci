package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.InviteRepository;
import com.kasumov.med_ci.util.Generator;
import com.kasumov.med_ci.model.entity.user.Invite;
import com.kasumov.med_ci.service.entity.user.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {

    private final InviteRepository inviteRepository;
    private final Generator generator;
    @Value("16")
    private int randomTokenLength;
    @Value("${mis.property.invite.expirationPeriod.hours}")
    private int expirationPeriod;

    @Override
    public Invite save(String email) {
        return inviteRepository.save(
                Invite.builder()
                        .email(email)
                        .token(generator.getRandomString(randomTokenLength))
                        .expirationDate(LocalDateTime.now().plusHours(expirationPeriod))
                        .build());
    }

    @Override
    public Invite getInviteByToken(String token) {
        return inviteRepository.findByToken(token);
    }

    @Override
    public void delete(Invite invite) {
        inviteRepository.delete(invite);
    }
}
