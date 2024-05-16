package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.dto.user.contact.ContactEmployeeDto;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.model.enums.ContactType;

import java.util.List;
import java.util.Set;

public interface ContactService {
    List<Contact> getPhoneAndTelegramByDoctor(long doctorId);

    List<Contact> getAllContactByPatient(long patientId);

    List<Contact> getContactsByUserId(Set<ContactType> types, long userId);

    List<Contact> getContactsByChatId(Set<ContactType> types, String chatId);


    List<Contact> saveAll(List<ContactEmployeeDto> contactDtos, UserHistory userHistory);
}
