package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.dto.user.employee.EmployeeOfferDto;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.user.Role;
import com.kasumov.med_ci.model.entity.user.items.University;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.user.HrManagerDtoService;
import com.kasumov.med_ci.service.entity.structure.PositionService;
import com.kasumov.med_ci.service.entity.user.RoleService;
import com.kasumov.med_ci.service.entity.user.UniversityService;
import com.kasumov.med_ci.service.entity.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("/api/manager/employee")
public class HrManagerEmployeeRestController {

    private final HrManagerDtoService hrManagerDtoService;
    private final UserService userService;
    private final RoleService roleService;
    private final PositionService positionService;
    private final UniversityService universityService;

    @ApiOperation(value = "кадровик устраивает сотрудника")
    @ApiResponses(value = {
            @ApiResponse(code = 453, message = "Snils не был передан"),
            @ApiResponse(code = 451, message = "Данный адрес эл. почты уже зарегистрирован. Введите другой"),
            @ApiResponse(code = 450, message = "Позиция на занимаемую должность не существует"),
            @ApiResponse(code = 450, message = "Role с таким именем не существует"),
            @ApiResponse(code = 450, message = "Университета с данным названием не существует"),
    })
    @PostMapping("/hiring/create")
    public Response<EmployeeOfferDto> hiringEmployee(@RequestBody EmployeeForHiringDTO employeeForHiringDTO) {
        if (userService.existsByEmail(employeeForHiringDTO.email())) {
            throw new BusyConstraintException("Данный адрес эл. почты уже зарегистрирован. Введите другой");
        }
        if (employeeForHiringDTO.snils() == null || employeeForHiringDTO.snils().equals("")) {
            throw new InvalidParametersPassedException("Snils не был передан");
        }
        Optional<Role> role = roleService.findById(employeeForHiringDTO.roleId());
        if (role.isEmpty()) {
            throw new EntityNotFoundException("Role с данным id не существует");
        }
        Optional<Position> position = positionService.findById(employeeForHiringDTO.positionId());
        if (position.isEmpty()) {
            throw new EntityNotFoundException("Позиция по id не существует");
        }
        Optional<University> university = universityService.findById(employeeForHiringDTO.diplomaForHiringDto().universityId());
        if (university.isEmpty()) {
            throw new EntityNotFoundException("Университета по id не существует");
        }
        return Response.ok(hrManagerDtoService.hiringEmployee(role.get(), position.get(), university.get(), employeeForHiringDTO));
    }
}
