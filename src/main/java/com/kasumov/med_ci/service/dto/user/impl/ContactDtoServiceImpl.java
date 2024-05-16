package com.kasumov.med_ci.service.dto.user.impl;


import com.kasumov.med_ci.model.dto.user.contact.ContactDto;
import com.kasumov.med_ci.model.dto.user.contact.converter.ContactDtoConverter;
import com.kasumov.med_ci.model.dto.user.patient.PatientContactDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.service.dto.user.ContactDtoService;
import com.kasumov.med_ci.service.entity.user.ContactService;
import com.kasumov.med_ci.util.ContactFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ContactDtoServiceImpl implements ContactDtoService {
    private final ContactService contactService;
    private final ContactDtoConverter contactDtoConverter;
    private final ContactFormat contactFormat;

    @Override
    public PatientContactDto getPatientContactDto(Patient patient) {
        return PatientContactDto.builder()
                        .email(contactFormat.formatMail(patient.getEmail()))
                        .contacts(contactDtoConverter.convertorListContactToContactDto(contactService.getAllContactByPatient(patient.getId())))
                        .build();
    }

    @Override
    public List<ContactDto> getAllContact(List<Contact> contacts) {
        return contactDtoConverter.convertorListContactToContactDto(contacts);
    }
}
