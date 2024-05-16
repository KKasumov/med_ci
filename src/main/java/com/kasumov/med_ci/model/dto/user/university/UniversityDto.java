package com.kasumov.med_ci.model.dto.user.university;

import com.kasumov.med_ci.model.entity.user.items.Attestation;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.Set;

@Builder
public record UniversityDto(Long id,
                            String name,
                            @Nullable Set<Diploma> diplomas,
                            @Nullable Set<Attestation> attestation
) {
}