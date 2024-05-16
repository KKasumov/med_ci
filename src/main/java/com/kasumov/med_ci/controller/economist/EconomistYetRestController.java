package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.economic.yet.YetDto;
import com.kasumov.med_ci.model.entity.economic.Yet;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.dto.economic.YetDtoService;
import com.kasumov.med_ci.service.entity.economic.YetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ECONOMIST')")
@RequestMapping("/api/economist/yet")
public class EconomistYetRestController {

    private final YetDtoService yetDtoService;
    private final YetService yetService;

    @ApiOperation("Экономист получает все УЕТ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Метод возвращает все Yet"),
    })
    @GetMapping("/get/all")
    public Response<List<YetDto>> getAllYet() {
        return Response.ok(yetDtoService.getAllYet());
    }

    @ApiOperation("Экономист удаляет цену УЕТ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Цена УЕТ удалена"),
            @ApiResponse(code = 450, message = "УЕТ не существует"),
    })
    @DeleteMapping("/{yetId}/delete")
    public Response<Void> deleteYetById(@PathVariable Long yetId) {
        Yet yet = yetService.getYetById(yetId);
        if (yet == null) {
            throw new EntityNotFoundException("УЕТ не существует");
        }
        yetService.delete(yet);
        return Response.ok();
    }

    @ApiOperation("Экономист модифицирует цену УЕТ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Yet обновлен"),
            @ApiResponse(code = 450, message = "Yet с таким id не существует")
    })
    @PatchMapping("/edit")
    public Response<YetDto> editYet(@RequestBody YetDto yetDto) {
        if (!yetService.existsById(yetDto.id())) {
            throw new EntityNotFoundException("Yet с таким id не существует");
        }
        return Response.ok(yetDtoService.editYetDto(yetDto));
    }

    @ApiOperation("Экономист проверяет УЕТ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список сообщений"),
    })
    @GetMapping("/check")
    public Response<List<String>> checkYets() {
        List<String> messages = yetService.getMessagesForYet();

        return Response.ok(200, messages);
    }

}





