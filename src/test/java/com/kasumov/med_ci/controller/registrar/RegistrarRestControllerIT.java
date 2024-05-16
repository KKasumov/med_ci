package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrarRestControllerIT extends ContextIT {


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_rest_controller/getCurrentRegistrar.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_rest_controller/getCurrentRegistrar_clear.sql")
    public void getCurrentRegistrar() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("arnold@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/registrar/main/current")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(50)))
                .andExpect(jsonPath("$.data.email", Is.is("arnold@mail.com")))
                .andExpect(jsonPath("$.data.lastName", Is.is("Schwarzenegger")))
                .andExpect(jsonPath("$.data.firstName", Is.is("Arnold")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("Gustav")))
                .andExpect(jsonPath("$.data.birthday", Is.is("1947-07-30")))
                .andExpect(jsonPath("$.data.gender", Is.is("MALE")));
    }

}

