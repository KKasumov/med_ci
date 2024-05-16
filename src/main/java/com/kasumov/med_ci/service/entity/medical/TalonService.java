package com.kasumov.med_ci.service.entity.medical;

import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.AgeType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TalonService {

    List<Talon> getTalonsByDoctorHistoryForToday(long doctorHistoryId);

    List<Talon> findByDepartmentIdAndDateTime(long departmentId, LocalDateTime start, LocalDateTime end);

    void deleteFreeTalonsByDoctorInCertainTime(LocalDateTime timeStart, LocalDateTime timeEnd, long doctorId);

    List<Talon> getAllTalonsByDoctorCertainTime(LocalDateTime timeStart, LocalDateTime timeEnd, long doctorId);

    Talon getTalonByIdAndDoctorIdWithDoctor(Long talonId, Long doctorId);

    void deleteTalon(Talon talon);

    List<Talon> getAllTalons(long id);

    Talon getTalonByIdAndDoctorId(Long talonId, Long doctorId);

    void distributePatientByDepartmentAndTalon(long departmentId, List<Talon> talons);

    List<Talon> getTalonByDoctorCertainTimeWithPatient(LocalDateTime timeStart, LocalDateTime timeEnd, long doctorId);

    void save(Talon talon);

    List<Talon> findFreeTalonByDepartmentIdAndDateTime(long departmentId, LocalDateTime start, LocalDateTime end);

    Talon getNearestTalonForPatient(long departmentId);

    Talon saveTalon(Talon talon, Patient patient);

    Optional<Talon> findById(Long talonId);

    Talon findTalonByPatientHistoryAndDepartmentAndDoctor(long talonId);
    Integer deleteTalonsByTime(LocalDateTime time);

    void deleteTalonById(Long talonId);

    void updateTalonPatientHistory(Long patientHistoryId, Long talonId);

    Talon getTalonById(long talonId);

    List<Talon> getTalonByDoctor(Long doctorId, LocalDateTime dateStart,
                                 LocalDateTime dateEnd, boolean isFree);

    List<Talon> getTalonByDepartment(Long departmentId,
                                     LocalDateTime dateStart, LocalDateTime dateEnd,
                                     AgeType ageType, AgeType adult,
                                     AgeType child, boolean isFree);

    List<Long> getCountByDoctor(Long doctorId, LocalDateTime dateStart,
                                LocalDateTime dateEnd, boolean isFree);

    List<Long> getCountByDepart(Long departId, AgeType ADULT, AgeType CHILD,
                                AgeType ageType, Boolean isFree,
                                LocalDateTime dateStart, LocalDateTime dateEnd);

    Talon findByIdWithDocHistAndDep(Long talonId);

    List<Talon> getPatientTalonsAssigned(long patientId);

}
