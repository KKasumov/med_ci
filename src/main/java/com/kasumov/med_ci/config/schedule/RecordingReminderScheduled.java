package com.kasumov.med_ci.config.schedule;

import com.kasumov.med_ci.repository.user.PatientRepository;
import com.kasumov.med_ci.util.MailService;
import com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot;
import com.kasumov.med_ci.util.TelegramBot.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class RecordingReminderScheduled {

    private final PatientRepository patientRepository;
    private final MailService mailService;
    private final TelegramBot telegramBot;

    public RecordingReminderScheduled(PatientRepository patientRepository, MailService mailService, TelegramBot telegramBot) {
        this.patientRepository = patientRepository;
        this.mailService = mailService;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 0 19 * * *")
    public void sendReminders() {
        List<String> reminders = patientRepository.getRemindersForPatients();
        for (Iterator<String> i = reminders.iterator(); i.hasNext(); ) {
            String reminder = i.next();
            int first = reminder.indexOf(",");
            int second = reminder.indexOf(",", first + 1);
            String email = reminder.substring(0, first);
            Long chatId = Long.valueOf(reminder.substring(first + 1, second));
            String message = reminder.substring(second + 1);
            try {
                mailService.send(email, "Напоминание о записи", message);
                if (chatId != 0) {
                    telegramBot.sendMessage(chatId, message);
                }
            } catch (Exception e) {
                log.error(StringConstansTelegramBot.LOG_ERROR + e.getMessage());
            }

        }
    }
}
