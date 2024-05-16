package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.user.Invite;

public interface InviteService {

    Invite save(String email);

    Invite getInviteByToken(String token);

    void delete(Invite invite);
}
