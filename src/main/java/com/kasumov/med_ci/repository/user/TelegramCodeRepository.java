package com.kasumov.med_ci.repository.user;
import com.kasumov.med_ci.model.entity.user.TelegramCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramCodeRepository  extends JpaRepository<TelegramCode, Long> {
    TelegramCode findByCode(String code);

}
