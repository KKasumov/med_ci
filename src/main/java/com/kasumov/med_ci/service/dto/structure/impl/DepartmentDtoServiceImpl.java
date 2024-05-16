package com.kasumov.med_ci.service.dto.structure.impl;

import com.kasumov.med_ci.model.dto.user.attestation.AttestationDtoAllDoctorByDateto;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForTalonDto;
import com.kasumov.med_ci.model.dto.user.doctor.converter.DoctorDtoConverter;
import com.kasumov.med_ci.model.dto.user.patient.PatientForTalonDto;
import com.kasumov.med_ci.model.dto.user.patient.converter.PatientDtoConverter;
import com.kasumov.med_ci.service.entity.medical.TalonService;
import com.kasumov.med_ci.service.entity.structure.DepartmentService;
import com.kasumov.med_ci.service.entity.user.ContactService;
import com.kasumov.med_ci.service.entity.user.DoctorService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithChiefDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithDoctorsForPatientDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithDoctorsForRegistrarDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithPositionDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithTalonsDto;
import com.kasumov.med_ci.model.dto.structure.department.NewDepartmentDto;
import com.kasumov.med_ci.model.dto.structure.department.converter.DepartmentDtoConverter;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import com.kasumov.med_ci.model.entity.user.history.PatientHistory;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.service.dto.structure.DepartmentDtoService;
import com.kasumov.med_ci.service.dto.structure.PositionDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DepartmentDtoServiceImpl implements DepartmentDtoService {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final TalonService talonService;
    private final ContactService contactService;
    private final DateConvertor dateConvertor;
    private final DoctorDtoConverter doctorDtoConverter;
    private final PatientDtoConverter patientDtoConverter;
    private final DepartmentDtoConverter departmentDtoConverter;
    private final PositionDtoService positionDtoService;

    @Override
    public List<DepartmentWithDoctorsForPatientDto> getDepartmentsSuitableForAge(Patient patient) {
        List<Doctor> doctors = doctorService.getFreeDoctorsForPatient(patient);
        Map<Department, List<Doctor>> departmentsAndDoctorsMap = createDepartmentsAndDoctorsMap(doctors);
        return departmentDtoConverter.departmentsToListDepartmentsDto(departmentsAndDoctorsMap);
    }

    @Override
    public List<DepartmentWithTalonsDto> getDepartmentWithTalonsOnTodayDto() {
        List<Department> departmentList = departmentService.findMedicalDepartments();
        Map<Department, List<Talon>> departmentMap = createDepartmentMap(departmentList);
        return departmentList.stream()
                .map(department -> DepartmentWithTalonsDto.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .ageType(department.getAgeType())
                        .chiefDoctorFirstName(getChiefFirstName(department))
                        .chiefDoctorLastName(getChiefLastName(department))
                        .talons(createTalonsDto(departmentMap.get(department)))
                        .build())
                .toList();
    }

    @Override
    public List<DepartmentWithDoctorsForRegistrarDto> getDepartmentsAndDoctorsDto() {
        return departmentDtoConverter
                .convertorDepartmentsAndDoctorsListToDto(
                        createDepartmentsAndDoctorsContactsMap(
                                doctorService.getDoctorsWithDepartmentsByAgeType()));
    }

    @Override
    public List<DepartmentDto> getMedicalDepartmentsByParameters(AgeType ageType, String pattern) {
        return departmentService.getMedicalDepartmentsByParameters(ageType, pattern).stream()
                .map(departmentDtoConverter::convertDepartmentToDepartmentDto)
                .toList();
    }

    private Map<Department, Map<Doctor, List<Contact>>> createDepartmentsAndDoctorsContactsMap(List<Doctor> doctors) {
        Map<Department, Map<Doctor, List<Contact>>> departmentsAndDoctorsContactsMap = new HashMap<>();
        for (Doctor doctor : doctors) {
            List<Contact> contactsPhoneAndTelegram = contactService.getPhoneAndTelegramByDoctor(doctor.getId());
            Department department = doctor.getDoctorHistory().getDepartment();
            if (departmentsAndDoctorsContactsMap.containsKey(department)) {
                Map<Doctor, List<Contact>> contactsDoctorMap =
                        new HashMap<>(departmentsAndDoctorsContactsMap.get(department));
                contactsDoctorMap.put(doctor, contactsPhoneAndTelegram);
                departmentsAndDoctorsContactsMap.put(doctor.getDoctorHistory().getDepartment(), contactsDoctorMap);
            } else {
                departmentsAndDoctorsContactsMap.put(doctor.getDoctorHistory().getDepartment(),
                        Map.of(doctor, contactsPhoneAndTelegram));
            }
        }
        return departmentsAndDoctorsContactsMap;
    }

    private Map<Department, List<Doctor>> createDepartmentsAndDoctorsMap(List<Doctor> doctorList) {
        Map<Department, List<Doctor>> departmentsMap = new HashMap<>();
        for (Doctor doctor : doctorList) {
            Department department = doctor.getDoctorHistory().getDepartment();
            if (departmentsMap.containsKey(department)) {
                List<Doctor> doctorsInDepartment = new ArrayList<>(departmentsMap.get(department));
                doctorsInDepartment.add(doctor);
                departmentsMap.put(department, doctorsInDepartment);
            } else {
                departmentsMap.put(department, List.of(doctor));
            }
        }
        return departmentsMap;
    }

    private Map<Department, List<Talon>> createDepartmentMap(List<Department> departmentList) {

        Map<Department, List<Talon>> departmentMap = new HashMap<>();
        departmentList
                .forEach(department -> departmentMap.put(department, talonService.findByDepartmentIdAndDateTime(
                        department.getId(),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX))));
        return departmentMap;
    }

    private Doctor findActualChief(Department department) {
        return department.getIoChiefDoctor() == null ? department.getChiefDoctor() : department.getIoChiefDoctor();
    }

    private String getChiefFirstName(Department department) {
        Doctor doctor = findActualChief(department);
        return doctor == null ? null : doctor.getFirstName();
    }

    private String getChiefLastName(Department department) {
        Doctor doctor = findActualChief(department);
        return doctor == null ? null : doctor.getLastName();
    }

    private List<TalonDto> createTalonsDto(List<Talon> talons) {
        return talons.stream()
                .map(talon -> TalonDto.builder()
                        .id(talon.getId())
                        .time(dateConvertor.localDateTimeToString(talon.getTime()))
                        .doctor(createDoctorDto(talon.getDoctorHistory()))
                        .patient(createPatientDto(talon.getPatientHistory()))
                        .build())
                .toList();
    }

    private DoctorForTalonDto createDoctorDto(DoctorHistory doctorHistory) {
        return doctorDtoConverter.entityToDoctorForTalonDto(doctorHistory.getEmployee());
    }

    private PatientForTalonDto createPatientDto(@Nullable PatientHistory patientHistory) {
        return patientHistory == null ?
                null : patientDtoConverter.entityToPatientForTalonDto(patientHistory.getPatient());
    }

    @Override
    public DepartmentWithChiefDto updateDepartment(DepartmentDto departmentDto) {
        Department department = departmentService.findById(departmentDto.id());
        department.setAgeType(departmentDto.ageType());
        department.setName(departmentDto.name());
        return departmentDtoConverter.convertToDepartmentWithChiefDto(
                departmentService.save(department));
    }

    @Override
    public DepartmentWithChiefDto addNewDepartment(NewDepartmentDto newDepartmentDto,
                                                   MedicalOrganization medicalOrganization) {
        return departmentDtoConverter.convertToDepartmentWithChiefDto(
                departmentService.save(
                        Department.builder()
                                .name(newDepartmentDto.name())
                                .ageType(newDepartmentDto.ageType())
                                .medicalOrganization(medicalOrganization)
                                .build()));
    }

    @Override
    public AttestationDtoAllDoctorByDateto getAttestationDtoAllDoctorByDate(Long departmentId,
                                                                            Integer day,
                                                                            Department department) {
        return department == null ? doctorDtoConverter.
                covertDoctorDtoByDoctorEndAttestationNoId(doctorService.getDoctorAllByEndAttestation(day, departmentId)) :
                doctorDtoConverter.covertDoctorDtoByDoctorEndAttestation(doctorService.getDoctorAllByEndAttestation(day,departmentId),
                        department);
    }

    @Override
    public DepartmentWithPositionDto getInformationByDepartment(Department department) {
        return DepartmentWithPositionDto.builder()
                .id(department.getId())
                .name(department.getName())
                .ageType(department.getAgeType())
                .positions(positionDtoService.getPositionDtoByDepartmentId(department.getId()))
                .build();
    }
}
