package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.model.dto.user.polis.NewPolisDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static com.kasumov.med_ci.model.enums.InsuranceType.OMS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientPolisRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/patient/patient_polis_rest_controller/saveNewPolis.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/patient/patient_polis_rest_controller/saveNewPolis_clear.sql")
    public void saveNewPolis() throws Exception {

        NewPolisDto newPolisDto = NewPolisDto.builder()
                .insuranceType(OMS)
                .serial("1111")
                .number("111111111")
                .dateStart("01.01.2000")
                .dateEnd("31.12.2030")
                .smoId(601)
                .build();

        NewPolisDto polisDtoException = NewPolisDto.builder()
                .insuranceType(OMS)
                .serial("1111")
                .number("111111111")
                .dateStart("01.01.2000")
                .dateEnd("31.12.2030")
                .smoId(100500)
                .build();

        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "1", mockMvc);
        mockMvc.perform(
                        post("/api/patient/polis/add/new")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(newPolisDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(3)))
                .andExpect(jsonPath("$.data[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[0].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data[0].serial", Is.is("##11")))
                .andExpect(jsonPath("$.data[0].number", Is.is("#####1111")))
                .andExpect(jsonPath("$.data[0].dateStart", Is.is("01.01.2000")))
                .andExpect(jsonPath("$.data[0].dateEnd", Is.is("31.12.2030")))
                .andExpect(jsonPath("$.data[0].isDeprecated", Is.is(false)))
                .andExpect(jsonPath("$.data[0].smo.id", Is.is(601)))
                .andExpect(jsonPath("$.data[0].smo.name", Is.is("smo1")))
                .andExpect(jsonPath("$.data[0].smo.region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[0].smo.code", Is.is("0000")))

                .andExpect(jsonPath("$.data[1].id", Is.is(50001)))
                .andExpect(jsonPath("$.data[1].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data[1].serial", Is.is("##00")))
                .andExpect(jsonPath("$.data[1].number", Is.is("##0001")))
                .andExpect(jsonPath("$.data[1].dateStart", Is.is("01.01.2012")))
                .andExpect(jsonPath("$.data[1].dateEnd", Is.is("01.01.2029")))
                .andExpect(jsonPath("$.data[1].isDeprecated", Is.is(true)))
                .andExpect(jsonPath("$.data[1].smo.id", Is.is(601)))
                .andExpect(jsonPath("$.data[1].smo.name", Is.is("smo1")))
                .andExpect(jsonPath("$.data[1].smo.region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data[1].smo.code", Is.is("0000")))

                .andExpect(jsonPath("$.data[2].id", Is.is(50002)))
                .andExpect(jsonPath("$.data[2].insuranceType", Is.is("DMS")))
                .andExpect(jsonPath("$.data[2].serial", Is.is("##01")))
                .andExpect(jsonPath("$.data[2].number", Is.is("##0002")))
                .andExpect(jsonPath("$.data[2].dateStart", Is.is("01.01.2013")))
                .andExpect(jsonPath("$.data[2].dateEnd", Is.is("01.01.2028")))
                .andExpect(jsonPath("$.data[2].isDeprecated", Is.is(false)))
                .andExpect(jsonPath("$.data[2].smo.id", Is.is(602)))
                .andExpect(jsonPath("$.data[2].smo.name", Is.is("smo2")))
                .andExpect(jsonPath("$.data[2].smo.region", Is.is("NIZHNY_NOVGOROD_REGION")))
                .andExpect(jsonPath("$.data[2].smo.code", Is.is("0001")));

        // Проверка СМО
        mockMvc.perform(
                        post("/api/patient/polis/add/new")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(polisDtoException))
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("СМО с таким id не существует")));
    }
}
