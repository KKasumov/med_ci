package com.kasumov.med_ci.model.dto.user.doctor.converter;

import com.kasumov.med_ci.model.dto.user.attestation.AttestationDto;
import com.kasumov.med_ci.model.dto.user.attestation.AttestationDtoAllDoctorByDateto;
import com.kasumov.med_ci.model.dto.user.contact.converter.ContactDtoConverter;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorDto;
import com.kasumov.med_ci.model.dto.medical.visit.VisitMedicalServiceNativeDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForPatientDto;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForTalonDto;
import com.kasumov.med_ci.model.dto.user.doctor.CurrentDoctorDto;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForRegistrarDto;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForVisitNativeDto;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.model.entity.user.items.Attestation;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.service.entity.user.AttestationService;
import com.kasumov.med_ci.util.DateConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DoctorDtoConverter {

    private final DateConvertor dateConvertor;
    private final ContactDtoConverter contactDtoConverter;
    private final AttestationService attestationService;

    public List<DoctorForPatientDto> doctorToListDoctorDto(List<Doctor> doctorList) {
        return doctorList.stream().map(doctor ->
                        DoctorForPatientDto.builder()
                                .id(doctor.getId())
                                .firstName(doctor.getFirstName())
                                .lastName(doctor.getLastName())
                                .patronymic(doctor.getPatronymic())
                                .gender(doctor.getGender())
                                .build())
                .toList();
    }

    public DoctorForTalonDto doctorToDoctorForTalonDto(Doctor doctor) {
        return DoctorForTalonDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .patronymic(doctor.getPatronymic())
                .build();
    }

    public CurrentDoctorDto entityToCurrentDoctorDto(Doctor entity) {
        return CurrentDoctorDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .patronymic(entity.getPatronymic())
                .birthday(dateConvertor.localDateToString(entity.getBirthday()))
                .gender(entity.getGender())
                .build();
    }

    public DoctorForTalonDto entityToDoctorForTalonDto(Employee entity) {
        return DoctorForTalonDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .patronymic(entity.getPatronymic())
                .build();
    }

    public DoctorForTalonDto employeeToDoctorForTalonDto(DoctorForTalonDto employee) {
        return DoctorForTalonDto.builder()
                .id(employee.id())
                .firstName(employee.firstName())
                .lastName(employee.lastName())
                .patronymic(employee.patronymic())
                .build();
    }

    public List<DoctorForRegistrarDto> convertorDoctorsForRegistrarMapToDto(
            Map<Doctor, List<Contact>> doctorsAndContactsMap) {
        List<Doctor> doctorList = doctorsAndContactsMap.keySet()
                .stream()
                .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                .toList();
        return doctorList.stream()
                .map(doctor ->
                        DoctorForRegistrarDto.builder()
                                .id(doctor.getId())
                                .firstName(doctor.getFirstName())
                                .lastName(doctor.getLastName())
                                .patronymic(doctor.getPatronymic())
                                .gender(doctor.getGender())
                                .contacts(contactDtoConverter.convertorListContactToContactDto(
                                        doctorsAndContactsMap.get(doctor)))
                                .build())
                .toList();
    }

    public DoctorDto convertDoctorToDoctorDto(Doctor entity) {
        if (entity == null) {
            return null;
        }
        return DoctorDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .patronymic(entity.getPatronymic())
                .birthday(dateConvertor.localDateToString(entity.getBirthday()))
                .gender(entity.getGender())
                .build();
    }

    public DoctorForVisitNativeDto convertDataToDoctorDto(VisitMedicalServiceNativeDto visit) {
        return DoctorForVisitNativeDto.builder()
                .id(visit.getDocId())
                .firstName(visit.getDocFirstName())
                .lastName(visit.getDocLastName())
                .patronymic(visit.getDocPatronymic())
                .build();
    }

    public List<AttestationDto> convertSetAttestationDtoToListAttestationDto(Set<Attestation> attestations) {
        return attestations.stream()
                .sorted((x,y) -> (int) (x.getId() - y.getId()))
                .map(attestation -> AttestationDto.builder()
                        .dateFrom(dateConvertor.localDateToString(attestation.getDateFrom()))
                        .dateEnd(dateConvertor.localDateToString(attestation.getDateTo()))
                        .number(attestation.getNumber())
                        .build())
                .toList();
    }

    public List<DoctorDto> convertDoctorToDoctorDTO(List<Doctor> doctors) {
        return doctors.stream().map(doctor ->
                DoctorDto.builder()
                        .id(doctor.getId())
                        .firstName(doctor.getFirstName())
                        .lastName(doctor.getLastName())
                        .patronymic(doctor.getPatronymic())
                        .birthday(dateConvertor.localDateToString(doctor.getBirthday()))
                        .gender(doctor.getGender())
                        .attestationDto(convertSetAttestationDtoToListAttestationDto(attestationService.getAttestationByDoctorId(doctor.getId())))
                        .build())
                .toList();
    }

    public AttestationDtoAllDoctorByDateto
        covertDoctorDtoByDoctorEndAttestation (List<Doctor> doctors, Department department) {
        return AttestationDtoAllDoctorByDateto.builder()
                .doctorDto(convertDoctorToDoctorDTO(doctors))
                .departmentDto(DepartmentDto.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .ageType(department.getAgeType())
                        .build())
                .build();
    }
    public AttestationDtoAllDoctorByDateto
    covertDoctorDtoByDoctorEndAttestationNoId (List<Doctor> doctors) {
        return AttestationDtoAllDoctorByDateto.builder()
                .doctorDto(convertDoctorToDoctorDTO(doctors))
                .build();
    }
}
