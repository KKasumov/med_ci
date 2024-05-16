package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    Invite findByToken(String token);

    Boolean existsByEmail(String email);
}
