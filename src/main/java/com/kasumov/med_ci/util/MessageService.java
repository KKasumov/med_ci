package com.kasumov.med_ci.util;

import com.kasumov.med_ci.model.dto.utill.MessageDto;
import com.kasumov.med_ci.util.TelegramBot.TelegramBot;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.user.Invite;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageService {

    private final MailService mailService;

    private final DateConvertor dateConvertor;

    private final TelegramBot telegramBot;

    @Value("${testSystem.server.address}")
    private String serverAddress;

    public void sendInviteMessage(Invite invite) {
        mailService.send(invite.getEmail(),
                "Changing the password assigned during registration to a custom one",
                String.format("""
                        Congratulations on registering with the mentally retarded hospital
                        medical information system. Follow the link to set your own password
                        in your personal account: \n
                        %s/api/auth/confirm/emailpassword?token=%s&password=
                        """, serverAddress, invite.getToken()));
    }

    public void sendAppointmentMessage(Patient patient) {
        String message = "Вы записаны на прием";
        mailService.send(patient.getEmail(), message, "Ваш талон на запись");
        telegramBot.sendMessageToChats(message, patient.getId());
    }

    public void sendEmailPatientMessage(Patient patient, MessageDto dto) {
        String message =  String.format("""
                        Вы записаны на приём:
                        Название отделения: %s
                        Имя доктора: %s
                        Фамилия доктора: %s
                        Отчество доктора: %s
                        Время визита: %s
                        """, dto.nameDepartment(),
                dto.nameDoctor(),
                dto.surnameDoctor(),
                dto.patronymicDoctor(),
                dto.time());

        mailService.send(patient.getEmail(), "Вы записаны на прием", message);
        telegramBot.sendMessageToChats(message, patient.getId());
    }

    public void sendEmailPatientMessageAboutNewTalon(Patient patient, Talon newTalon) {
        String message = String.format("""
                        Номер Вашего талона: %s \r
                        Номер Вашего отделения: %s \r
                        Имя Вашего доктора: %s
                        """,
                newTalon.getId(),
                newTalon.getDoctorHistory().getDepartment().getName(),
                newTalon.getDoctorHistory().getEmployee().getFirstName() + " "
                        + newTalon.getDoctorHistory().getEmployee().getLastName());

        mailService.send(patient.getEmail(), "Вы перезаписаны на новый талон", message);
        telegramBot.sendMessageToChats(message, patient.getId());

    }

    public void sendEmailEmployeeTerminationLaborContact(LaborContract laborContract) {
        String sb = laborContract.getEmployeeHistory().getEmployee().getFirstName() + " "
                + laborContract.getEmployeeHistory().getEmployee().getLastName() + " "
                + laborContract.getEmployeeHistory().getEmployee().getPatronymic();
        mailService.send(laborContract.getEmployeeHistory().getEmployee().getEmail(),
                "Разрыв трудового соглашения",
                String.format("Уважаемый %s, с Вам был расторгнут трудовой договор №%s с %s до %s. "
                                + "Занимаемая должность %s.", sb,
                        laborContract.getId(),
                        dateConvertor.localDateToString(laborContract.getStartDate()),
                        dateConvertor.localDateToString(laborContract.getEndDate()),
                        laborContract.getPosition().getName()));
    }
}
