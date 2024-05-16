package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.model.dto.user.patient.PatientContactDto;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.ContactDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
@RequestMapping("/api/patient/contact")
public class PatientContactRestController {

    private final ContactDtoService contactDtoService;

    @GetMapping("/get/all")
    public Response<PatientContactDto> getAllContacts() {
        Patient patient = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(contactDtoService.getPatientContactDto(patient));
    }
}
