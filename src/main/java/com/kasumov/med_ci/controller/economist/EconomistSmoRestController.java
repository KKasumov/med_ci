package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.economic.smo.SmoDto;
import com.kasumov.med_ci.model.entity.economic.Smo;
import com.kasumov.med_ci.model.enums.Region;
import com.kasumov.med_ci.model.exception.BusyConstraintException;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.economic.SmoDtoService;
import com.kasumov.med_ci.service.entity.economic.SmoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("api/economist/smo")
public class EconomistSmoRestController {

    private final SmoService smoService;
    private final SmoDtoService smoDtoService;

    @ApiOperation("Экономист получет все СМО по переданным параметрам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Получен список СМО"),
    })
    @GetMapping("/get/by/parameters")
    public Response<List<SmoDto>> getAllSmoByParameters(@Nullable Region region, String pattern) {
        return Response.ok(smoDtoService.getAllSmoDtoByParameters(region, pattern));
    }

    @ApiOperation("Экономист удаляет СМО по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "СМО удалено"),
            @ApiResponse(code = 450, message = "СМО не существует"),
            @ApiResponse(code = 451, message = "У СМО есть полисы"),
            @ApiResponse(code = 451, message = "У СМО есть счета"),
            @ApiResponse(code = 451, message = "У СМО есть муниципальные заказы")
    })
    @DeleteMapping("/{smoId}/delete")
    public Response<Void> deleteSmoById(@PathVariable Long smoId) {
        Smo smo = smoService.getSmoById(smoId);
        if (smo == null) {
            throw new EntityNotFoundException("СМО не существует");
        }
        if (!smo.getPolises().isEmpty()) {
            throw new BusyConstraintException("У СМО есть полисы");
        }
        if (!smo.getOrders().isEmpty()) {
            throw new BusyConstraintException("У СМО есть счета");
        }
        if (!smo.getMunicipalOrdersForSmo().isEmpty()) {
            throw new BusyConstraintException("У СМО есть муниципальные заказы");
        }
        smoService.delete(smo);
        return Response.ok();
    }
}
