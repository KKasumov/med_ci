package com.kasumov.med_ci.service.dto.medical.impl;

import com.kasumov.med_ci.model.dto.user.doctor.DoctorForTalonDto;
import com.kasumov.med_ci.model.dto.user.doctor.converter.DoctorDtoConverter;
import com.kasumov.med_ci.model.dto.user.patient.PatientForTalonDto;
import com.kasumov.med_ci.model.dto.user.patient.converter.PatientDtoConverter;
import com.kasumov.med_ci.repository.medical.TalonRepository;
import com.kasumov.med_ci.service.entity.medical.TalonService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.util.MessageService;
import com.kasumov.med_ci.model.dto.medical.talon.TalonDto;
import com.kasumov.med_ci.model.dto.medical.talon.TalonsByDaysDto;
import com.kasumov.med_ci.model.dto.medical.talon.converter.TalonDtoConverter;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithTalonsByDaysDto;
import com.kasumov.med_ci.model.dto.structure.department.converter.DepartmentDtoConverter;
import com.kasumov.med_ci.model.dto.utill.converter.MessageDtoConverter;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import com.kasumov.med_ci.model.entity.user.history.PatientHistory;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.service.dto.medical.TalonDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TalonDtoServiceImpl implements TalonDtoService {

    private final DateConvertor dateConvertor;
    private final DoctorDtoConverter doctorDtoConverter;
    private final PatientDtoConverter patientDtoConverter;
    private final TalonService talonService;
    private final TalonRepository talonRepository;
    private final TalonDtoConverter talonDtoConverter;
    private final MessageDtoConverter messageDtoConverter;
    private final MessageService messageService;
    private final DepartmentDtoConverter departmentDtoConverter;

    @Value("${patientScope}")
    private int patientScope;

    @Value("${doctorScope}")
    private int doctorScope;

    @Value("${talonsOnDay}")
    private int talonsOnDay;

    @Override
    public List<TalonsByDaysDto> getTalonsByDaysAndDoctor(String dateStart, String dateEnd, Doctor doctor) {

        LocalDateTime start = dateStart == null ? LocalDateTime.of(LocalDate.now(), LocalTime.MIN) :
                dateConvertor.stringToLocalDateTime(dateStart);
        LocalDateTime end = dateEnd == null ? LocalDateTime.of(LocalDate.now().plusDays(doctorScope), LocalTime.MAX) :
                dateConvertor.stringToLocalDateTime(dateEnd);
        return groupByDay(
                createTalonsDto(
                        talonRepository.findByDoctorHistoryAndDateBetween(doctor.getId(), start, end)));
    }

    public List<TalonsByDaysDto> groupByDay(List<TalonDto> talons) {
        Map<String, List<TalonDto>> map = new HashMap<>();
        for (TalonDto talon : talons) {
            LocalDateTime talonTime = dateConvertor.stringToLocalDateTime(talon.time());
            LocalDate talonDay = talonTime.toLocalDate();
            String day = dateConvertor.localDateToString(talonDay);
            if (map.containsKey(day)) {
                List<TalonDto> list = new ArrayList<>(map.get(day));
                list.add(talon);
                map.put(day, list);
            } else {
                map.put(day, List.of(talon));
            }
        }

        return map.keySet().stream()
                .map(day -> new TalonsByDaysDto(day, map.get(day)))
                .sorted(Comparator.comparing(o -> dateConvertor.stringToLocalDate(o.day())))
                .toList();
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
    public TalonsByDaysDto getCurrentDoctorTodayTalons(Doctor doctor) {

        List<Talon> talons = talonService.getTalonsByDoctorHistoryForToday(doctor.getDoctorHistory().getId());
        return TalonsByDaysDto.builder()
                .day(dateConvertor.localDateToString(LocalDate.now()))
                .talons(talonDtoConverter.listTalonToListTalonDto(talons, doctor))
                .build();
    }

    @Override
    public List<TalonsByDaysDto> deleteTalonsCertainTime(String dateStart, String dateEnd, Doctor doctor) {
        LocalDateTime localDateTimeStart = dateStart == null ?
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN) :
                dateConvertor.stringToLocalDateTime(dateStart);
        LocalDateTime localDateTimeEnd = dateEnd == null ?
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(doctorScope) :
                dateConvertor.stringToLocalDateTime(dateEnd);
        talonService.deleteFreeTalonsByDoctorInCertainTime(localDateTimeStart, localDateTimeEnd, doctor.getId());
        List<Talon> undeletedDoctorTalons =
                talonService.getAllTalonsByDoctorCertainTime(localDateTimeStart, localDateTimeEnd, doctor.getId());
        return talonDtoConverter.talonsByDaysDtoList(createMapTalons(undeletedDoctorTalons), doctor);
    }

    private Map<LocalDate, List<Talon>> createMapTalons(List<Talon> talonList) {
        Map<LocalDate, List<Talon>> talonsMap = new HashMap<>();
        for (Talon talon : talonList) {
            LocalDate talonTime = talon.getTime().toLocalDate();
            if (talonsMap.containsKey(talonTime)) {
                List<Talon> talons = new ArrayList<>(talonsMap.get(talonTime));
                talons.add(talon);
                talonsMap.put(talonTime, talons);
            } else {
                talonsMap.put(talonTime, List.of(talon));
            }
        }
        return talonsMap;
    }

    @Override
    public List<TalonDto> getPatientTalons(long patientId) {
        return talonService.getAllTalons(patientId).stream()
                .map(talonDtoConverter::talonToTalonDto)
                .toList();
    }

    @Override
    public TalonDto getTalonDto(Talon talon) {
        return talonDtoConverter.talonToTalonDto(talon);
    }

    @Transactional
    @Override
    public TalonDto overwritingTalon(Talon newTalon, Talon oldTalon, boolean isDeleteOldTalon) {
        PatientHistory patientHistoryForOldTalon = oldTalon.getPatientHistory();
        newTalon.setPatientHistory(patientHistoryForOldTalon);
        if (isDeleteOldTalon) {
            talonService.deleteTalonById(oldTalon.getId());
        } else {
            talonService.updateTalonPatientHistory(null, oldTalon.getId());
        }
        messageService.sendEmailPatientMessageAboutNewTalon(patientHistoryForOldTalon.getPatient(), newTalon);
        return talonDtoConverter.talonToTalonDto(newTalon);
    }


    @Override
    public List<TalonDto> distributePatientAndDeleteTalons(String dateStart, String dateEnd, Doctor doctor) {
        LocalDateTime localDateTimeStart = dateStart == null ?
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN) :
                dateConvertor.stringToLocalDateTime(dateStart);

        LocalDateTime localDateTimeEnd = dateEnd == null ?
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(doctorScope) :
                dateConvertor.stringToLocalDateTime(dateEnd);

        talonService.deleteFreeTalonsByDoctorInCertainTime(localDateTimeStart, localDateTimeEnd, doctor.getId());
        talonService.distributePatientByDepartmentAndTalon(doctor.getDoctorHistory().getDepartment().getId(),
                talonService.getAllTalonsByDoctorCertainTime(localDateTimeStart, localDateTimeEnd, doctor.getId()));

        return talonDtoConverter.listTalonToListTalonDto(talonService.getAllTalonsByDoctorCertainTime(
                localDateTimeStart, localDateTimeEnd, doctor.getId()), doctor);
    }

    @Override
    @Transactional
    public List<TalonsByDaysDto> addTalonsForDoctor(Doctor doctor, String dayStart, String dayEnd) {
        LocalDateTime localDateTime = dayStart == null ?
                LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 00)) :
                dateConvertor.stringToLocalDate(dayStart).atTime(7, 00);

        LocalDateTime localDateTimeStart = dayStart == null ?
                LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 00)) :
                dateConvertor.stringToLocalDate(dayStart).atTime(7, 00);

        LocalDateTime localDateTimeEnd = dayEnd == null ?
                LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59)).plusDays(doctorScope) :
                dateConvertor.stringToLocalDate(dayEnd).atTime(23, 59);

        Map<LocalDate, List<Talon>> mapTalons = createMapTalons(doctor.getDoctorHistory().getTalons());

        List<Talon> talon = new ArrayList<>();
        while (localDateTimeStart.isBefore(localDateTimeEnd)) {
            if (localDateTimeStart.getDayOfWeek().getValue() == 7
                || localDateTimeStart.getDayOfWeek().getValue() == 6
                || mapTalons.containsKey(localDateTimeStart.toLocalDate())) {
                localDateTimeStart = localDateTimeStart.plusDays(1);
            } else {
                for (int i = 0; i < talonsOnDay; i++) {
                    talon.add(Talon.builder()
                            .time(localDateTimeStart)
                            .doctorHistory(doctor.getDoctorHistory())
                            .build());
                    localDateTimeStart = localDateTimeStart.plusHours(1);
                }
                localDateTimeStart = LocalDateTime.of(
                        localDateTimeStart.toLocalDate().plusDays(1), LocalTime.of(7, 00));
            }
        }
        talonRepository.saveAll(talon);
        List<Talon> listDoctorTalons =
                talonService.getTalonByDoctorCertainTimeWithPatient(localDateTime, localDateTimeEnd, doctor.getId());
        return talonDtoConverter.talonsByDaysDtoList(createMapTalons(listDoctorTalons), doctor);
    }

    @Override
    public TalonDto bookPatientForAppointment(Talon talon, Patient patient) {
        talonService.saveTalon(talon, patient);
        TalonDto talonForPatient = talonDtoConverter.talonToTalonDto(talon);
        messageService.sendAppointmentMessage(patient);
        return talonForPatient;
    }

    @Override
    @Transactional
    public TalonDto assignPatientToFreeTalonDoctor(Talon talon, Patient patient, Department department) {
        talon.setPatientHistory(patient.getPatientHistory());
        messageService.sendEmailPatientMessage(
                patient, messageDtoConverter.messageForPatientToAssignFreeTalon(department, talon));
        return talonDtoConverter.talonToTalonDto(talon);
    }

    @Override
    @Transactional
    public TalonDto registerPatientForFreeTicket(Talon talon, Patient patient) {
        talonService.saveTalon(talon, patient);
        messageService.sendAppointmentMessage(patient);
        return talonDtoConverter.talonToTalonDto(talon);
    }

    @Override
    public List<DepartmentWithTalonsByDaysDto> getTalonsByParameters(Long departmentId, Long doctorId,
                                                                     String dateStart, String dateEnd,
                                                                     AgeType ageType, boolean isFree) {
        List<DepartmentWithTalonsByDaysDto> department = new ArrayList<>();
        int size = 0;
        LocalDateTime start = startDate(dateStart);
        LocalDateTime end = endDate(departmentId, dateEnd);
        List<Long> countByDoctor = talonService.getCountByDoctor(doctorId, start, end, isFree);
        List<Long> countTalonByDepartment = talonService.getCountByDepart(
                departmentId, AgeType.ADULT,
                AgeType.CHILD, ageType, isFree, start, end);
        if (countByDoctor.size() > 0) {
            department.add(talonByDoctor(doctorId, start, end, isFree, Math.toIntExact(countByDoctor.get(0))));
        } else if (countTalonByDepartment.size() > 0) {
            int saveCount = 0;
            size = countTalonByDepartment.size();
            HashMap<Integer, Long> countHashMap = countHashMap(size, countTalonByDepartment);
            for (Map.Entry<Integer, Long> set : countHashMap.entrySet()) {
                if (saveCount == 0) {
                    department.add(getDepartByDto(departmentId, doctorId, start, end, ageType, isFree,
                            Math.toIntExact(set.getValue()), saveCount));
                    saveCount = Math.toIntExact(set.getValue());
                } else {
                    saveCount = Math.toIntExact(set.getValue());
                    department.add(getDepartByDto(departmentId, doctorId, start, end, ageType, isFree,
                            Math.toIntExact(set.getValue()), saveCount));
                }
            }
        }
        return department;
    }


    private DepartmentWithTalonsByDaysDto getDepartByDto(Long departmentId, Long doctorId,
                                                         LocalDateTime dateStart, LocalDateTime dateEnd,
                                                         AgeType ageType, boolean isFree,
                                                         int count, int saveCount) {
        List<Talon> getTalonByParameters = null;
        if (doctorId != null) {
            getTalonByParameters = talonService.getTalonByDoctor(doctorId, dateStart, dateEnd, isFree);
        } else {
            getTalonByParameters = talonService.getTalonByDepartment(departmentId, dateStart, dateEnd, ageType,
                    AgeType.ADULT, AgeType.CHILD, isFree);
        }
        List<TalonDto> getAllTalonDto = getAllTalonDto((long) count, getTalonByParameters, saveCount);
        return departmentDtoConverter.convertToDto(
                talonService
                        .findByIdWithDocHistAndDep(getAllTalonDto.get(saveCount).id())
                        .getDoctorHistory().getDepartment(), groupByDay(getAllTalonDto));
    }

    private DepartmentWithTalonsByDaysDto talonByDoctor(Long doctorId, LocalDateTime dateStart,
                                                        LocalDateTime dateEnd, boolean isFree, int count) {
        List<Talon> getTalonByParameters = talonService.getTalonByDoctor(doctorId, dateStart, dateEnd, isFree);
        List<TalonDto> getAllTalonDto = getAllTalonByDoctor(getTalonByParameters, count);
        return departmentDtoConverter.convertToDto(
                talonService.findByIdWithDocHistAndDep(getAllTalonDto.get(count - 1).id())
                        .getDoctorHistory().getDepartment(), groupByDay(getAllTalonDto));
    }

    private LocalDateTime startDate(String dateStart) {
        return dateStart == null ? LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                : dateConvertor.stringToLocalDateTime(dateStart);
    }

    private LocalDateTime endDate(Long departmentId, String dateEnd) {
        return departmentId == null ? checkScope(patientScope, dateEnd)
                : checkScope(doctorScope, dateEnd);
    }

    private HashMap<Integer, Long> countHashMap(int size, List<Long> countTalonByDepartment) {
        HashMap<Integer, Long> longLongHashMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            longLongHashMap.put(i, countTalonByDepartment.get(i));
        }
        return longLongHashMap;
    }

    private LocalDateTime checkScope(int scope, String dateEnd) {
        return dateEnd == null ? LocalDateTime.of(LocalDate.now().plusDays(scope), LocalTime.MAX) :
                dateConvertor.stringToLocalDateTime(dateEnd);
    }

    private List<TalonDto> getAllTalonByDoctor(List<Talon> talons, int id) {
        List<TalonDto> talonDtos = new ArrayList<>();
        if (id != 0) {
            for (Talon talon : talons) {
                talonDtos.add(talonDtoConverter.talonToTalonDto(talon));
            }
        }
        return talonDtos;
    }

    private List<TalonDto> getAllTalonDto(Long count, List<Talon> talons, int saveCount) {
        List<TalonDto> talonDtos = new ArrayList<>();
        if (saveCount == 0) {
            for (int i = 0; i < talons.size(); i++) {
                if (i != count + 1) {
                    talonDtos.add(
                            talonDtoConverter.talonToTalonDto(talons.get(i))
                    );
                } else {
                    i = talons.size();
                }
            }
        } else {
            for (int i = saveCount; i < talons.size(); i++) {
                if (i != count + saveCount + 1) {
                    talonDtos.add(
                            talonDtoConverter.talonToTalonDto(talons.get(i))
                    );
                } else {
                    i = talons.size();
                }
            }
        }
        return talonDtos;
    }

    @Override
    public List<TalonDto> findPatientTalonsAssigned(long patientId) {
        return talonDtoConverter.patientTalonsAssignedToTalonDTO(talonService.getPatientTalonsAssigned(patientId));
    }
}
