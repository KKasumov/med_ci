package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrarPolisRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_polis_rest_controller/getAllPatientPolis.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_polis_rest_controller/getAllPatientPolis_clear.sql")
    public void registrarGetAllPatientPolis() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);

        mockMvc.perform(get("/api/registrar/polis/for/patient/{patientId}/get/all", 20)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[0].id", Is.is(501)))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data[0].serial", Is.is("0000")))
                .andExpect(jsonPath("$.data[0].number", Is.is("000001")))
                .andExpect(jsonPath("$.data[0].dateStart", Is.is("01.01.2012")))
                .andExpect(jsonPath("$.data[0].dateEnd", Is.is("01.01.2029")))
                .andExpect(jsonPath("$.data[0].isDeprecated", Is.is(false)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].smo.name", Is.is("smo1")))
                .andExpect(jsonPath("$.data[0].smo.region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[0].smo.code", Is.is("0000")))

                .andExpect(jsonPath("$.data[1].id", Is.is(502)))
                .andExpect(jsonPath("$.data[1].insuranceType", Is.is("DMS")))
                .andExpect(jsonPath("$.data[1].serial", Is.is("0001")))
                .andExpect(jsonPath("$.data[1].number", Is.is("000002")))
                .andExpect(jsonPath("$.data[1].dateStart", Is.is("01.01.2013")))
                .andExpect(jsonPath("$.data[1].dateEnd", Is.is("01.01.2028")))
                .andExpect(jsonPath("$.data[1].isDeprecated", Is.is(false)))
                .andExpect(jsonPath("$.data[1].smo.id", Is.is(602)))
                .andExpect(jsonPath("$.data[1].smo.name", Is.is("smo2")))
                .andExpect(jsonPath("$.data[1].smo.region", Is.is("NIZHNY_NOVGOROD_REGION")))
                .andExpect(jsonPath("$.data[1].smo.code", Is.is("0001")));
    }
}
