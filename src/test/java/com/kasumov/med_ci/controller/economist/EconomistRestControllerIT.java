package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EconomistRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/economist/economist_rest_controller/getCurrentEconomist.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/economist/economist_rest_controller/getCurrentEconomist_clear.sql")

    public void getCurrentEconomistDto() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("econom@yandex.ru", "100", mockMvc);
        mockMvc.perform(
                        get("/api/economist/main/current")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(10)))
                .andExpect(jsonPath("$.data.email", Is.is("econom@yandex.ru")))
                .andExpect(jsonPath("$.data.lastName", Is.is("Vostrikova")))
                .andExpect(jsonPath("$.data.firstName", Is.is("Galina")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("Ivanovna")))
                .andExpect(jsonPath("$.data.birthday", Is.is("12.02.1962")))
                .andExpect(jsonPath("$.data.gender", Is.is("FEMALE")));
    }
}
