package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientSmoRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/patient/patient_smo_rest_controller/patientGetAllSmo.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/patient/patient_smo_rest_controller/patientGetAllSmo_clear.sql")
    public void patientGetAllSmo_isOk_Test() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("nikolay@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/patient/smo/get/all")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(3)))
                .andExpect(jsonPath("$.data.[0].id", Is.is(250)))
                .andExpect(jsonPath("$.data.[0].name", Is.is("smo1")))
                .andExpect(jsonPath("$.data.[0].region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data.[0].code", Is.is("0000")))
                .andExpect(jsonPath("$.data.[1].id", Is.is(251)))
                .andExpect(jsonPath("$.data.[1].name", Is.is("smo2")))
                .andExpect(jsonPath("$.data.[1].region", Is.is("SAINT_PETERSBURG_REGION")))
                .andExpect(jsonPath("$.data.[1].code", Is.is("0001")))
                .andExpect(jsonPath("$.data.[2].id", Is.is(252)))
                .andExpect(jsonPath("$.data.[2].name", Is.is("smo3")))
                .andExpect(jsonPath("$.data.[2].region", Is.is("KRASNODAR_REGION")))
                .andExpect(jsonPath("$.data.[2].code", Is.is("0002")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/patient/patient_smo_rest_controller/patientGetAllSmo_noSmo.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/patient/patient_smo_rest_controller/patientGetAllSmo_clear.sql")
    public void patientGetAllSmo_notFoundSmo_Test() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("nikolay@mail.com", "1", mockMvc);

        mockMvc.perform(
                        get("/api/patient/smo/get/all")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(0)));
    }
}
