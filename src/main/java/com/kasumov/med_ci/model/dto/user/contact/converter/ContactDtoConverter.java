package com.kasumov.med_ci.model.dto.user.contact.converter;

import com.kasumov.med_ci.model.dto.user.contact.ContactDto;
import com.kasumov.med_ci.model.dto.user.contact.ContactEmployeeDto;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactDtoConverter {

    public List<ContactDto> convertorListContactToContactDto(List<Contact> contactList) {
        if (contactList != null) {
            return contactList.stream()
                    .map(contact -> ContactDto.builder()
                            .id(contact.getId())
                            .contactType(contact.getContactType())
                            .value(contact.getValue())
                            .build())
                    .toList();
        } else {
            return null;
        }
    }

    public List<Contact> convertToEntityList(List<ContactEmployeeDto> contactDtos, UserHistory userHistory) {
        return contactDtos.stream().map(
                        dto -> convertToEntity(dto, userHistory))
                .collect(Collectors.toList());
    }

    public Contact convertToEntity(ContactEmployeeDto contactEmployeeDto, UserHistory userHistory) {
        Contact contact = new Contact();
        contact.setValue(contactEmployeeDto.value());
        contact.setContactType(contactEmployeeDto.contactType());
        contact.setUserHistory(userHistory);
        return contact;
    }

}
