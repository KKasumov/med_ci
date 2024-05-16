package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/patient/patient_rest_controller/getCurrentUser.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/patient/patient_rest_controller/getCurrentUser_clear.sql")
    public void getCurrentUserTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/patient/main/current")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//              .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(20)))
                .andExpect(jsonPath("$.data.email", Is.is("ivan@mail.com")))
                .andExpect(jsonPath("$.data.lastName", Is.is("Ivanov")))
                .andExpect(jsonPath("$.data.firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.birthday", Is.is("25.06.1990")))
                .andExpect(jsonPath("$.data.gender", Is.is("MALE")));
    }

}