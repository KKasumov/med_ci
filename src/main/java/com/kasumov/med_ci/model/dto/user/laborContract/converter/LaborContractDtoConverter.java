package com.kasumov.med_ci.model.dto.user.laborContract.converter;

import com.kasumov.med_ci.model.dto.user.employeeHistory.converter.EmployeeHistoryDtoConverter;
import com.kasumov.med_ci.model.dto.structure.position.converter.PositionDtoConverter;
import com.kasumov.med_ci.model.dto.user.diploma.converter.DiplomaDtoConverter;
import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.dto.user.laborContract.LaborContractDto;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.history.EmployeeHistory;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.user.laborContract.LaborUserContractDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
public class LaborContractDtoConverter {

    private final DateConvertor dateConvertor;

    private final DiplomaDtoConverter diplomaDtoConverter;

    private final PositionDtoConverter positionDtoConverter;

    private final EmployeeHistoryDtoConverter employeeHistoryDtoConverterDto;

    public LaborContractDtoConverter(DateConvertor dateConvertor,
                                     @Lazy DiplomaDtoConverter diplomaDtoConverter,
                                     PositionDtoConverter positionDtoConverter,
                                     EmployeeHistoryDtoConverter employeeHistoryDtoConverterDto) {
        this.dateConvertor = dateConvertor;
        this.diplomaDtoConverter = diplomaDtoConverter;
        this.positionDtoConverter = positionDtoConverter;
        this.employeeHistoryDtoConverterDto = employeeHistoryDtoConverterDto;
    }

    public LaborContractDto convertLaborContractToDto(LaborContract laborContract) {
        return LaborContractDto.builder()
                .id(laborContract.getId())
                .startDate(dateConvertor.localDateToString(laborContract.getStartDate()))
                .endDate(dateConvertor.localDateToString(laborContract.getEndDate()))
                .part(laborContract.getPart())
                .employeeHistoryDto(employeeHistoryDtoConverterDto
                        .employeeHistoryConvertToDto(laborContract.getEmployeeHistory()))
                .positionDto(positionDtoConverter.convertFromEntity(laborContract.getPosition()))
                .diplomaDto(diplomaDtoConverter.convertDiplomaToDiplomaDto(laborContract.getDiploma()))
                .build();
    }

    public LaborContractDto convertLaborContractToDtoWithoutDiplomaAndAttestation(LaborContract laborContract) {
        return LaborContractDto.builder()
                .id(laborContract.getId())
                .startDate(dateConvertor.localDateToString(laborContract.getStartDate()))
                .endDate(dateConvertor.localDateToString(laborContract.getEndDate()))
                .part(laborContract.getPart())
                .employeeHistoryDto(employeeHistoryDtoConverterDto
                        .employeeHistoryConvertToDto(laborContract.getEmployeeHistory()))
                .positionDto(positionDtoConverter.convertFromEntity(laborContract.getPosition()))
                .diplomaDto(
                        diplomaDtoConverter.convertDiplomaToDiplomaDtoWithoutDiplomaAndAttestation(
                                laborContract.getDiploma()))
                .build();
    }

    public LaborUserContractDto laborContractUserToDto(LaborContract laborContract) {
        return LaborUserContractDto
                .builder()
                .id(laborContract.getId())
                .endDate(dateConvertor.localDateToString(laborContract.getEndDate()))
                .firstName(laborContract.getEmployeeHistory().getEmployee().getFirstName())
                .lastName(laborContract.getEmployeeHistory().getEmployee().getLastName())
                .patronymic(laborContract.getEmployeeHistory().getEmployee().getPatronymic())
                .position(laborContract.getPosition().getName())
                .build();
    }

    public LaborContract convertToEntity(EmployeeForHiringDTO dto,
                                         EmployeeHistory employeeHistory,
                                         Diploma diploma, Position position) {
        LaborContract laborContract = new LaborContract();
        laborContract.setStartDate(dateConvertor.stringToLocalDate(dto.laborContractForHiringDto().startDate()));
        if (dto.laborContractForHiringDto().endDate() != null) {
            laborContract.setEndDate(dateConvertor.stringToLocalDate(dto.laborContractForHiringDto().endDate()));
        }
        laborContract.setPart(dto.laborContractForHiringDto().part());
        laborContract.setEmployeeHistory(employeeHistory);
        laborContract.setPosition(position);
        laborContract.setDiploma(diploma);
        return laborContract;
    }
}
