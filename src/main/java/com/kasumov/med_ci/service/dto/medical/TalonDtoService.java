package com.kasumov.med_ci.service.dto.medical;

import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import com.kasumov.med_ci.model.dto.medical.talon.TalonsByDaysDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithTalonsByDaysDto;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.AgeType;

import java.util.List;

public interface TalonDtoService {

    TalonsByDaysDto getCurrentDoctorTodayTalons(Doctor doctor);

    List<TalonsByDaysDto> getTalonsByDaysAndDoctor(String dateStart, String dateEnd, Doctor doctor);

    List<TalonsByDaysDto> deleteTalonsCertainTime(String timeStart, String timeEnd, Doctor doctor);

    List<TalonDto> getPatientTalons(long id);

    TalonDto getTalonDto(Talon talon);

    TalonDto overwritingTalon(Talon newTalon, Talon oldTalon, boolean isDeleteOldTalon);

    List<TalonDto> distributePatientAndDeleteTalons(String dateStart, String dateEnd, Doctor doctor);

    List<TalonsByDaysDto> addTalonsForDoctor(Doctor doctor, String dayStart, String dayEnd);

    TalonDto bookPatientForAppointment(Talon talon, Patient patient);

    TalonDto assignPatientToFreeTalonDoctor(Talon talon, Patient patient, Department department);
    List<DepartmentWithTalonsByDaysDto> getTalonsByParameters(Long departmentId,
                                                             Long doctorId,
                                                             String dateStart,
                                                             String dateEnd,
                                                             AgeType ageType,
                                                             boolean isFree);

    TalonDto registerPatientForFreeTicket(Talon talon, Patient patient);

    List<TalonDto> findPatientTalonsAssigned(long patientId);

}
