package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.medical.medicalService.NewMedicalServiceDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EconomistServicesRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_services_rest_controller/economistSaveNewMedService.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_services_rest_controller/economistSaveNewMedService_clear.sql")
    public void economistSaveNewMedService() throws Exception {
        NewMedicalServiceDto newMedicalServiceDto = NewMedicalServiceDto.builder()
                .identifier("0001")
                .name("UkolPlatnyi")
                .isDisabled(false)
                .build();

        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);
        mockMvc.perform(
                        post("/api/economist/service/add/new")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(newMedicalServiceDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.identifier", Is.is("0001")))
                .andExpect(jsonPath("$.data.name", Is.is("UkolPlatnyi")))
                .andExpect(jsonPath("$.data.isDisabled", Is.is(false)))
                .andExpect(jsonPath("$.data.departmentDto", Matchers.nullValue()));

//      Проверка, что услуга уже существует
        mockMvc.perform(
                        post("/api/economist/service/add/new")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(newMedicalServiceDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Услуга с переданным идентификатором используется")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_services_rest_controller/getMedicalServicesByParameters.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_services_rest_controller/getMedicalServicesByParameters_clear.sql")

    public void getMedicalServicesByParametersTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);

        mockMvc.perform(
                        get("/api/economist/service/find/by/parameters?pattern={parameters}&departmentId={departmentId}","","")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.length()",Is.is(4)))

                .andExpect(jsonPath("$.data[0].id",Is.is(1)))
                .andExpect(jsonPath("$.data[0].identifier",Is.is("Хирургия")))
                .andExpect(jsonPath("$.data[0].name",Is.is("Аппендицит")))
                .andExpect(jsonPath("$.data[0].isDisabled",Is.is(true)))
                .andExpect(jsonPath("$.data[0].departmentDto.id",Is.is(1)))
                .andExpect(jsonPath("$.data[0].departmentDto.name",Is.is("name")))
                .andExpect(jsonPath("$.data[0].departmentDto.ageType",Is.is("CHILD")))

                .andExpect(jsonPath("$.data[1].id",Is.is(2)))
                .andExpect(jsonPath("$.data[1].identifier",Is.is("Терапевт")))
                .andExpect(jsonPath("$.data[1].name",Is.is("Насморк")))
                .andExpect(jsonPath("$.data[1].isDisabled",Is.is(true)))
                .andExpect(jsonPath("$.data[1].departmentDto.id",Is.is(2)))
                .andExpect(jsonPath("$.data[1].departmentDto.name",Is.is("mmmm")))
                .andExpect(jsonPath("$.data[1].departmentDto.ageType",Is.is("ADULT")))

                .andExpect(jsonPath("$.data[2].id",Is.is(5)))
                .andExpect(jsonPath("$.data[2].identifier",Is.is("ЛОР")))
                .andExpect(jsonPath("$.data[2].name",Is.is("Нос")))
                .andExpect(jsonPath("$.data[2].isDisabled",Is.is(true)))
                .andExpect(jsonPath("$.data[2].departmentDto.id",Is.is(1)))
                .andExpect(jsonPath("$.data[2].departmentDto.name",Is.is("name")))
                .andExpect(jsonPath("$.data[2].departmentDto.ageType",Is.is("CHILD")))

                .andExpect(jsonPath("$.data[3].id",Is.is(10)))
                .andExpect(jsonPath("$.data[3].identifier",Is.is("Венеролог")))
                .andExpect(jsonPath("$.data[3].name",Is.is("Вена")))
                .andExpect(jsonPath("$.data[3].isDisabled",Is.is(true)))
                .andExpect(jsonPath("$.data[3].departmentDto.id",Is.is(20)))
                .andExpect(jsonPath("$.data[3].departmentDto.name",Is.is("mmmm")))
                .andExpect(jsonPath("$.data[3].departmentDto.ageType",Is.is("ADULT")))

                .andExpect(jsonPath("$.code", Is.is(200)));

        mockMvc.perform(
                        get("/api/economist/service/find/by/parameters?pattern={parameters}&departmentId={departmentId}","",1)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.length()",Is.is(2)))

                .andExpect(jsonPath("$.data[0].id",Is.is(1)))
                .andExpect(jsonPath("$.data[0].identifier",Is.is("Хирургия")))
                .andExpect(jsonPath("$.data[0].name",Is.is("Аппендицит")))
                .andExpect(jsonPath("$.data[0].isDisabled",Is.is(true)))
                .andExpect(jsonPath("$.data[0].departmentDto.id",Is.is(1)))
                .andExpect(jsonPath("$.data[0].departmentDto.name",Is.is("name")))
                .andExpect(jsonPath("$.data[0].departmentDto.ageType",Is.is("CHILD")))

                .andExpect(jsonPath("$.data[1].id",Is.is(5)))
                .andExpect(jsonPath("$.data[1].identifier",Is.is("ЛОР")))
                .andExpect(jsonPath("$.data[1].name",Is.is("Нос")))
                .andExpect(jsonPath("$.data[1].isDisabled",Is.is(true)))
                .andExpect(jsonPath("$.data[1].departmentDto.id",Is.is(1)))
                .andExpect(jsonPath("$.data[1].departmentDto.name",Is.is("name")))
                .andExpect(jsonPath("$.data[1].departmentDto.ageType",Is.is("CHILD")))

                .andExpect(jsonPath("$.code", Is.is(200)));

        mockMvc.perform(
                        get("/api/economist/service/find/by/parameters?pattern={parameters}&departmentId={departmentId}","",123)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.length()",Is.is(0)))
                .andExpect(jsonPath("$.code", Is.is(200)));

        mockMvc.perform(
                        get("/api/economist/service/find/by/parameters?pattern={parameters}&departmentId={departmentId}","п","")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.length()",Is.is(2)))

                .andExpect(jsonPath("$.data[0].id",Is.is(1)))
                .andExpect(jsonPath("$.data[0].identifier",Is.is("Хирургия")))
                .andExpect(jsonPath("$.data[0].name",Is.is("Аппендицит")))
                .andExpect(jsonPath("$.data[0].isDisabled",Is.is(true)))
                .andExpect(jsonPath("$.data[0].departmentDto.id",Is.is(1)))
                .andExpect(jsonPath("$.data[0].departmentDto.name",Is.is("name")))
                .andExpect(jsonPath("$.data[0].departmentDto.ageType",Is.is("CHILD")))

                .andExpect(jsonPath("$.data[1].id",Is.is(2)))
                .andExpect(jsonPath("$.data[1].identifier",Is.is("Терапевт")))
                .andExpect(jsonPath("$.data[1].name",Is.is("Насморк")))
                .andExpect(jsonPath("$.data[1].isDisabled",Is.is(true)))
                .andExpect(jsonPath("$.data[1].departmentDto.id",Is.is(2)))
                .andExpect(jsonPath("$.data[1].departmentDto.name",Is.is("mmmm")))
                .andExpect(jsonPath("$.data[1].departmentDto.ageType",Is.is("ADULT")))
                .andExpect(jsonPath("$.code", Is.is(200)));

        mockMvc.perform(
                        get("/api/economist/service/find/by/parameters?pattern={parameters}&departmentId={departmentId}","пп","")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(jsonPath("$.data[0].id",Is.is(1)))
                .andExpect(jsonPath("$.data[0].identifier",Is.is("Хирургия")))
                .andExpect(jsonPath("$.data[0].name",Is.is("Аппендицит")))
                .andExpect(jsonPath("$.data[0].isDisabled",Is.is(true)))
                .andExpect(jsonPath("$.data[0].departmentDto.id",Is.is(1)))
                .andExpect(jsonPath("$.data[0].departmentDto.name",Is.is("name")))
                .andExpect(jsonPath("$.data[0].departmentDto.ageType",Is.is("CHILD")))

                .andExpect(jsonPath("$.code", Is.is(200)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_services_rest_controller/economistDeleteMedicalService.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_services_rest_controller/economistDeleteMedicalService_clear.sql")
    public void economistDeleteMedicalService() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);

        //проверка, что не удаляется из-за связи с отделением
        mockMvc.perform(delete("/api/economist/service/{medicalServiceId}/delete", 41)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Медицинская услуга используется отделением")));

        //проверка, что не удаляется из-за связи с посещениями
        mockMvc.perform(delete("/api/economist/service/{medicalServiceId}/delete", 42)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Существуют посещения, связанные с данной услугой")));

        //проверка удачного удаления
        mockMvc.perform(delete("/api/economist/service/{medicalServiceId}/delete", 40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(status().isOk());

        //проверка, что услуга удалена
        mockMvc.perform(delete("/api/economist/service/{medicalServiceId}/delete", 40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Медицинской услуги с таким id не существует")));
    }
}
