package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EconomistDiseaseRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_disease_rest_controller/economistRemovesDisease.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_disease_rest_controller/economistRemovesDisease_clear.sql")
    public void economistRemovesDisease() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);

        //проверка, что не удаляется из-за связи с отделением
        mockMvc.perform(delete("/api/economist/disease/{diseaseId}/delete", 401)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Заболевание используется отделением")));

        //проверка, что не удаляется из-за связи с обращением
        mockMvc.perform(delete("/api/economist/disease/{diseaseId}/delete", 403)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Заболевание используется в обращении")));

        //проверка удачного удаления
        mockMvc.perform(delete("/api/economist/disease/{diseaseId}/delete", 400)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        //проверка, что заболевание удалено
        mockMvc.perform(delete("/api/economist/disease/{diseaseId}/delete", 400)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Заболевания с таким id не существует")));


    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_disease_rest_controller/economistBlockedDisease.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_disease_rest_controller/economistBlockedDisease_clear.sql")
    public void economistBlockedDisease() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);
        //проверка, что заболевание заблокировано
        mockMvc.perform(post("/api/economist/disease/{diseaseId}/block", 408)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.identifier").value("identifier9"))
                .andExpect(jsonPath("$.data.name").value("name9"))
                .andExpect(jsonPath("$.data.isDisabled").value(true))
                .andExpect(jsonPath("$.data.ageType").value("ADULT"))
                .andExpect(jsonPath("$.data.department.id").value(1111))
                .andExpect(jsonPath("$.data.department.name").value("name1"))
                .andExpect(jsonPath("$.data.department.ageType", Matchers.nullValue()));


        mockMvc.perform(post("/api/economist/disease/{diseaseId}/block", 88888888)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Заболевания с таким id не существует")));

        mockMvc.perform(post("/api/economist/disease/{diseaseId}/block", 401)
                .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.identifier").value("identifier2"))
                .andExpect(jsonPath("$.data.name").value("name2"))
                .andExpect(jsonPath("$.data.isDisabled").value(true))
                .andExpect(jsonPath("$.data.ageType").value("ADULT"))
                .andExpect(jsonPath("$.data.department", Matchers.nullValue()));
    }

     @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_disease_rest_controller/economistUnBlockedDisease.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_disease_rest_controller/economistUnBlockedDisease_clear.sql")
    public void economistUnBlockedDisease() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);
        //проверка, что заболевание разблокировано
        mockMvc.perform(post("/api/economist/disease/{diseaseId}/unBlock", 408)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.identifier").value("identifier9"))
                .andExpect(jsonPath("$.data.name").value("name9"))
                .andExpect(jsonPath("$.data.isDisabled").value(false))
                .andExpect(jsonPath("$.data.ageType").value("ADULT"))
                .andExpect(jsonPath("$.data.department.id").value(1111))
                .andExpect(jsonPath("$.data.department.name").value("name1"))
                .andExpect(jsonPath("$.data.department.ageType", Matchers.nullValue()));


        mockMvc.perform(post("/api/economist/disease/{diseaseId}/unBlock", 88888888)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Заболевания с таким id не существует")));

        mockMvc.perform(post("/api/economist/disease/{diseaseId}/unBlock", 401)
                .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.identifier").value("identifier2"))
                .andExpect(jsonPath("$.data.name").value("name2"))
                .andExpect(jsonPath("$.data.isDisabled").value(false))
                .andExpect(jsonPath("$.data.ageType").value("ADULT"))
                .andExpect(jsonPath("$.data.department", Matchers.nullValue()));
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/economist/economist_disease_rest_controller/economistFindDiseasesByParameters.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/economist/economist_disease_rest_controller/economistFindDiseasesByParameters_clear.sql")
    public void findDiseasesByParametersTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);

        //проверка, получения заболеваний с переданнами паттерном и id отделения

        mockMvc.perform(get("/api/economist/disease/find/by/parameters")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pattern", "name")
                        .param("departmentId", "1111")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(401))
                .andExpect(jsonPath("$.data[0].identifier").value("identifier2"))
                .andExpect(jsonPath("$.data[0].name").value("name2"))
                .andExpect(jsonPath("$.data[0].isDisabled").value(false))
                .andExpect(jsonPath("$.data[0].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[0].department.id").value(1111))
                .andExpect(jsonPath("$.data[0].department.name").value("name1"))
                .andExpect(jsonPath("$.data[0].department.ageType").value("ADULT"))

                .andExpect(jsonPath("$.data[1].id").value(404))
                .andExpect(jsonPath("$.data[1].identifier").value("name7"))
                .andExpect(jsonPath("$.data[1].name").value("disease3"))
                .andExpect(jsonPath("$.data[1].isDisabled").value(false))
                .andExpect(jsonPath("$.data[1].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[1].department.id").value(1111))
                .andExpect(jsonPath("$.data[1].department.name").value("name1"))
                .andExpect(jsonPath("$.data[1].department.ageType").value("ADULT"));

        //проверка, получения заболеваний с переданнами паттерном без id отделения

        mockMvc.perform(get("/api/economist/disease/find/by/parameters")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pattern", "name")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(400))
                .andExpect(jsonPath("$.data[0].identifier").value("identifier1"))
                .andExpect(jsonPath("$.data[0].name").value("name1"))
                .andExpect(jsonPath("$.data[0].isDisabled").value(false))
                .andExpect(jsonPath("$.data[0].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[0].department", Matchers.nullValue()))

                .andExpect(jsonPath("$.data[1].id").value(401))
                .andExpect(jsonPath("$.data[1].identifier").value("identifier2"))
                .andExpect(jsonPath("$.data[1].name").value("name2"))
                .andExpect(jsonPath("$.data[1].isDisabled").value(false))
                .andExpect(jsonPath("$.data[1].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[1].department.id").value(1111))
                .andExpect(jsonPath("$.data[1].department.name").value("name1"))
                .andExpect(jsonPath("$.data[1].department.ageType").value("ADULT"))

                .andExpect(jsonPath("$.data[2].id").value(404))
                .andExpect(jsonPath("$.data[2].identifier").value("name7"))
                .andExpect(jsonPath("$.data[2].name").value("disease3"))
                .andExpect(jsonPath("$.data[2].isDisabled").value(false))
                .andExpect(jsonPath("$.data[2].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[2].department.id").value(1111))
                .andExpect(jsonPath("$.data[2].department.name").value("name1"))
                .andExpect(jsonPath("$.data[2].department.ageType").value("ADULT"))

                .andExpect(jsonPath("$.data[3].id").value(405))
                .andExpect(jsonPath("$.data[3].identifier").value("name8"))
                .andExpect(jsonPath("$.data[3].name").value("disease4"))
                .andExpect(jsonPath("$.data[3].isDisabled").value(false))
                .andExpect(jsonPath("$.data[3].ageType").value("CHILD"))
                .andExpect(jsonPath("$.data[3].department.id").value(2222))
                .andExpect(jsonPath("$.data[3].department.name").value("name2"))
                .andExpect(jsonPath("$.data[3].department.ageType").value("CHILD"));

        //проверка, получения заболеваний без паттерна и без id отделения

        mockMvc.perform(get("/api/economist/disease/find/by/parameters")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(400))
                .andExpect(jsonPath("$.data[0].identifier").value("identifier1"))
                .andExpect(jsonPath("$.data[0].name").value("name1"))
                .andExpect(jsonPath("$.data[0].isDisabled").value(false))
                .andExpect(jsonPath("$.data[0].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[0].department", Matchers.nullValue()))

                .andExpect(jsonPath("$.data[1].id").value(401))
                .andExpect(jsonPath("$.data[1].identifier").value("identifier2"))
                .andExpect(jsonPath("$.data[1].name").value("name2"))
                .andExpect(jsonPath("$.data[1].isDisabled").value(false))
                .andExpect(jsonPath("$.data[1].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[1].department.id").value(1111))
                .andExpect(jsonPath("$.data[1].department.name").value("name1"))
                .andExpect(jsonPath("$.data[1].department.ageType").value("ADULT"))

                .andExpect(jsonPath("$.data[2].id").value(402))
                .andExpect(jsonPath("$.data[2].identifier").value("identifier4"))
                .andExpect(jsonPath("$.data[2].name").value("disease1"))
                .andExpect(jsonPath("$.data[2].isDisabled").value(false))
                .andExpect(jsonPath("$.data[2].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[2].department", Matchers.nullValue()))

                .andExpect(jsonPath("$.data[3].id").value(403))
                .andExpect(jsonPath("$.data[3].identifier").value("identifier6"))
                .andExpect(jsonPath("$.data[3].name").value("disease2"))
                .andExpect(jsonPath("$.data[3].isDisabled").value(false))
                .andExpect(jsonPath("$.data[3].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[3].department.id").value(1111))
                .andExpect(jsonPath("$.data[3].department.name").value("name1"))
                .andExpect(jsonPath("$.data[3].department.ageType").value("ADULT"))

                .andExpect(jsonPath("$.data[4].id").value(404))
                .andExpect(jsonPath("$.data[4].identifier").value("name7"))
                .andExpect(jsonPath("$.data[4].name").value("disease3"))
                .andExpect(jsonPath("$.data[4].isDisabled").value(false))
                .andExpect(jsonPath("$.data[4].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[4].department.id").value(1111))
                .andExpect(jsonPath("$.data[4].department.name").value("name1"))
                .andExpect(jsonPath("$.data[4].department.ageType").value("ADULT"))

                .andExpect(jsonPath("$.data[5].id").value(405))
                .andExpect(jsonPath("$.data[5].identifier").value("name8"))
                .andExpect(jsonPath("$.data[5].name").value("disease4"))
                .andExpect(jsonPath("$.data[5].isDisabled").value(false))
                .andExpect(jsonPath("$.data[5].ageType").value("CHILD"))
                .andExpect(jsonPath("$.data[5].department.id").value(2222))
                .andExpect(jsonPath("$.data[5].department.name").value("name2"))
                .andExpect(jsonPath("$.data[5].department.ageType").value("CHILD"));



        //проверка, получения заболеваний с id отделения без паттерна

        mockMvc.perform(get("/api/economist/disease/find/by/parameters")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("departmentId", "1111")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(401))
                .andExpect(jsonPath("$.data[0].identifier").value("identifier2"))
                .andExpect(jsonPath("$.data[0].name").value("name2"))
                .andExpect(jsonPath("$.data[0].isDisabled").value(false))
                .andExpect(jsonPath("$.data[0].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[0].department.id").value(1111))
                .andExpect(jsonPath("$.data[0].department.name").value("name1"))
                .andExpect(jsonPath("$.data[0].department.ageType").value("ADULT"))

                .andExpect(jsonPath("$.data[1].id").value(403))
                .andExpect(jsonPath("$.data[1].identifier").value("identifier6"))
                .andExpect(jsonPath("$.data[1].name").value("disease2"))
                .andExpect(jsonPath("$.data[1].isDisabled").value(false))
                .andExpect(jsonPath("$.data[1].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[1].department.id").value(1111))
                .andExpect(jsonPath("$.data[1].department.name").value("name1"))
                .andExpect(jsonPath("$.data[1].department.ageType").value("ADULT"))

                .andExpect(jsonPath("$.data[2].id").value(404))
                .andExpect(jsonPath("$.data[2].identifier").value("name7"))
                .andExpect(jsonPath("$.data[2].name").value("disease3"))
                .andExpect(jsonPath("$.data[2].isDisabled").value(false))
                .andExpect(jsonPath("$.data[2].ageType").value("ADULT"))
                .andExpect(jsonPath("$.data[2].department.id").value(1111))
                .andExpect(jsonPath("$.data[2].department.name").value("name1"))
                .andExpect(jsonPath("$.data[2].department.ageType").value("ADULT"));


        //проверка, что отделение не медицинское

        mockMvc.perform(get("/api/economist/disease/find/by/parameters")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("departmentId", "3333")
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Мед отделения с таким id не существует")));


        //проверка, что отделения с таким id не существует

        mockMvc.perform(get("/api/economist/disease/find/by/parameters")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .param("departmentId", "9999999999999")
        )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Мед отделения с таким id не существует")));

    }
}
