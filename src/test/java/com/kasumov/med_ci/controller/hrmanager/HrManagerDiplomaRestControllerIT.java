package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.diploma.DiplomaForHiringDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HrManagerDiplomaRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/hrmanager/hrmanager_rest_controller/deleteDiplomaHrManager.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/hrmanager/hrmanager_rest_controller/deleteDiplomaHrManager_clear.sql")
    public void deleteDiplomaByIdTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);
        mockMvc.perform(
                        delete("/api/manager/diploma/{diplomaId}", 700)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data", Is.is("OK")));

        mockMvc.perform(
                        delete("/api/manager/diploma/{diplomaId}", 7)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Диплом с таким Id не найден")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_diploma_rest_controller/addDiplomaByContract.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_diploma_rest_controller/addDiplomaByContract_clear.sql")
    public void addDiplomaByContractIT() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr111@gmail.com", "1", mockMvc);
        DiplomaForHiringDto newDiplomaDtoWithIncorrectUniversity = DiplomaForHiringDto.builder()
                .endDate("03.03.2050")
                .serialNumber("222")
                .universityId(43244L)
                .build();

        DiplomaForHiringDto newDiplomaDto = DiplomaForHiringDto.builder()
                .endDate("03.03.2050")
                .serialNumber("222")
                .universityId(4100L)
                .build();

        //проверка на создание аттестации с несуществующим университетом
        mockMvc.perform(post("/api/manager/diploma/add/for/contract/{contractId}", 4100L)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newDiplomaDtoWithIncorrectUniversity))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Университета с таким id не найдено")));


        //проверка на существование трудового договора
        mockMvc.perform(post("/api/manager/diploma/add/for/contract/{contractId}", 423424234L)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newDiplomaDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Договора с таким id не найдено")));

        // проверка на срок годности контракта
        mockMvc.perform(post("/api/manager/diploma/add/for/contract/{contractId}", 4101L)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newDiplomaDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Договор уже просрочен")));

        // проверка на работоспособность
        mockMvc.perform(post("/api/manager/diploma/add/for/contract/{contractId}", 4100L)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newDiplomaDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.endDate", Is.is("03.03.2050")))
                .andExpect(jsonPath("$.data.number", Is.is("222")))
                .andExpect(jsonPath("$.data.universityDto.id", Is.is(4100)))
                .andExpect(jsonPath("$.data.laborContractDto.id", Is.is(4100)));

        // проверка на то, что диплом существует в системе
        mockMvc.perform(post("/api/manager/diploma/add/for/contract/{contractId}", 4102L)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newDiplomaDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Диплом уже существует в системе")));
    }

}
