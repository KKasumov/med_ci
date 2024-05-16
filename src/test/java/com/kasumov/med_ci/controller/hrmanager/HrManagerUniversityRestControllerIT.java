package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.university.UniversityDtoShort;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HrManagerUniversityRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hrmanager_university_rest_controller/hrmanagerSaveNewUniversity.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hrmanager_university_rest_controller/hrmanagerSaveNewUniversity_clear.sql")
    public void hrmanagerSaveNewUniversity() throws Exception {

        UniversityDtoShort universityDtoShort = UniversityDtoShort.builder()
                .name("АлтГТУ")
                .build();

        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.com", "123", mockMvc);

        mockMvc.perform(post("/api/manager/university/add/new")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(universityDtoShort))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(200))
                        .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                        .andExpect(jsonPath("$.data.name", Is.is("АлтГТУ")));


        mockMvc.perform(
                        post("/api/manager/university/add/new")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(universityDtoShort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Университет существует в системе")));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hrmanager_university_rest_controller/hrmanagerGetAllUniversities.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hrmanager_university_rest_controller/hrmanagerGetAllUniversities_clear.sql")
    public void hrmanagerGetAllUniversities() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.com", "123", mockMvc);

        mockMvc.perform(get("/api/manager/university/get/all")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("name1"))

                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("name2"))

                .andExpect(jsonPath("$.data[2].id").value(3))
                .andExpect(jsonPath("$.data[2].name").value("name3"))

                .andExpect(jsonPath("$.data[3].id").value(4))
                .andExpect(jsonPath("$.data[3].name").value("name4"));


    }
}