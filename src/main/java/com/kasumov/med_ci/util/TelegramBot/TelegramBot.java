package com.kasumov.med_ci.util.TelegramBot;

import com.kasumov.med_ci.util.MailService;
import com.kasumov.med_ci.config.BotConfig.BotConfig;
import com.kasumov.med_ci.model.entity.user.TelegramCode;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.model.enums.ContactType;
import com.kasumov.med_ci.repository.user.ContactRepository;
import com.kasumov.med_ci.service.entity.user.PatientService;
import com.kasumov.med_ci.service.entity.user.TelegramCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.sender.DefaultSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.kasumov.med_ci.model.enums.ContactType.TELEGRAM;

@Slf4j
@Component
@EnableScheduling
public class TelegramBot extends TelegramLongPollingBot {

    private final PatientService patientService;
    private final ContactRepository contactRepository;
    private final MailService mailService;
    private final TelegramCodeService telegramCodeService;
    private MessageSender sender;
    final BotConfig config;


    public TelegramBot(PatientService patientService, ContactRepository contactRepository, MailService mailService,
                       TelegramCodeService telegramCodeService, BotConfig config) {
        this.patientService = patientService;
        this.contactRepository = contactRepository;
        this.mailService = mailService;
        this.telegramCodeService = telegramCodeService;
        this.config = config;
        this.sender = new DefaultSender(this);
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    public void setSender(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.startsWith(StringConstansTelegramBot.COMMAND_START)) {
                startMessage(chatId);
            } else if (messageText.startsWith(StringConstansTelegramBot.COMMAND_REGISTRATION)) {
                sendCodeToUserEmail(chatId, messageText);
            } else if (messageText.startsWith(StringConstansTelegramBot.COMMAND_CODE)) {
                registrationUser(chatId, messageText);
            } else if (messageText.startsWith(StringConstansTelegramBot.COMMAND_UNSUBSCRIBE)) {
                unsubscribe(chatId);
            }else {
                sendMessage(chatId, StringConstansTelegramBot.NOT_RECOGNIZED);
            }
        }

    }

    public void startMessage(long chatId) {
        sendMessage(chatId, StringConstansTelegramBot.START_MESSAGE);
    }

    private void unsubscribe(long chatId) {
        List<Contact> contacts = contactRepository.getContactsByChatId(Set.of(TELEGRAM), String.valueOf(chatId));
        for (Contact contact : contacts) {
            contactRepository.delete(contact);
        }
        sendMessage(chatId, StringConstansTelegramBot.SUCCESS_UNSUBSCRIBE);
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            log.error(StringConstansTelegramBot.LOG_ERROR + e.getMessage());
        }
    }

    private void sendCodeToUserEmail(long chatId, String messageText) {
        var mailTo = messageText.substring(messageText.indexOf(" ") + 1);
        if (patientService.getPatientByEmail(mailTo) == null) {
            sendMessage(chatId, StringConstansTelegramBot.PATIENT_NOT_FOUND);
        } else {
            TelegramCode telegramCode = telegramCodeService.save(mailTo, chatId);
            mailService.send(mailTo, StringConstansTelegramBot.SUBJECT_OF_MAIL, telegramCode.getCode());
            sendMessage(chatId, StringConstansTelegramBot.SEND_VERIFICATION_CODE);
        }
    }

    private void registrationUser(long chatId, String messageText) {
        var code = messageText.substring(messageText.indexOf(" ") + 1);
        TelegramCode telegramCode = telegramCodeService.getTelegramCodeByCode(code);
        if (telegramCode == null || telegramCode.getExpirationDate().isBefore(LocalDateTime.now()) || telegramCode.getChatId() != chatId) {
            sendMessage(chatId, StringConstansTelegramBot.INCORRECT_CODE);
        } else {
            contactRepository.save(
                    Contact.builder()
                            .contactType(ContactType.TELEGRAM)
                            .value(String.valueOf(chatId))
                            .userHistory(patientService.getPatientByEmail(telegramCode.getEmail()).getUserHistory())
                            .build());
            telegramCodeService.delete(telegramCode);
            sendMessage(chatId, StringConstansTelegramBot.SUCCESS_REGISTRATION);
        }
    }

    public void sendMessageToChats(String message, long userId) {
        contactRepository.getContactsByUserId(Set.of(TELEGRAM), userId).forEach(contact -> {
            sendMessage(Long.parseLong(contact.getValue()), message);
        });

    }
}


