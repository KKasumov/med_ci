package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentWithChiefDto;
import com.kasumov.med_ci.model.dto.structure.department.NewDepartmentDto;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.exception.SameConstraintException;
import com.kasumov.med_ci.model.exception.CharacteristicsException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.structure.DepartmentWithChiefDtoService;
import com.kasumov.med_ci.service.entity.structure.DepartmentService;
import com.kasumov.med_ci.service.entity.structure.MedicalOrganizationService;
import com.kasumov.med_ci.service.entity.user.DoctorService;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.service.dto.structure.DepartmentDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.kasumov.med_ci.model.enums.RolesEnum.DIRECTOR;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR_MANAGER')")
@RequestMapping("api/manager/department")
public class HrManagerDepartmentRestController {

    private final DepartmentService departmentService;
    private final DoctorService doctorService;
    private final DepartmentWithChiefDtoService departmentWithChiefDtoService;
    private final DepartmentDtoService departmentDtoService;
    private final MedicalOrganizationService medicalOrganizationService;

    @ApiOperation("Кадровик назначает заведующего отделением")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Заведующий отделением назначен"),
            @ApiResponse(code = 450, message = "Отделения с таким id не существует"),
            @ApiResponse(code = 450, message = "Доктора с таким id не существует"),
            @ApiResponse(code = 455, message = "Доктор уже назначен заведующим отделением"),
            @ApiResponse(code = 456, message = "У доктора с таким id  роль DIRECTOR"),
            @ApiResponse(code = 456, message = "Доктор работает в другом отделении"),
            @ApiResponse(code = 453, message = "У доктора нет действующего трудового договора")

    })
    @PatchMapping("/{departmentId}/add/chief/{doctorId}")
    public Response<DepartmentWithChiefDto> addChiefOfDepartment(@PathVariable long departmentId, @PathVariable long doctorId) {
        Optional<Doctor> newChief = doctorService.getDoctorWithRole(doctorId);
        if (newChief.isEmpty()) {
            throw new EntityNotFoundException("Доктора с таким id не существует");
        }
        if (newChief.get().getRole().getName().equals(DIRECTOR.name())) {
                throw new CharacteristicsException("У доктора с таким id  роль DIRECTOR");
        }

        Department department = departmentService.findById(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("Отделения с таким id не существует");
        }

        Doctor currentChiefDoctor = departmentService.getChiefDoctorByDepartmentId(departmentId);
        if (currentChiefDoctor != null && currentChiefDoctor.getId() == doctorId) {
            throw new SameConstraintException("Доктор уже назначен заведующим отделением");
        }
        Department currentDepartment = newChief.get().getEmployeeHistory().getDepartment();
        if (currentDepartment != null && currentDepartment.getId() != departmentId) {
            throw new CharacteristicsException("Доктор работает в другом отделении");
        }
        if (doctorService.isExistDoctorByLaborContractValid(doctorId)) {
            throw new InvalidParametersPassedException("У доктора нет действующего трудового договора");
        }

        return Response.ok(departmentWithChiefDtoService.addChiefOfDepartment(department, newChief.get()));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Отделение удалено"),
            @ApiResponse(code = 450, message = "Отделения не существует"),
            @ApiResponse(code = 451, message = "У отделения есть сотрудники"),
            @ApiResponse(code = 451, message = "У отделения есть заболевания")
    })
    @DeleteMapping("/{departmentId}/delete")
    public Response<Void> deleteDepartmentById(@PathVariable Long departmentId) {
        Department department = departmentService.findById(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("Отделения не существует");
        }
        if (!department.getEmployeeHistories().isEmpty()) {
            throw new BusyConstraintException("У отделения есть сотрудники");
        }
        if (!department.getDiseases().isEmpty()) {
            throw new BusyConstraintException("У отделения есть заболевания");
        }
        departmentService.delete(department);
        return Response.ok();
    }

    @ApiOperation("Обновление отделения")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Отделение успешно обновлено"),
            @ApiResponse(code = 450, message = "Отделение с таким ID не найдено"),
            @ApiResponse(code = 451, message = "Изменение типа отделения запрещено, т.к. с ним связан хоть один доктор"),
            @ApiResponse(code = 451, message = "Изменение типа отделения запрещено, т.к. с ним связано хоть одно заболевание"),
            @ApiResponse(code = 451, message = "Изменение типа отделения запрещено, т.к. с ним связана хоть одна медицинская услуга")
    })
    @PatchMapping("/update")
    public Response<DepartmentWithChiefDto> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = departmentService.findById(departmentDto.id());
        if (department == null) {
            throw new EntityNotFoundException("Отделение с таким ID не найдено");
        }
        if (!department.getEmployeeHistories().isEmpty()) {
            throw new BusyConstraintException("Изменение типа отделения запрещено, т.к. с ним связан хоть один доктор");
        }
        if (!department.getDiseases().isEmpty()) {
            throw new BusyConstraintException("Изменение типа отделения запрещено, т.к. с ним связано хоть одно заболевание");
        }
        if (!department.getMedicalServices().isEmpty()) {
            throw new BusyConstraintException("Изменение типа отделения запрещено, т.к. с ним связана хоть одна медицинская услуга");
        }
        return Response.ok(departmentDtoService.updateDepartment(departmentDto));
    }

    @ApiOperation("Добавление нового отделения")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Отделение успешно добавлено"),
            @ApiResponse(code = 450, message = "Организации с таким ID не существует")
    })
    @PostMapping("/add/for/organization/{organizationId}")
    public Response<DepartmentWithChiefDto> createNewDepartment(@PathVariable long organizationId,
                                                                @RequestBody NewDepartmentDto newDepartmentDto) {
        MedicalOrganization medicalOrganization = medicalOrganizationService.findById(organizationId);
        if (medicalOrganization == null) {
            throw new EntityNotFoundException("Организации с таким ID не существует");
        }
        return Response.ok(departmentDtoService.addNewDepartment(newDepartmentDto, medicalOrganization));
    }

    @ApiOperation("Кадровик назначает ИО заведующего отделением")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ИО заведующего отделением назначен"),
            @ApiResponse(code = 450, message = "Отделения с таким id не существует"),
            @ApiResponse(code = 450, message = "Доктора с таким id не существует"),
            @ApiResponse(code = 455, message = "Доктор уже назначен ио заведующего отделением"),
            @ApiResponse(code = 450, message = "У доктора с таким id  роль DIRECTOR"),
            @ApiResponse(code = 455, message = "Доктор работает в другом отделении"),
            @ApiResponse(code = 455, message = "У доктора нет действующего трудового договора")

    })
    @PatchMapping("/{departmentId}/add/io/chief/{doctorId}")
    public Response<DepartmentWithChiefDto> addIOChiefOfDepartment(@PathVariable long departmentId,
                                                                   @PathVariable long doctorId) {
        Department department = departmentService.findById(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("Отделения с таким id не существует");
        }

        Optional<Doctor> newIOChief = doctorService.getDoctorWithRole(doctorId);
        if (newIOChief.isEmpty()) {
            throw new EntityNotFoundException("Доктора с таким id не существует");
        }
        if (newIOChief.get().getRole().getName().equals(DIRECTOR.name())) {
            throw new EntityNotFoundException("У доктора с таким id  роль DIRECTOR");
        }

        Doctor currentIOChiefDoctor = departmentService.getIOChiefDoctorByDepartmentId(departmentId);
        if (currentIOChiefDoctor != null && currentIOChiefDoctor.getId() == doctorId) {
            throw new SameConstraintException("Доктор уже назначен ио заведующего отделением");
        }
        Department currentDepartment = newIOChief.get().getEmployeeHistory().getDepartment();
        if (currentDepartment != null && currentDepartment.getId() != departmentId) {
            throw new SameConstraintException("Доктор работает в другом отделении");
        }
        if (doctorService.isExistDoctorByLaborContractValid(doctorId)) {
            throw new SameConstraintException("У доктора нет действующего трудового договора");
        }
        return Response.ok(departmentWithChiefDtoService.addIOChiefOfDepartment(department, newIOChief.get()));
    }

    @ApiOperation("Кадровик снимает с должности заведующего отделением")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Заведующий отделением снят с должности"),
            @ApiResponse(code = 450, message = "Отделения с таким id не существует"),
            @ApiResponse(code = 450, message = "Заведущего в отделении не существует"),
    })
    @PatchMapping("/{departmentId}/delete/chief")
    public Response<DepartmentWithChiefDto> hrmanagerRemovesChiefOfDepartment(@PathVariable long departmentId) {
        Department department = departmentService.findById(departmentId);
         if (department == null) {
            throw new EntityNotFoundException("Отделения с таким id не существует");
        }
        if (doctorService.chiefByDepartment(department)) {
            throw new EntityNotFoundException("Заведущего в отделении не существует");
        }
        return Response.ok(departmentWithChiefDtoService.removersChiefOfDepartment(department));
    }
}
