package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.dto.user.employee.EmployeeOfferDto;
import com.kasumov.med_ci.model.dto.user.hrmanager.CurrentHrManagerDto;
import com.kasumov.med_ci.model.dto.user.hrmanager.converter.HrManagerDtoConverter;
import com.kasumov.med_ci.model.dto.user.identityDocument.converter.IdentityDocumentConverter;
import com.kasumov.med_ci.model.dto.user.laborContract.converter.LaborContractDtoConverter;
import com.kasumov.med_ci.repository.user.EmployeeRepository;
import com.kasumov.med_ci.service.dto.user.ContactDtoService;
import com.kasumov.med_ci.service.dto.user.HrManagerDtoService;
import com.kasumov.med_ci.util.MessageService;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.Role;
import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import com.kasumov.med_ci.model.entity.user.items.University;
import com.kasumov.med_ci.model.enums.RolesEnum;
import com.kasumov.med_ci.service.entity.user.ContactService;
import com.kasumov.med_ci.service.entity.user.DiplomaService;
import com.kasumov.med_ci.service.entity.user.DoctorHistoryService;
import com.kasumov.med_ci.service.entity.user.EmployeeHistoryService;
import com.kasumov.med_ci.service.entity.user.EmployeeService;
import com.kasumov.med_ci.service.entity.user.IdentityDocumentService;
import com.kasumov.med_ci.service.entity.user.InviteService;
import com.kasumov.med_ci.service.entity.user.LaborContractService;
import com.kasumov.med_ci.service.entity.user.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HrManagerDtoServiceImpl implements HrManagerDtoService {

    private final IdentityDocumentConverter identityDocumentConverter;
    private final HrManagerDtoConverter hrManagerDtoConverter;
    private final LaborContractDtoConverter laborContractDtoConverter;
    private final EmployeeService employeeService;
    private final DiplomaService diplomaService;
    private final DoctorHistoryService doctorHistoryService;
    private final UserHistoryService userHistoryService;
    private final IdentityDocumentService identityDocumentService;
    private final ContactService contactService;
    private final ContactDtoService contactDtoService;
    private final EmployeeHistoryService employeeHistoryService;
    private final LaborContractService laborContractService;
    private final EmployeeRepository employeeRepository;
    private final MessageService messageService;
    private final InviteService inviteService;

    @Override
    public CurrentHrManagerDto getCurrentHrManagerDto(Employee hrManager) {
        return hrManagerDtoConverter.entityToCurrentHrManagerDto(hrManager);
    }

    @Transactional
    @Override
    public EmployeeOfferDto hiringEmployee(Role role,
                                           Position position,
                                           University university,
                                           EmployeeForHiringDTO dto) {
        List<Contact> contacts = new ArrayList<>();
        Employee employee = employeeService.createEmployee(role, dto);
        UserHistory userHistory = userHistoryService.saveForEmployee(employee);
        IdentityDocument identityDocument =
                identityDocumentService.saveIdentityDocumentForOffer(userHistory, dto.identityDocumentDto());
        if (!dto.contactDtos().isEmpty()) {
            contacts = contactService.saveAll(dto.contactDtos(), userHistory);
        }
        EmployeeHistory employeeHistory = distributionEmployeeHistory(role, position, dto.email());
        Diploma diploma = diplomaService.addDiploma(dto.diplomaForHiringDto(), university);
        LaborContract laborContractForEmployeeHiring = laborContractService
                .createLaborContractForEmployeeHiring(dto, employeeHistory, diploma, position);

        messageService.sendInviteMessage(inviteService.save(dto.email()));
        return EmployeeOfferDto
                .builder()
                .laborContractDto(
                        laborContractDtoConverter.convertLaborContractToDto(laborContractForEmployeeHiring))
                .identityDocumentDto(
                        identityDocumentConverter.entityToIdentityDocumentDto(identityDocument))
                .contactDto(contactDtoService.getAllContact(contacts))
                .build();
    }

    private EmployeeHistory distributionEmployeeHistory(Role role, Position position, String email) {
       return role.getName().equals(RolesEnum.DOCTOR.name()) ?
                doctorHistory(role, position, email) : employeeHistory(position, email);
    }

    private DoctorHistory doctorHistory(Role role, Position position, String email) {
        return doctorHistoryService.saveDoctorHistory(role, position, employeeRepository.findByEmail(email));
    }

    private EmployeeHistory employeeHistory(Position position, String email) {
        return employeeHistoryService.saveEmployeeHistory(position, employeeRepository.findByEmail(email));
    }
}
