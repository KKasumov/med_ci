package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EconomistOrderRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/economist/economist_order_rest_controller/economistGetAllOrders.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/economist/economist_order_rest_controller/economistGetAllOrders_clear.sql")

    public void getAllOrdersByParametersTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("economist@gmail.com", "1", mockMvc);


        mockMvc.perform(get("/api/economist/order/get/by/parameters")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data[0].comment", Matchers.nullValue()))
                .andExpect(jsonPath("$.data[0].date", Is.is("31.01.2023")))
                .andExpect(jsonPath("$.data[0].money", Is.is(500.0)))
                .andExpect(jsonPath("$.data[0].isFormed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isAcceptedForPayment", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isPayed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(101)));

        // Тестирование с параметром insuranceType
        mockMvc.perform(get("/api/economist/order/get/by/parameters")
                        .header("Authorization", accessToken)
                        .param("insuranceType", "DMS")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(603)))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("DMS")))
                .andExpect(jsonPath("$.data[0].comment", Matchers.nullValue()))
                .andExpect(jsonPath("$.data[0].date", Is.is("01.02.2023")))
                .andExpect(jsonPath("$.data[0].money", Is.is(1000.0)))
                .andExpect(jsonPath("$.data[0].isFormed", Is.is(false)))
                .andExpect(jsonPath("$.data[0].isAcceptedForPayment", Is.is(false)))
                .andExpect(jsonPath("$.data[0].isPayed", Is.is(false)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(102)));

        // Тестирование с параметром isFormed
        mockMvc.perform(get("/api/economist/order/get/by/parameters")
                        .header("Authorization", accessToken)
                        .param("isFormed", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data[0].comment", Matchers.nullValue()))
                .andExpect(jsonPath("$.data[0].date", Is.is("31.01.2023")))
                .andExpect(jsonPath("$.data[0].money", Is.is(500.0)))
                .andExpect(jsonPath("$.data[0].isFormed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isAcceptedForPayment", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isPayed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(101)));

        // Тестирование с параметром isAcceptedForPayment
        mockMvc.perform(get("/api/economist/order/get/by/parameters")
                        .header("Authorization", accessToken)
                        .param("isAcceptedForPayment", "false")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(602)))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data[0].comment", Matchers.nullValue()))
                .andExpect(jsonPath("$.data[0].date", Is.is("01.02.2023")))
                .andExpect(jsonPath("$.data[0].money", Is.is(700.0)))
                .andExpect(jsonPath("$.data[0].isFormed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isAcceptedForPayment", Is.is(false)))
                .andExpect(jsonPath("$.data[0].isPayed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(101)));

        // Тестирование с параметром isPayed
        mockMvc.perform(get("/api/economist/order/get/by/parameters")
                        .header("Authorization", accessToken)
                        .param("isPayed", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data[0].comment", Matchers.nullValue()))
                .andExpect(jsonPath("$.data[0].date", Is.is("31.01.2023")))
                .andExpect(jsonPath("$.data[0].money", Is.is(500.0)))
                .andExpect(jsonPath("$.data[0].isFormed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isAcceptedForPayment", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isPayed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(101)));

        // Тестирование с параметром smoId
        mockMvc.perform(get("/api/economist/order/get/by/parameters")
                        .header("Authorization", accessToken)
                        .param("smoId", "102")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(603)))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("DMS")))
                .andExpect(jsonPath("$.data[0].comment", Matchers.nullValue()))
                .andExpect(jsonPath("$.data[0].date", Is.is("01.02.2023")))
                .andExpect(jsonPath("$.data[0].money", Is.is(1000.0)))
                .andExpect(jsonPath("$.data[0].isFormed", Is.is(false)))
                .andExpect(jsonPath("$.data[0].isAcceptedForPayment", Is.is(false)))
                .andExpect(jsonPath("$.data[0].isPayed", Is.is(false)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(102)));

        // Тестирование с параметром dateEnd
        mockMvc.perform(get("/api/economist/order/get/by/parameters")
                        .header("Authorization", accessToken)
                        .param("dateEnd", (String) null)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data[0].comment", Matchers.nullValue()))
                .andExpect(jsonPath("$.data[0].date", Is.is("31.01.2023")))
                .andExpect(jsonPath("$.data[0].money", Is.is(500.0)))
                .andExpect(jsonPath("$.data[0].isFormed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isAcceptedForPayment", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isPayed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(101)));

        // Тестирование с параметром dateStart
        mockMvc.perform(get("/api/economist/order/get/by/parameters")
                        .header("Authorization", accessToken)
                        .param("dateStart", (String) null)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data[0].comment", Matchers.nullValue()))
                .andExpect(jsonPath("$.data[0].date", Is.is("31.01.2023")))
                .andExpect(jsonPath("$.data[0].money", Is.is(500.0)))
                .andExpect(jsonPath("$.data[0].isFormed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isAcceptedForPayment", Is.is(true)))
                .andExpect(jsonPath("$.data[0].isPayed", Is.is(true)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(101)));

    }

}
