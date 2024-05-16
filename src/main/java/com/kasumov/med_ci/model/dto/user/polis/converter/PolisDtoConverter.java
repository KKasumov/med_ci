package com.kasumov.med_ci.model.dto.user.polis.converter;

import com.kasumov.med_ci.model.dto.user.polis.NewPolisDto;
import com.kasumov.med_ci.model.entity.user.items.Polis;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.util.PolisFormat;
import lombok.RequiredArgsConstructor;
import com.kasumov.med_ci.model.dto.economic.smo.converter.SmoDtoConverter;
import com.kasumov.med_ci.model.dto.user.polis.PolisDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PolisDtoConverter {
    private final DateConvertor dateConvertor;
    private final SmoDtoConverter smoDtoConverter;
    private final PolisFormat polisFormat;

    public Polis convertPolisFromNewPolisDto(NewPolisDto dto) {
        return Polis.builder()
                .insuranceType(dto.insuranceType())
                .serial(dto.serial())
                .number(dto.number())
                .dateStart(dateConvertor.stringToLocalDate(dto.dateStart()))
                .dateEnd(dateConvertor.stringToLocalDate(dto.dateEnd()))
                .isDeprecated(false)
                .build();
    }

    public PolisDto convertPolisToPolisDto(Polis polis) {
        return PolisDto.builder()
                .id(polis.getId())
                .insuranceType(polis.getInsuranceType())
                .serial(polis.getSerial())
                .number(polis.getNumber())
                .dateStart(dateConvertor.localDateToString(polis.getDateStart()))
                .dateEnd(dateConvertor.localDateToString(polis.getDateEnd()))
                .isDeprecated(polis.isDeprecated())
                .smo(smoDtoConverter.convertSmoToSmoForPolisDto(polis.getSmo()))
                .build();
    }

    public PolisDto convertPatientPolisToPolisDto(Polis polis) {
        return PolisDto.builder()
                .id(polis.getId())
                .insuranceType(polis.getInsuranceType())
                .serial(polisFormat.formatSerial(polis.getSerial()))
                .number(polisFormat.formatNumber(polis.getNumber()))
                .dateStart(dateConvertor.localDateToString(polis.getDateStart()))
                .dateEnd(dateConvertor.localDateToString(polis.getDateEnd()))
                .isDeprecated(polis.isDeprecated())
                .smo(smoDtoConverter.convertSmoToSmoForPolisDto(polis.getSmo()))
                .build();
    }


    public static String reverseStringData(String str) {
        String str1[] = str.split("-");
        str = str1[2] + "." + str1[1] + "." + str1[0];
        return str;
    }

    private String replaceCharacters(String str, int len) {
        if (str.length() <= len) {
            len = 0;
        }
        return "#".repeat(str.length() - len) + str.substring(str.length() - len);
    }

    public PolisDto entityToPatientDto(Polis entity) {
        return PolisDto.builder()
                .id(entity.getId())
                .insuranceType(entity.getInsuranceType())
                .serial(replaceCharacters(entity.getSerial(), 2))
                .number(replaceCharacters(entity.getNumber(), 2))
                .dateStart(reverseStringData(String.valueOf(entity.getDateStart())))
                .dateEnd(reverseStringData(String.valueOf(entity.getDateEnd())))
                .isDeprecated(entity.isDeprecated())
                .smo(smoDtoConverter.toSmoForPolisDto(entity.getSmo()))
                .build();
    }
}