package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatientDocumentsRestControllerTest extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/patient/PatientDocumentsRestController/addPatientDocs.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/patient/PatientDocumentsRestController/clear.sql")
    public void getPatientDocs() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/patient/documents")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.snils", Is.is("snils")))

                .andExpect(jsonPath("$.data.identityDocuments.length()", Is.is(2)))
                .andExpect(jsonPath("$.data.identityDocuments[0].id", Is.is(2)))
                .andExpect(jsonPath("$.data.identityDocuments[0].documentType", Is.is("PASSPORT")))
                .andExpect(jsonPath("$.data.identityDocuments[0].serial", Is.is("########89")))
                .andExpect(jsonPath("$.data.identityDocuments[0].number", Is.is("########10")))
                .andExpect(jsonPath("$.data.identityDocuments[0].dateStart", Is.is("20.12.2020")))
                .andExpect(jsonPath("$.data.identityDocuments[0].firstName", Is.is("Name1")))
                .andExpect(jsonPath("$.data.identityDocuments[0].lastName", Is.is("Last")))
                .andExpect(jsonPath("$.data.identityDocuments[0].patronymic", Is.is("patro")))
                .andExpect(jsonPath("$.data.identityDocuments[0].birthday", Is.is("20.12.2020")))
                .andExpect(jsonPath("$.data.identityDocuments[0].gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.identityDocuments[0].isDeprecated", Is.is(true)))

                .andExpect(jsonPath("$.data.identityDocuments[1].id", Is.is(1)))
                .andExpect(jsonPath("$.data.identityDocuments[1].documentType", Is.is("PASSPORT")))
                .andExpect(jsonPath("$.data.identityDocuments[1].serial", Is.is("########89")))
                .andExpect(jsonPath("$.data.identityDocuments[1].number", Is.is("########10")))
                .andExpect(jsonPath("$.data.identityDocuments[1].dateStart", Is.is("20.12.2020")))
                .andExpect(jsonPath("$.data.identityDocuments[1].firstName", Is.is("Name")))
                .andExpect(jsonPath("$.data.identityDocuments[1].lastName", Is.is("Last")))
                .andExpect(jsonPath("$.data.identityDocuments[1].patronymic", Is.is("patro")))
                .andExpect(jsonPath("$.data.identityDocuments[1].birthday", Is.is("20.12.2020")))
                .andExpect(jsonPath("$.data.identityDocuments[1].gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.identityDocuments[1].isDeprecated", Is.is(false)))

                .andExpect(jsonPath("$.data.polises.length()", Is.is(2)))
                .andExpect(jsonPath("$.data.polises[0].id", Is.is(1)))
                .andExpect(jsonPath("$.data.polises[0].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data.polises[0].number", Is.is("########21")))
                .andExpect(jsonPath("$.data.polises[0].serial", Is.is("########90")))
                .andExpect(jsonPath("$.data.polises[0].dateStart", Is.is("20.02.2020")))
                .andExpect(jsonPath("$.data.polises[0].dateEnd", Is.is("20.02.2020")))
                .andExpect(jsonPath("$.data.polises[0].isDeprecated", Is.is(false)))
                .andExpect(jsonPath("$.data.polises[0].smo.id", Is.is(1)))
                .andExpect(jsonPath("$.data.polises[0].smo.name", Is.is("name")))
                .andExpect(jsonPath("$.data.polises[0].smo.region", Is.is("MOSCOW")))
                .andExpect(jsonPath("$.data.polises[0].smo.code", Is.is("0000")))

                .andExpect(jsonPath("$.data.polises[1].id", Is.is(2)))
                .andExpect(jsonPath("$.data.polises[1].insuranceType", Is.is("OMS")))
                .andExpect(jsonPath("$.data.polises[1].number", Is.is("########21")))
                .andExpect(jsonPath("$.data.polises[1].serial", Is.is("########90")))
                .andExpect(jsonPath("$.data.polises[1].dateStart", Is.is("20.02.2020")))
                .andExpect(jsonPath("$.data.polises[1].dateEnd", Is.is("20.02.2020")))
                .andExpect(jsonPath("$.data.polises[1].isDeprecated", Is.is(true)))
                .andExpect(jsonPath("$.data.polises[1].smo.id", Is.is(2)))
                .andExpect(jsonPath("$.data.polises[1].smo.name", Is.is("name")))
                .andExpect(jsonPath("$.data.polises[1].smo.region", Is.is("MOSCOW_REGION")))
                .andExpect(jsonPath("$.data.polises[1].smo.code", Is.is("0001")))

                .andExpect(jsonPath("$.code", Is.is(200)));
    }
}