
package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.util.ContextIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EconomistDepartmentRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_department_rest_controller/economistGetDepartments.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_department_rest_controller/economistGetDepartments_clear.sql")
    public void getMedicalDepartmentsByParameters() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);

        mockMvc.perform(get("/api/economist/department/get/medical/by/parameters")
                        .header("Authorization", accessToken)
                        .param("ageType", "CHILD")
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("name1"))
                .andExpect(jsonPath("$.data[0].ageType").value("CHILD"))

                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("name2"))
                .andExpect(jsonPath("$.data[1].ageType").value("CHILD"));

        mockMvc.perform(get("/api/economist/department/get/medical/by/parameters")
                        .header("Authorization", accessToken)
                        .param("pattern", "name2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(2))
                .andExpect(jsonPath("$.data[0].name").value("name2"))
                .andExpect(jsonPath("$.data[0].ageType").value("CHILD"))

                .andExpect(jsonPath("$.data[1].id").value(3))
                .andExpect(jsonPath("$.data[1].name").value("name2"))
                .andExpect(jsonPath("$.data[1].ageType").value("ADULT"));

        mockMvc.perform(get("/api/economist/department/get/medical/by/parameters")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("name1"))
                .andExpect(jsonPath("$.data[0].ageType").value("CHILD"))

                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("name2"))
                .andExpect(jsonPath("$.data[1].ageType").value("CHILD"))

                .andExpect(jsonPath("$.data[2].id").value(3))
                .andExpect(jsonPath("$.data[2].name").value("name2"))
                .andExpect(jsonPath("$.data[2].ageType").value("ADULT"));

    }
}
