package com.kasumov.med_ci.config.schedule;

import com.kasumov.med_ci.service.entity.medical.TalonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class DeleteTalonsByTimeScheduled {
    private final TalonService talonService;
    private static final Logger logger = LoggerFactory.getLogger(DeleteTalonsByTimeScheduled.class);

    private final String dataCron = "0 0 0 * * *";
    @Scheduled(cron = dataCron)
    public void deleteTalonsByTime() {
        logger.info("Время начала удаления просроченных записей " + new Date());
        try {
            LocalDateTime time = LocalDateTime.now();
            Integer countDeleteTalon = talonService.deleteTalonsByTime(time);
            logger.info("Удалено " + countDeleteTalon + " записей. Время окончания " + new Date());
        } catch (Exception e) {
            logger.error("Удаления просроченных талонов не произошло");
        }
    }
}
