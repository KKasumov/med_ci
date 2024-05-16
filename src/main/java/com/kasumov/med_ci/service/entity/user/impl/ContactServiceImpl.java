package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.model.dto.user.contact.ContactEmployeeDto;
import com.kasumov.med_ci.model.dto.user.contact.converter.ContactDtoConverter;
import com.kasumov.med_ci.repository.user.ContactRepository;
import com.kasumov.med_ci.util.ContactFormat;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.model.enums.ContactType;
import com.kasumov.med_ci.service.entity.user.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.kasumov.med_ci.model.enums.ContactType.PHONE;
import static com.kasumov.med_ci.model.enums.ContactType.TELEGRAM;
import static com.kasumov.med_ci.model.enums.ContactType.ADDRESS;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactFormat contactFormat;
    private final ContactDtoConverter contactDtoConverter;


    @Override
    public List<Contact> getPhoneAndTelegramByDoctor(long doctorId) {
        return contactRepository.getContactsByTypeAndDoctor(Set.of(PHONE, TELEGRAM), doctorId);
    }

    @Override
    public List<Contact> getAllContactByPatient(long patientId) {

        List<Contact> list = contactRepository.getContactsByUserId(Set.of(ADDRESS, PHONE, TELEGRAM), patientId);
        for(Contact contact1 : list) {
            switch (contact1.getContactType()) {
                case ADDRESS -> contact1.setValue(contactFormat.formatAddress(contact1.getValue()));
                case TELEGRAM -> contact1.setValue(contactFormat.formatTelegram(contact1.getValue()));
                case PHONE -> contact1.setValue(contactFormat.formatPhone(contact1.getValue()));
            }
        }
        return list;
    }

    @Override
    public List<Contact> getContactsByUserId(Set<ContactType> types, long userId) {
        return contactRepository.getContactsByUserId(types, userId);
    }

    @Override
    public List<Contact> getContactsByChatId(Set<ContactType> types, String chatId) {
        return contactRepository.getContactsByChatId(types, chatId);
    }

    @Override
    public List<Contact> saveAll(List<ContactEmployeeDto> contactDtos, UserHistory userHistory) {
        return contactRepository.saveAll(contactDtoConverter.convertToEntityList(contactDtos, userHistory));
    }
}