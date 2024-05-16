package com.kasumov.med_ci.TelegramBot;
import com.kasumov.med_ci.repository.user.PatientRepository;
import com.kasumov.med_ci.config.schedule.RecordingReminderScheduled;
import com.kasumov.med_ci.util.MailService;
import com.kasumov.med_ci.util.TelegramBot.TelegramBot;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class RecordingReminderScheduledIt {
    public static final long CHAT_ID = 1337L;
    public static final String TEST_EMAIL = "test@mail.ru";
    public static final String TEST_MESSAGE = "Напоминаем, что Вы записаны на завтра к доктору Petrova1 Irina1 Olegovna в отделение Surgery на время 2023-04-25 16:00:00";
    private PatientRepository patientRepository;

    private MailService mailService;

    private TelegramBot telegramBot;

    private RecordingReminderScheduled scheduledTasks;

    @Before
    public void setUp() {
        patientRepository = mock(PatientRepository.class);
        mailService = mock(MailService.class);
        telegramBot = mock(TelegramBot.class);
        scheduledTasks = new RecordingReminderScheduled(patientRepository, mailService, telegramBot);
    }

    @Test
    public void sendRemindersSendsMailAndTelegram() throws Exception {
        String reminder = TEST_EMAIL + "," + CHAT_ID + "," + TEST_MESSAGE;
        List<String> reminders = new ArrayList<>();
        reminders.add(reminder);
        Mockito.when(patientRepository.getRemindersForPatients()).thenReturn(reminders);
        scheduledTasks.sendReminders();
        Mockito.verify(mailService).send(TEST_EMAIL, "Напоминание о записи", TEST_MESSAGE);
        Mockito.verify(telegramBot).sendMessage(CHAT_ID, TEST_MESSAGE);
    }
}
