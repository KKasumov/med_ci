package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class HrManagerBuildingRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_building_rest_controller/getAllBuildings.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/hrmanager" +
            "/hrmanager_building_rest_controller/getAllBuildings_clear.sql")
    public void getAllBuildingsDto() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@gmail.com", "1", mockMvc);

        mockMvc.perform(get("/api/manager/building/get/all")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))

                .andExpect(jsonPath("$.data.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.data.[0].physicalAddress",
                        Is.is("Центральная, д.22, кв.176")))
                .andExpect(jsonPath("$.data.[0].cabinets.[0].id",
                        Is.is(1)))
                .andExpect(jsonPath("$.data.[0].cabinets.[0].number",
                        Is.is(204)))
                .andExpect(jsonPath("$.data.[0].cabinets.[0].name",
                        Is.is("Рентген")))
                .andExpect(jsonPath("$.data.[0].cabinets.[1].id",
                        Is.is(2)))
                .andExpect(jsonPath("$.data.[0].cabinets.[1].number",
                        Is.is(402)))
                .andExpect(jsonPath("$.data.[0].cabinets.[1].name",
                        Is.is("Терапевт")))


                .andExpect(jsonPath("$.data.[1].id", Is.is(2)))
                .andExpect(jsonPath("$.data.[1].physicalAddress",
                        Is.is("Гагарина, д.18, кв.122")))
                .andExpect(jsonPath("$.data.[1].cabinets.[0].id",
                        Is.is(3)))
                .andExpect(jsonPath("$.data.[1].cabinets.[0].number",
                        Is.is(103)))
                .andExpect(jsonPath("$.data.[1].cabinets.[0].name",
                        Is.is("Невролог")))


                .andExpect(jsonPath("$.data.[2].id", Is.is(3)))
                .andExpect(jsonPath("$.data.[2].physicalAddress",
                        Is.is("Щурова, д.1, кв.8")))
                .andExpect(jsonPath("$.data.[2].cabinets",
                        Matchers.empty()));
    }
}
