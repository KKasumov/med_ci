package com.kasumov.med_ci.model.dto.medical.appeal.converter;

import com.kasumov.med_ci.model.dto.medical.disease.DiseaseDto;
import com.kasumov.med_ci.model.dto.user.doctor.converter.DoctorDtoConverter;
import com.kasumov.med_ci.model.entity.economic.Yet;
import com.kasumov.med_ci.model.entity.economic.price.PayPriceOfMedicalService;
import com.kasumov.med_ci.model.entity.medical.Appeal;
import com.kasumov.med_ci.model.entity.medical.Disease;
import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.model.entity.medical.Visit;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.enums.InsuranceType;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.medical.appeal.AppealForPatientNativeFullDto;
import com.kasumov.med_ci.model.dto.medical.disease.converter.DiseaseDtoConverter;
import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDtoNativeRec;
import com.kasumov.med_ci.model.dto.medical.visit.VisitMedicalServiceNativeDto;
import com.kasumov.med_ci.model.dto.medical.visit.VisitMedicalServiceNativeDtoRec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AppealDtoConverter {
    private final DateConvertor dateConvertor;
    private final DiseaseDtoConverter diseaseDtoConverter;
    private final DoctorDtoConverter doctorDtoConverter;

    public AppealForPatientNativeFullDto appealToAppealForPatientFullDto(Appeal appeal, List<VisitMedicalServiceNativeDto> visits) {
        return AppealForPatientNativeFullDto.builder()
                .id(appeal.getId())
                .closedDate(dateConvertor.localDateTimeToString(appeal.getClosedDate().atStartOfDay()))
                .insuranceType(appeal.getInsuranceType())
                .disease(getDiseaseDto(appeal))
                .visits(getVisits(visits))
                .money(getMoneyForAppeal(appeal).setScale(2, RoundingMode.DOWN))
                .build();
    }

    private DiseaseDto getDiseaseDto(Appeal appeal) {
        Disease disease = appeal.getDisease();
        return Optional.ofNullable(disease)
                .map(diseaseDtoConverter::diseaseToDiseaseDto)
                .orElse(null);
    }

    private List<VisitMedicalServiceNativeDtoRec> getVisits(List<VisitMedicalServiceNativeDto> visits) {
        return visits.stream()
                .map(visit -> VisitMedicalServiceNativeDtoRec.builder()
                        .id(visit.getVisitId())
                        .dayOfVisit(dateConvertor.localDateTimeToString(visit.getDayOfVisit().atStartOfDay()))
                        .doctor(doctorDtoConverter.convertDataToDoctorDto(visit))
                        .medicalServices(getMedService(visit.getMedicalServiceDtoNative().toString()))
                        .money(visit.getVisitTotalPrice().setScale(2, RoundingMode.DOWN))
                        .build())
                .sorted(Comparator.comparingLong(VisitMedicalServiceNativeDtoRec::id))
                .toList();
    }

    private List<MedicalServiceDtoNativeRec> getMedService(String medicalServicesStr) {
        return Arrays.stream(medicalServicesStr.split("[\\[\\]|]"))
                .filter(s -> !s.isEmpty())
                .map(fragment -> {
                    String[] values = fragment.split(",");
                    return MedicalServiceDtoNativeRec.builder()
                            .id(Long.parseLong(values[0]))
                            .identifier(values[1])
                            .name(values[2])
                            .build();
                })
                .sorted(Comparator.comparing(MedicalServiceDtoNativeRec::id))
                .toList();
    }

    private BigDecimal getMoneyForVisit(Visit visit) {
        return visit.getMedicalServices().stream()
                .map(MedicalService::getPayPriceOfMedicalServices)
                .flatMap(Set::stream)
                .filter(ppms -> isDateInRange(ppms.getDayFrom(), ppms.getDayTo(), visit.getDayOfVisit()))
                .map(PayPriceOfMedicalService::getMoneyInPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getMoneyForVisitOms(Visit visit) {
        List<MedicalService> medicalServices = visit.getMedicalServices().stream().distinct().toList();
        return medicalServices.stream()
                .map(MedicalService::getOmsPriceOfMedicalServices)
                .flatMap(Set::stream)
                .filter(opms -> isDateInRange(opms.getDayFrom(), opms.getDayTo(), visit.getDayOfVisit()))
                .map(opms -> {
                    BigDecimal yet = opms.getYet();
                    MedicalOrganization medicalOrganization = visit.getDoctorHistory().getDepartment().getMedicalOrganization();
                    BigDecimal price = getPriceForVisit(medicalOrganization.getYets(), visit.getDayOfVisit());
                    return yet.multiply(price);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean isDateInRange(LocalDate dateFrom, LocalDate dateTo, LocalDate date) {
        return (dateFrom.isBefore(date) || dateFrom.isEqual(date))
                && (dateTo == null || dateTo.isAfter(date) || dateTo.isEqual(date));
    }

    private BigDecimal getPriceForVisit(Set<Yet> yets, LocalDate visitDate) {
        return yets.stream()
                .filter(y -> isDateInRange(y.getDayFrom(), y.getDayTo(), visitDate))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No valid price found for visit"))
                .getPrice();
    }

    private BigDecimal getMoneyForAppeal(Appeal appeal) {
        Function<Visit, BigDecimal> getMoneyFunction = InsuranceType.DMS.name().equals(appeal.getInsuranceType().toString())
                ? this::getMoneyForVisit
                : this::getMoneyForVisitOms;
        return appeal.getVisits().stream()
                .map(getMoneyFunction)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
