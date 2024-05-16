package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.contact.ContactDto;
import com.kasumov.med_ci.model.dto.user.patient.PatientContactDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.items.Contact;

import java.util.List;


public interface ContactDtoService {

    PatientContactDto getPatientContactDto(Patient patient);

    List<ContactDto> getAllContact(List<Contact> contacts);
}
