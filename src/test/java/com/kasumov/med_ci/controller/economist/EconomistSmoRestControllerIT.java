package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.enums.Region;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EconomistSmoRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/economist/economist_smo_rest_controller/economistGetAllSmo.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/economist/economist_smo_rest_controller/economistGetAllSmo_clear.sql")

    public void getAllSmoByParametersTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("economist@gmail.com", "1", mockMvc);

        //СМО всех регионов (регион не передан), дефолтный паттерн
        mockMvc.perform(
                        get("/api/economist/smo/get/by/parameters")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data[0].id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].name", Is.is("smo_pattern1_name")))
                .andExpect(jsonPath("$.data[0].region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[0].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[0].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[0].code", Is.is("0000")))

                .andExpect(jsonPath("$.data[1].id", Is.is(602)))
                .andExpect(jsonPath("$.data[1].name", Is.is("smo_pattern2_name")))
                .andExpect(jsonPath("$.data[1].region", Is.is("KAZAN_REGION")))
                .andExpect(jsonPath("$.data[1].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[1].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[1].code", Is.is("0001")))

                .andExpect(jsonPath("$.data[2].id", Is.is(603)))
                .andExpect(jsonPath("$.data[2].name", Is.is("smo_3_name")))
                .andExpect(jsonPath("$.data[2].region", Is.is("CHELYABINSK_REGION")))
                .andExpect(jsonPath("$.data[2].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[2].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[2].code", Is.is("0002")))

                .andExpect(jsonPath("$.data[3].id", Is.is(604)))
                .andExpect(jsonPath("$.data[3].name", Is.is("smo_pattern4_name")))
                .andExpect(jsonPath("$.data[3].region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[3].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[3].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[3].code", Is.is("0003")))

                .andExpect(jsonPath("$.data[4].id", Is.is(605)))
                .andExpect(jsonPath("$.data[4].name", Is.is("smo_5_name")))
                .andExpect(jsonPath("$.data[4].region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[4].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[4].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[4].code", Is.is("0004")));

        //СМО переданный регион, дефолтный паттерн
        mockMvc.perform(
                        get("/api/economist/smo/get/by/parameters")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("region", Region.KAZAN_REGION.name())
                )
                .andExpect(jsonPath("$.data[0].id", Is.is(602)))
                .andExpect(jsonPath("$.data[0].name", Is.is("smo_pattern2_name")))
                .andExpect(jsonPath("$.data[0].region", Is.is("KAZAN_REGION")))
                .andExpect(jsonPath("$.data[0].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[0].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[0].code", Is.is("0001")));

        //СМО всех регионов (регион не передан), переданный паттерн
        mockMvc.perform(
                        get("/api/economist/smo/get/by/parameters")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("pattern", "pattern")
                )
                .andExpect(jsonPath("$.data[0].id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].name", Is.is("smo_pattern1_name")))
                .andExpect(jsonPath("$.data[0].region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[0].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[0].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[0].code", Is.is("0000")))

                .andExpect(jsonPath("$.data[1].id", Is.is(602)))
                .andExpect(jsonPath("$.data[1].name", Is.is("smo_pattern2_name")))
                .andExpect(jsonPath("$.data[1].region", Is.is("KAZAN_REGION")))
                .andExpect(jsonPath("$.data[1].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[1].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[1].code", Is.is("0001")))

                .andExpect(jsonPath("$.data[2].id", Is.is(604)))
                .andExpect(jsonPath("$.data[2].name", Is.is("smo_pattern4_name")))
                .andExpect(jsonPath("$.data[2].region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[2].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[2].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[2].code", Is.is("0003")));

        //СМО переданный регион, переданный паттерн
        mockMvc.perform(
                        get("/api/economist/smo/get/by/parameters")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("region", Region.MOSCOW.name())
                                .param("pattern", "pattern")
                )
                .andExpect(jsonPath("$.data[0].id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].name", Is.is("smo_pattern1_name")))
                .andExpect(jsonPath("$.data[0].region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[0].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[0].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[0].code", Is.is("0000")))

                .andExpect(jsonPath("$.data[1].id", Is.is(604)))
                .andExpect(jsonPath("$.data[1].name", Is.is("smo_pattern4_name")))
                .andExpect(jsonPath("$.data[1].region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[1].startDate", Is.is("01.01.2011")))
                .andExpect(jsonPath("$.data[1].endDate", Is.is(dateConvertor.localDateToString(LocalDate.now().plusYears(10)))))
                .andExpect(jsonPath("$.data[1].code", Is.is("0003")));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/economist/economist_smo_rest_controller/economistDeleteSmo.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/economist/economist_smo_rest_controller/economistDeleteSmo_clear.sql")
    public void deleteSmo() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("economist@gmail.com", "1", mockMvc);

        // проверка, что не удаляет из-за отсутствия СМО с таким id
        mockMvc.perform(
                        delete("/api/economist/smo/{smoId}/delete", 100500)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("СМО не существует")));

        // проверка, что не удаляет из-за наличия полиса
        mockMvc.perform(
                        delete("/api/economist/smo/{smoId}/delete", 601)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("У СМО есть полисы")));

        // проверка, что не удаляет из-за наличия счетов
        mockMvc.perform(
                        delete("/api/economist/smo/{smoId}/delete", 602)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("У СМО есть счета")));


        // проверка, что не удаляет из-за наличия муниципальных заказов
        mockMvc.perform(
                        delete("/api/economist/smo/{smoId}/delete", 603)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("У СМО есть муниципальные заказы")));

        // проверка на удаление
        mockMvc.perform(
                        delete("/api/economist/smo/{smoId}/delete", 604)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200));

        //проверка что каскадно удалены счета в банках
        Assertions.assertEquals(0, entityManager.createQuery("""
                SELECT COUNT(sba)
                FROM SmoBankAccount sba
                WHERE sba.smo.id = 604
                """, Long.class).getSingleResult());

        // проверка успешного удаления
        mockMvc.perform(
                        delete("/api/economist/smo/{smoId}/delete", 604)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("СМО не существует")));

    }
}
