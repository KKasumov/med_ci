package com.kasumov.med_ci.model.dto.structure.department.converter;

import com.kasumov.med_ci.model.dto.user.contact.converter.ContactDtoConverter;
import com.kasumov.med_ci.model.dto.user.doctor.converter.DoctorDtoConverter;
import com.kasumov.med_ci.model.dto.user.user.converter.UserDtoConverter;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.User;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.model.dto.medical.talon.TalonsByDaysDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithChiefDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithDoctorsForPatientDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithDoctorsForRegistrarDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithTalonsByDaysDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DepartmentDtoConverter {

    private final DoctorDtoConverter doctorDtoConverter;
    private final ContactDtoConverter contactDtoConverter;
    private final UserDtoConverter userDtoConverter;


    public List<DepartmentWithDoctorsForPatientDto> departmentsToListDepartmentsDto(
            Map<Department, List<Doctor>> departmentsAndDoctorsMap) {
        List<Department> departmentList = departmentsAndDoctorsMap.keySet().stream()
                .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                .toList();
        return departmentList
                .stream()
                .filter(department -> !departmentsAndDoctorsMap.get(department).isEmpty())
                .map(department -> DepartmentWithDoctorsForPatientDto.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .ageType(department.getAgeType())
                        .doctors(doctorDtoConverter.doctorToListDoctorDto(departmentsAndDoctorsMap.get(department)))
                        .build())
                .toList();
    }

    public DepartmentDto convertDepartmentToDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .ageType(department.getAgeType())
                .build();
    }

    public List<DepartmentWithDoctorsForRegistrarDto> convertorDepartmentsAndDoctorsListToDto(
            Map<Department, Map<Doctor, List<Contact>>> departmentsAndDoctorsAndContactsMap) {
        List<Department> departmentList = departmentsAndDoctorsAndContactsMap.keySet()
                .stream()
                .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                .toList();
        return departmentList
                .stream()
                .map(department -> DepartmentWithDoctorsForRegistrarDto.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .ageType(department.getAgeType())
                        .chiefDoctorFirstName(Optional.ofNullable(department.getIoChiefDoctor())
                                .map(User::getFirstName)
                                .or(() -> Optional.ofNullable(department.getChiefDoctor())
                                        .map(User::getFirstName))
                                .orElse(null))
                        .chiefDoctorLastName(Optional.ofNullable(department.getIoChiefDoctor())
                                .map(User::getLastName)
                                .or(() -> Optional.ofNullable(department.getChiefDoctor())
                                        .map(User::getLastName))
                                .orElse(null))
                        .chiefContacts(contactDtoConverter.convertorListContactToContactDto(
                                departmentsAndDoctorsAndContactsMap.get(department)
                                        .get(Optional.ofNullable(department.getIoChiefDoctor())
                                                        .or(() -> Optional.ofNullable(department.getChiefDoctor()))
                                                        .orElse(null))))
                        .doctors(doctorDtoConverter.convertorDoctorsForRegistrarMapToDto(
                                departmentsAndDoctorsAndContactsMap.get(department)))
                        .build())
                .toList();
    }

    public List<DepartmentWithChiefDto> convertToDepartmentWithChiefDtoList(Set<Department> departmentList) {
        return departmentList.stream()
                .sorted((x, y) -> (int) (x.getId() - y.getId()))
                .map(department -> DepartmentWithChiefDto.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .ageType(department.getAgeType())
                        .chiefDoctor(userDtoConverter.convertUserToUserDto(department.getChiefDoctor()))
                        .ioChiefDoctor(userDtoConverter.convertUserToUserDto(department.getIoChiefDoctor()))
                        .build())
                .toList();
    }
    public DepartmentWithChiefDto convertToDepartmentWithChiefDto(Department department) {
        return DepartmentWithChiefDto.builder()
                .id(department.getId())
                .name(department.getName())
                .ageType(department.getAgeType())
                .chiefDoctor(userDtoConverter.convertUserToUserDto(department.getChiefDoctor()))
                .ioChiefDoctor(userDtoConverter.convertUserToUserDto(department.getIoChiefDoctor()))
                .build();
    }

    public DepartmentDto convertDepartmentEntityToDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .ageType(department.getAgeType())
                .build();
    }

    public DepartmentWithTalonsByDaysDto convertToDto(Department department, List<TalonsByDaysDto> talonList) {
        return DepartmentWithTalonsByDaysDto.builder()
                .id(department.getId())
                .name(department.getName())
                .ageType(department.getAgeType())
                .days(talonList)
                .build();
    }

}
