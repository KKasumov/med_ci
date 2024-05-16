package com.kasumov.med_ci.model.dto.user.invite;

import lombok.Builder;

@Builder
public record InviteDto(
        String email,
        String token,
        String expirationDate
) {
}
