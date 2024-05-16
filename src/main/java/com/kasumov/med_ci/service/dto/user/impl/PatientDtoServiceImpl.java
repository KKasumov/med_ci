package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.identityDocument.converter.IdentityDocumentDtoConverter;
import com.kasumov.med_ci.model.dto.user.patient.CurrentPatientDto;
import com.kasumov.med_ci.model.dto.user.patient.NewPatientDto;
import com.kasumov.med_ci.model.dto.user.patient.PatientDto;
import com.kasumov.med_ci.model.dto.user.patient.converter.PatientDtoConverter;
import com.kasumov.med_ci.util.MessageService;
import com.kasumov.med_ci.model.entity.user.Invite;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.history.PatientHistory;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import com.kasumov.med_ci.model.enums.Gender;
import com.kasumov.med_ci.service.dto.user.PatientDtoService;
import com.kasumov.med_ci.service.entity.user.IdentityDocumentService;
import com.kasumov.med_ci.service.entity.user.InviteService;
import com.kasumov.med_ci.service.entity.user.PatientHistoryService;
import com.kasumov.med_ci.service.entity.user.PatientService;
import com.kasumov.med_ci.service.entity.user.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientDtoServiceImpl implements PatientDtoService {
    private final PatientDtoConverter patientDtoConverter;
    private final IdentityDocumentDtoConverter identityDocumentDtoConverter;
    private final PatientService patientService;
    private final PatientHistoryService patientHistoryService;
    private final UserHistoryService userHistoryService;
    private final IdentityDocumentService identityDocumentService;
    private final InviteService inviteService;
    private final MessageService messageService;

    @Override
    public CurrentPatientDto getCurrentPatientDto(Patient patient) {
        return patientDtoConverter.entityToCurrentPatientDto(patient);
    }

    @Override
    @Transactional
    public PatientDto setPatientIsEnabled(Long patientId, boolean isEnabled) {
        Patient patient = patientService.findPatientById(patientId).get();
        patient.setEnabled(isEnabled);
        return patientDtoConverter.entityToPatientDto(patient);
    }

    @Override
    @Transactional
    public PatientDto saveNewPatient(NewPatientDto newPatientDto) {
        Patient savedPatient = patientService.save(patientDtoConverter.convertPatientFromNewPatientDto(newPatientDto));

        patientHistoryService.save(new PatientHistory(savedPatient));

        UserHistory userHistory = userHistoryService.save(new UserHistory(savedPatient));

        IdentityDocument identityDocument = identityDocumentDtoConverter
                .convertIdentityDocomentFromNewIdentityDocumentDto(newPatientDto.identityDocument());
        identityDocument.setUserHistory(userHistory);
        identityDocumentService.save(identityDocument);

        Invite invite = inviteService.save(savedPatient.getEmail());
        messageService.sendInviteMessage(invite);
        return patientDtoConverter.entityToPatientDto(savedPatient);
    }

    @Override
    public List<PatientDto> getPatientByParameters(String firstName,
                                                   String lastName,
                                                   Gender gender,
                                                   String snils,
                                                   String polisNumber) {
        return patientDtoConverter.entityToListPatientDto(
                patientService.getPatientByParameters(firstName, lastName, gender, snils, polisNumber));
    }
}
