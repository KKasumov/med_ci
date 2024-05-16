package com.kasumov.med_ci.TelegramBot;

import com.kasumov.med_ci.config.BotConfig.BotConfig;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.TelegramCode;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.repository.user.ContactRepository;
import com.kasumov.med_ci.service.entity.user.PatientService;
import com.kasumov.med_ci.service.entity.user.TelegramCodeService;
import com.kasumov.med_ci.util.MailService;
import com.kasumov.med_ci.util.TelegramBot.TelegramBot;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.kasumov.med_ci.model.enums.ContactType.TELEGRAM;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.COMMAND_CODE;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.COMMAND_REGISTRATION;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.COMMAND_START;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.COMMAND_UNSUBSCRIBE;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.INCORRECT_CODE;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.PATIENT_NOT_FOUND;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.SEND_VERIFICATION_CODE;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.START_MESSAGE;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.SUCCESS_REGISTRATION;
import static com.kasumov.med_ci.util.TelegramBot.StringConstansTelegramBot.SUCCESS_UNSUBSCRIBE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class TelegramBotIT {
    public static final long CHAT_ID = 1337L;
    public static final long INCORRECT_CHAT_ID = 1237L;
    public static final String TEST_EMAIL = "test@mail.ru";
    public static final String TEST_CODE = "123QWE";
    private TelegramBot bot;
    private MessageSender messageSender;
    private PatientService patientService;
    private ContactRepository contactRepository;
    private MailService mailService;
    private TelegramCodeService telegramCodeService;
    BotConfig botConfig;


    @Before
    public void setUp() {
        botConfig = new BotConfig();
        patientService = mock(PatientService.class);
        contactRepository = mock(ContactRepository.class);
        mailService = mock(MailService.class);
        telegramCodeService = mock(TelegramCodeService.class);
        messageSender = mock(MessageSender.class);
        bot = new TelegramBot(patientService, contactRepository, mailService, telegramCodeService, botConfig);
        bot.onRegister();
        bot.setSender(messageSender);
    }

    @Test
    public void startMessage() throws TelegramApiException {
        Update upd = new Update();
        Message message = new Message();
        upd.setMessage(message);
        upd.getMessage().setText(COMMAND_START);
        upd.getMessage().setChat(new Chat(CHAT_ID, "private"));
        bot.onUpdateReceived(upd);
        SendMessage answer = new SendMessage();
        answer.setChatId(CHAT_ID);
        answer.setText(START_MESSAGE);
        Mockito.verify(messageSender, times(1)).execute(answer);
    }

    @Test
    public void sendCodeToUserEmailPatientNotFound() throws TelegramApiException {
        Update upd = new Update();
        Message message = new Message();
        upd.setMessage(message);
        upd.getMessage().setText(COMMAND_REGISTRATION + " " + TEST_EMAIL);
        upd.getMessage().setChat(new Chat(CHAT_ID, "private"));
        when(patientService.getPatientByEmail(COMMAND_REGISTRATION + " " + TEST_EMAIL)).thenReturn(null);
        bot.onUpdateReceived(upd);
        SendMessage answer = new SendMessage();
        answer.setChatId(CHAT_ID);
        answer.setText(PATIENT_NOT_FOUND);
        Mockito.verify(messageSender, times(1)).execute(answer);
    }

    @Test
    public void sendCodeToUserEmailExistPatient() throws TelegramApiException {
        Update upd = new Update();
        Message message = new Message();
        upd.setMessage(message);
        upd.getMessage().setText(COMMAND_REGISTRATION + " " + TEST_EMAIL);
        upd.getMessage().setChat(new Chat(CHAT_ID, "private"));
        when(patientService.getPatientByEmail(TEST_EMAIL)).thenReturn(new Patient());
        when(telegramCodeService.save(TEST_EMAIL, CHAT_ID)).thenReturn(new TelegramCode());
        bot.onUpdateReceived(upd);
        SendMessage answer = new SendMessage();
        answer.setChatId(CHAT_ID);
        answer.setText(SEND_VERIFICATION_CODE);
        Mockito.verify(messageSender, times(1)).execute(answer);
    }

    @Test
    public void registrationUserTelegramCodeIncorrect() throws TelegramApiException {
        Update upd = new Update();
        Message message = new Message();
        upd.setMessage(message);
        upd.getMessage().setText(COMMAND_CODE + " " + TEST_CODE);
        upd.getMessage().setChat(new Chat(CHAT_ID, "private"));
        when(telegramCodeService.getTelegramCodeByCode(TEST_CODE)).thenReturn(null);
        bot.onUpdateReceived(upd);
        SendMessage answer = new SendMessage();
        answer.setChatId(CHAT_ID);
        answer.setText(INCORRECT_CODE);
        Mockito.verify(messageSender, times(1)).execute(answer);
    }

    @Test
    public void registrationUserTelegramCodeOverdue() throws TelegramApiException {
        Update upd = new Update();
        Message message = new Message();
        upd.setMessage(message);
        upd.getMessage().setText(COMMAND_CODE + " " + TEST_CODE);
        upd.getMessage().setChat(new Chat(CHAT_ID, "private"));
        when(telegramCodeService.getTelegramCodeByCode(TEST_CODE)).thenReturn(new TelegramCode(TEST_EMAIL, TEST_CODE,
                CHAT_ID, LocalDateTime.of(2016, Month.JULY, 9, 11, 6, 22)));
        bot.onUpdateReceived(upd);
        SendMessage answer = new SendMessage();
        answer.setChatId(CHAT_ID);
        answer.setText(INCORRECT_CODE);
        Mockito.verify(messageSender, times(1)).execute(answer);
    }

    @Test
    public void registrationUserTelegramCodeIncorrectChatId() throws TelegramApiException {
        Update upd = new Update();
        Message message = new Message();
        upd.setMessage(message);
        upd.getMessage().setText(COMMAND_CODE + " " + TEST_CODE);
        upd.getMessage().setChat(new Chat(CHAT_ID, "private"));
        when(telegramCodeService.getTelegramCodeByCode(TEST_CODE)).thenReturn(new TelegramCode(TEST_EMAIL, TEST_CODE,
                INCORRECT_CHAT_ID, LocalDateTime.of(2036, Month.JULY, 9, 11, 6, 22)));
        bot.onUpdateReceived(upd);
        SendMessage answer = new SendMessage();
        answer.setChatId(CHAT_ID);
        answer.setText(INCORRECT_CODE);
        Mockito.verify(messageSender, times(1)).execute(answer);
    }

    @Test
    public void registrationUserTelegramCodeCorrect() throws TelegramApiException {
        Update upd = new Update();
        Message message = new Message();
        upd.setMessage(message);
        upd.getMessage().setText(COMMAND_CODE + " " + TEST_CODE);
        upd.getMessage().setChat(new Chat(CHAT_ID, "private"));
        when(telegramCodeService.getTelegramCodeByCode(TEST_CODE)).thenReturn(new TelegramCode(TEST_EMAIL, TEST_CODE,
                        CHAT_ID, LocalDateTime.of(2036, Month.JULY, 9, 11, 6, 22)))
                .thenReturn(null);
        when(patientService.getPatientByEmail(TEST_EMAIL)).thenReturn(new Patient());
        bot.onUpdateReceived(upd);
        SendMessage answer = new SendMessage();
        answer.setChatId(CHAT_ID);
        answer.setText(SUCCESS_REGISTRATION);
        Mockito.verify(messageSender, times(1)).execute(answer);
    }

    @Test
    public void unsubscribe() throws TelegramApiException {
        Update upd = new Update();
        Message message = new Message();
        upd.setMessage(message);
        upd.getMessage().setText(COMMAND_UNSUBSCRIBE);
        upd.getMessage().setChat(new Chat(CHAT_ID, "private"));
        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact(1L, TELEGRAM, String.valueOf(CHAT_ID), new UserHistory());
        contacts.add(contact);
        when(contactRepository.getContactsByChatId(Set.of(TELEGRAM), String.valueOf(CHAT_ID))).thenReturn(contacts)
                .thenReturn(null);
        bot.onUpdateReceived(upd);
        SendMessage answer = new SendMessage();
        answer.setChatId(CHAT_ID);
        answer.setText(SUCCESS_UNSUBSCRIBE);
        Mockito.verify(messageSender, times(1)).execute(answer);
    }

}
