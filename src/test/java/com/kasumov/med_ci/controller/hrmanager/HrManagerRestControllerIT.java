package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HrManagerRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_rest_controller/getCurrentHrManager.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_rest_controller/getCurrentHrManager_clear.sql")
    public void GetCurrentHrManagerDto() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);
        mockMvc.perform(
                        get("/api/manager/main/current")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(77)))
                .andExpect(jsonPath("$.data.email", Is.is("hr@mail.ru")))
                .andExpect(jsonPath("$.data.lastName", Is.is("Ivanov")))
                .andExpect(jsonPath("$.data.firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.birthday", Is.is("01.01.1970")))
                .andExpect(jsonPath("$.data.gender", Is.is("MALE")));
    }
}
