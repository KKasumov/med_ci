package com.kasumov.med_ci.model.dto.medical.talon.converter;

import com.kasumov.med_ci.model.dto.medical.talon.TalonsByDaysDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithTalonsByDaysDto;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForTalonDto;
import com.kasumov.med_ci.model.dto.user.doctor.converter.DoctorDtoConverter;
import com.kasumov.med_ci.model.dto.user.patient.PatientForTalonDto;
import com.kasumov.med_ci.model.dto.user.patient.converter.PatientDtoConverter;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.repository.user.DoctorRepository;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TalonDtoConverter {

    private final DateConvertor dateConvertor;
    private final DoctorDtoConverter doctorDtoConverter;
    private final PatientDtoConverter patientDtoConverter;
    private final DoctorRepository doctorRep;

    public List<TalonsByDaysDto> talonsByDaysDtoList(Map<LocalDate, List<Talon>> talonMap, Doctor doctor) {
        List<LocalDate> localDateList = talonMap.keySet()
                .stream()
                .sorted(Comparator.comparingInt(LocalDate::getDayOfYear))
                .toList();
        return localDateList.stream()
                .map(talon -> TalonsByDaysDto.builder()
                        .day(dateConvertor.localDateToString(talon))
                        .talons(listTalonToListTalonDto(talonMap.get(talon), doctor))
                        .build())
                .toList();
    }

    public List<TalonDto> listTalonToListTalonDto(List<Talon> talonList, Doctor doctor) {
        return talonList.stream()
                .map(talon ->
                        TalonDto.builder()
                                .id(talon.getId())
                                .time(dateConvertor.localDateTimeToString(talon.getTime()))
                                .doctor(doctorDtoConverter.doctorToDoctorForTalonDto(doctor))
                                .patient(getPatientForTalonDto(talon))
                                .build())
                .toList();
    }

    public TalonDto talonToTalonDto(Talon talon) {
        return TalonDto.builder()
                .id(talon.getId())
                .time(dateConvertor.localDateTimeToString(talon.getTime()))
                .doctor(getDoctorForTalonDro(talon))
                .patient(getPatientForTalonDto(talon))
                .build();
    }

    private PatientForTalonDto getPatientForTalonDto(Talon talon) {
        return talon.getPatientHistory() == null ? null : patientDtoConverter
                .patientToPatientForTalonDto(talon.getPatientHistory().getPatient());
    }

    private DoctorForTalonDto getDoctorForTalonDro(Talon talon) {
        return doctorDtoConverter
                .doctorToDoctorForTalonDto(doctorRep.findById(talon.getDoctorHistory().getEmployee().getId()).get());
    }

    public TalonDto toTalonDto(Talon talon, DoctorForTalonDto doctor, Patient patient){
        return TalonDto.builder()
                .id(talon.getId())
                .time(dateConvertor.localDateTimeToString(talon.getTime()))
                .doctor(doctorDtoConverter.employeeToDoctorForTalonDto(doctor))
                .patient(patientDtoConverter.patientToPatientForTalonDto(patient))
                .build();
    }

    public List<DepartmentWithTalonsByDaysDto> toListDepartmentWithTalonsByDaysDto(List<TalonsByDaysDto> talonList, long departamentId, AgeType ageType){
        return talonList.stream()
                .map(talon ->
                        DepartmentWithTalonsByDaysDto.builder()
                                .id(departamentId)
                                .ageType(ageType)
                                .days(talonList)
                                .build())
                .toList();

    }

    public List<TalonDto> patientTalonsAssignedToTalonDTO(List<Talon> talonList) {
        return talonList.stream()
                .map(talon ->
                        TalonDto.builder()
                                .id(talon.getId())
                                .time(dateConvertor.localDateTimeToString(talon.getTime()))
                                .build())
                .toList();
    }

}
