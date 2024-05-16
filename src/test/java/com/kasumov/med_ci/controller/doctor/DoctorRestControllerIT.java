package com.kasumov.med_ci.controller.doctor;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DoctorRestControllerIT extends ContextIT {


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/doctor/doctor_rest_controller/getCurrentDoctor.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/doctor/doctor_rest_controller/getCurrentDoctor_clear.sql")
    public void getCurrentDoctor() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("arkady@mail.com", "arkady@mail.com", mockMvc);
        mockMvc.perform(
                        get("/api/doctor/main/current")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(10)))
                .andExpect(jsonPath("$.data.email", Is.is("arkady@mail.com")))
                .andExpect(jsonPath("$.data.lastName", Is.is("Parovozov")))
                .andExpect(jsonPath("$.data.firstName", Is.is("Arkady")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("Chuh-Chuh")))
                .andExpect(jsonPath("$.data.birthday", Is.is("18.06.1990")))
                .andExpect(jsonPath("$.data.gender", Is.is("MALE")));

    }
}
