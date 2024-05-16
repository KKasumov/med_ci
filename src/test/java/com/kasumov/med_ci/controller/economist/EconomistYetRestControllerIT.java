package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.economic.yet.YetDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EconomistYetRestControllerIT extends ContextIT {


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistGetAllYet.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistGetAllYet_clear.sql")
    public void economistGetAllYet_isOk_Test() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/economist/yet/get/all")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(3)))
                .andExpect(jsonPath("$.data.[0].id", Is.is(600)))
                .andExpect(jsonPath("$.data.[0].price", Is.is(100.0)))
                .andExpect(jsonPath("$.data.[0].dayFrom", Is.is("01.01.2023")))
                .andExpect(jsonPath("$.data.[0].dayTo", Is.is("01.02.2023")))
                .andExpect(jsonPath("$.data.[1].id", Is.is(601)))
                .andExpect(jsonPath("$.data.[1].price", Is.is(200.0)))
                .andExpect(jsonPath("$.data.[1].dayFrom", Is.is("01.01.2023")))
                .andExpect(jsonPath("$.data.[1].dayTo", Is.is("02.03.2023")))
                .andExpect(jsonPath("$.data.[2].id", Is.is(602)))
                .andExpect(jsonPath("$.data.[2].price", Is.is(300.0)))
                .andExpect(jsonPath("$.data.[2].dayFrom", Is.is("01.01.2023")))
                .andExpect(jsonPath("$.data.[2].dayTo", Matchers.nullValue()));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistGetAllYet_noYet.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistGetAllYet_clear.sql")
    public void economistGetAllYet_notFoundYet_Test() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/economist/yet/get/all")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(0)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/economist/economist_delete_yet/economistDeleteYet.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/economist/economist_delete_yet/economistDeleteYet_clear.sql")
    public void deleteYet() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(
                        delete("/api/economist/yet/{yetId}/delete", 600)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)));


        mockMvc.perform(
                        delete("/api/economist/yet/{yetId}/delete", 8888)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("УЕТ не существует")));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistEditYet.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistEditYet_clear.sql")
    public void economistEditYet() throws Exception {

        // проверка модификации цены

        YetDto yetDto = YetDto.builder()
                .id(600)
                .price(BigDecimal.valueOf(800))
                .dayFrom("01.01.2000")
                .dayTo("11.01.2000")
                .build();

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(
                        patch("/api/economist/yet/edit")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(yetDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200));

        Assertions.assertEquals("800.00", entityManager.createQuery("""
                SELECT y.price
                FROM Yet y
                WHERE y.id = 600
                """).getSingleResult().toString());

        Assertions.assertEquals("2000-01-01", entityManager.createQuery("""
                SELECT y.dayFrom
                FROM Yet y
                WHERE y.id = 600
                """).getSingleResult().toString());

        Assertions.assertEquals("2000-01-11", entityManager.createQuery("""
                SELECT y.dayTo
                FROM Yet y
                WHERE y.id = 600
                """).getSingleResult().toString());


        // проверка модификации не существующей цены

        YetDto yetDto1 = YetDto.builder()
                .id(10000)
                .price(BigDecimal.valueOf(800))
                .dayFrom("01.01.2000")
                .dayTo("11.01.2000")
                .build();

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(
                        patch("/api/economist/yet/edit")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(yetDto1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Yet с таким id не существует")));
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistCheckYetOk.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistCheckYetOk_clear.sql")
    public void economistCheckYet_isOk_Test() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/economist/yet/check")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Is.is(200)));
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistCheckIncorrectDateYet.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_yet_rest_controller/economistCheckIncorrectDateYet_clear.sql")
    public void economistCheck_Incorrect_Date_Yet_Test() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(get("/api/economist/yet/check")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(mvcResult -> {
                    String response = mvcResult.getResponse().getContentAsString();
                    List<String> responseList = Arrays.asList(response.split("\n"));
                    System.out.println(responseList);
                })
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.messages.length()", Is.is(48)))
                .andExpect(jsonPath("$.messages[?(@ =~ /Период с.*/)]").exists())
                .andExpect(jsonPath("$.messages[?(@ =~ /Период с.*/)]").value(hasItem(startsWith("Период с"))));


    }
}


