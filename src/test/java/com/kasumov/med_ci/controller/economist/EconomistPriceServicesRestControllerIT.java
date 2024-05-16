package com.kasumov.med_ci.controller.economist;

import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.NewPayPriceOfMedicalServiceDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EconomistPriceServicesRestControllerIT extends ContextIT {
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_price_services_rest_controller/economistSavePriceService.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_price_services_rest_controller/economistSavePriceService_clear.sql")
    public void economistSaveNewPayPriceOfMedService() throws Exception {
        NewPayPriceOfMedicalServiceDto newPayPriceOfMedicalServiceDto = NewPayPriceOfMedicalServiceDto.builder()
                .dayFrom("01.01.2000")
                .dayTo("11.01.2000")
                .moneyInPrice(BigDecimal.valueOf(1000))
                .build();

        // Проверка добавление цены

        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);
        mockMvc.perform(
                        post("/api/economist/service/1/price/pay/add")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(newPayPriceOfMedicalServiceDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.dayFrom", Is.is("01.01.2000")))
                .andExpect(jsonPath("$.data.dayTo", Is.is("11.01.2000")))
                .andExpect(jsonPath("$.data.moneyInPrice", Is.is(1000)))
                .andExpect(jsonPath("$.data.medicalService.id", Is.is(1)))
                .andExpect(jsonPath("$.data.medicalService.identifier", Is.is("01")))
                .andExpect(jsonPath("$.data.medicalService.name", Is.is("service")))
                .andExpect(jsonPath("$.data.medicalService.isDisabled", Is.is(false)))
                .andExpect(jsonPath("$.data.medicalService.departmentDto", Matchers.nullValue()));

        // Добавление цены на не существующую услугу

        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);
        mockMvc.perform(
                        post("/api/economist/service/999999999/price/pay/add")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(newPayPriceOfMedicalServiceDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Услуги с таким id не существует")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/economist/economist_price_services_rest_controller/economistGetPriceService.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/economist/economist_price_services_rest_controller/economistGetPriceService_clear.sql")
    public void economistGetPriceOfMedServiceTest() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("economist@mail.com", "1", mockMvc);
        // проверка получения цен
        mockMvc.perform(get("/api/economist/service/{medicalServiceId}/price/get", 1)
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.data.listOmsPriceOfMedicalServiceDto[0].id", Is.is(2)))
                .andExpect(jsonPath("$.data.listOmsPriceOfMedicalServiceDto[0].dayFrom", Is.is("10.12.2020")))
                .andExpect(jsonPath("$.data.listOmsPriceOfMedicalServiceDto[0].dayTo", Is.is("20.12.2022")))
                .andExpect(jsonPath("$.data.listOmsPriceOfMedicalServiceDto[0].yet", Is.is(100.00)))

                .andExpect(jsonPath("$.data.listPayPriceOfMedicalServiceDto[0].id", Is.is(2)))
                .andExpect(jsonPath("$.data.listPayPriceOfMedicalServiceDto[0].dayFrom", Is.is("10.12.2022")))
                .andExpect(jsonPath("$.data.listPayPriceOfMedicalServiceDto[0].dayTo", Is.is("20.12.2022")))
                .andExpect(jsonPath("$.data.listPayPriceOfMedicalServiceDto[0].moneyInPrice", Is.is(100.00)))

                .andExpect(jsonPath("$.data.listOmsPriceOfMedicalServiceDto[1].id", Is.is(3)))
                .andExpect(jsonPath("$.data.listOmsPriceOfMedicalServiceDto[1].dayFrom", Is.is("10.12.2020")))
                .andExpect(jsonPath("$.data.listOmsPriceOfMedicalServiceDto[1].dayTo", Is.is("20.12.2022")))
                .andExpect(jsonPath("$.data.listOmsPriceOfMedicalServiceDto[1].yet", Is.is(100.00)))
                .andExpect(jsonPath("$.data.listPayPriceOfMedicalServiceDto[1].id", Is.is(3)))
                .andExpect(jsonPath("$.data.listPayPriceOfMedicalServiceDto[1].dayFrom", Is.is("10.12.2022")))
                .andExpect(jsonPath("$.data.listPayPriceOfMedicalServiceDto[1].dayTo", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.listPayPriceOfMedicalServiceDto[1].moneyInPrice", Is.is(100.00)));


                // проверка на отсутствия услуги
        mockMvc.perform(
                        get("/api/economist/service/{medicalServiceId}/price/get", 98999)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Услуги с таким id не существует")));

    }
}
