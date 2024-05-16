package com.kasumov.med_ci.service.entity.medical.impl;

import com.kasumov.med_ci.repository.medical.TalonRepository;
import com.kasumov.med_ci.util.MailService;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.service.entity.medical.TalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TalonServiceImpl implements TalonService {

    private final TalonRepository talonRepository;
    private final MailService mailService;

    @Override
    public List<Talon> getTalonsByDoctorHistoryForToday(long doctorHistoryId) {
        return talonRepository.getTalonsByDoctorHistoryForToday(doctorHistoryId);
    }

    @Override
    @Transactional
    public void deleteFreeTalonsByDoctorInCertainTime(LocalDateTime timeStart, LocalDateTime timeEnd, long doctorId) {
        talonRepository.deleteFreeTalonsByDoctorInCertainTime(timeStart, timeEnd, doctorId);
    }

    @Override
    public List<Talon> getAllTalonsByDoctorCertainTime(LocalDateTime timeStart, LocalDateTime timeEnd, long doctorId) {
        return talonRepository.getTalonByDoctorCertainTime(timeStart, timeEnd, doctorId);
    }

    @Override
    public List<Talon> findByDepartmentIdAndDateTime(long departmentId, LocalDateTime start, LocalDateTime end) {
        return talonRepository.findByDepartmentIdAndDateTime(departmentId, start, end);
    }

    @Override
    public Talon getTalonByIdAndDoctorIdWithDoctor(Long talonId, Long doctorId) {
        return talonRepository.getTalonByIdAndDoctorIdWithDoctor(talonId, doctorId);
    }

    @Override
    public void deleteTalon(Talon talon) {
        talonRepository.delete(talon);
    }

    @Override
    public List<Talon> getAllTalons(long id) {
        return talonRepository.findAllByPatientHistory(id);
    }

    @Override
    public Talon getTalonByIdAndDoctorId(Long talonId, Long doctorId) {
        return talonRepository.getTalonByIdAndDoctorId(talonId, doctorId);
    }

    @Override
    public void distributePatientByDepartmentAndTalon(long departmentId, List<Talon> talons) {
        for (Talon talon : talons) {
            List<Talon> freeTalons = talonRepository.findTalonByDepartmentAndTime(departmentId, talon.getTime());
            if (!freeTalons.isEmpty()) {
                freeTalons.get(0).setPatientHistory(talon.getPatientHistory());
                mailService.send(talon.getPatientHistory().getPatient().getEmail(),
                        "Запись к врачу", "Ваш талон был перенес к другому доктору");
                talonRepository.delete(talon);
            }
        }
    }

    @Override
    public List<Talon> getTalonByDoctorCertainTimeWithPatient(LocalDateTime timeStart, LocalDateTime timeEnd, long doctorId) {
        return talonRepository.getTalonByDoctorCertainTimeWithPatient(timeStart, timeEnd, doctorId);
    }

    @Override
    public void save(Talon talon) {
        talonRepository.save(talon);
    }


    @Override
    public List<Talon> findFreeTalonByDepartmentIdAndDateTime(long departmentId, LocalDateTime start, LocalDateTime end) {
        return talonRepository.findFreeTalonByDepartmentIdAndDateTime(departmentId, start, end);
    }

    @Override
    public Talon getNearestTalonForPatient(long departmentId) {
        List<Talon> talonList = findFreeTalonByDepartmentIdAndDateTime(
                departmentId,
                LocalDateTime.of(LocalDate.now(), LocalTime.now()),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
        );
        return !talonList.isEmpty() ? talonList.get(0) : null;
    }

    @Override
    public Talon saveTalon(Talon talon, Patient patient) {
        talon.setPatientHistory(patient.getPatientHistory());
        return talonRepository.save(talon);
    }

    @Override
    public Optional<Talon> findById(Long talonId) {
        return talonRepository.findById(talonId);
    }

    @Override
    public Talon findTalonByPatientHistoryAndDepartmentAndDoctor(long talonId) {
        return talonRepository.findTalonByIdWithPatientHistoryAndDepartmentAndDoctor(talonId);
    }

    @Override
    public void deleteTalonById(Long talonId) {
        talonRepository.deleteById(talonId);
    }

    @Override
    public void updateTalonPatientHistory(Long patientHistoryId, Long talonId) {
        talonRepository.updateTalonPatientHistory(patientHistoryId, talonId);
    }

    @Override
    public Talon getTalonById(long talonId) {
        Optional<Talon> talon = talonRepository.findById(talonId);
        return talon.orElse(null);
    }

    @Override
    public List<Talon> getTalonByDoctor(Long doctorId, LocalDateTime dateStart,
                                        LocalDateTime dateEnd, boolean isFree) {
        return talonRepository.getTalonByDoctor(doctorId, dateStart, dateEnd, isFree);
    }

    @Override
    public List<Talon> getTalonByDepartment(Long departmentId, LocalDateTime dateStart,
                                            LocalDateTime dateEnd, AgeType ageType,
                                            AgeType adult, AgeType child, boolean isFree) {
        return talonRepository.getTalonByDepartment(departmentId, dateStart,
                dateEnd, ageType, adult, child, isFree);
    }

    @Override
    @Transactional
    public Integer deleteTalonsByTime(LocalDateTime time) {
        return talonRepository.deleteTalonsByTime(time);
    }


    @Override
    public List<Long> getCountByDoctor(Long doctorId, LocalDateTime dateStart,
                                       LocalDateTime dateEnd, boolean isFree) {
        return talonRepository.getCountByDoctor(doctorId, dateStart, dateEnd, isFree);
    }

    @Override
    public List<Long> getCountByDepart(Long departId, AgeType ADULT, AgeType CHILD,
                                       AgeType ageType, Boolean isFee,
                                       LocalDateTime dateStart, LocalDateTime dateEnd) {
        return talonRepository.getCountByDepart(departId, ADULT, CHILD, ageType, isFee, dateStart, dateEnd);
    }

    @Override
    public Talon findByIdWithDocHistAndDep(Long talonId) {
        return talonRepository.findByIdWithDocHistAndDep(talonId);
    }


    @Override
    public List<Talon> getPatientTalonsAssigned(long patientId) {
        return talonRepository.findPatientTalonsAssigned(patientId);
    }
}
