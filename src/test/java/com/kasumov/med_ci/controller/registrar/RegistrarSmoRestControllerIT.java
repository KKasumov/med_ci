package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrarSmoRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_smo_rest_controller/registrarGetAllSmo.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_smo_rest_controller/registrarGetAllSmo_clear.sql")
    public void registrarGetAllSmo_isOk_Test() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/registrar/smo/get/all")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[0].id", Is.is(600)))
                .andExpect(jsonPath("$.data[0].name", Is.is("smo1")))
                .andExpect(jsonPath("$.data[0].region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[0].code", Is.is("0000")))
                .andExpect(jsonPath("$.data[1].id", Is.is(601)))
                .andExpect(jsonPath("$.data[1].name", Is.is("smo2")))
                .andExpect(jsonPath("$.data[1].region", Is.is("NIZHNY_NOVGOROD_REGION")))
                .andExpect(jsonPath("$.data[1].code", Is.is("0001")));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_smo_rest_controller/registrarGetAllSmo_noSmo.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_smo_rest_controller/registrarGetAllSmo_clear.sql")
    public void registrarGetAllSmo_notFoundSmo_Test() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/registrar/smo/get/all")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(0)));
    }
}
