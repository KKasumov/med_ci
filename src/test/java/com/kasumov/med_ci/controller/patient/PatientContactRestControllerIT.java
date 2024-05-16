package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatientContactRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/patient/patient_contact_rest_controller/getPatientContacts.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/patient/patient_contact_rest_controller/getPatientContacts_clear.sql")

    public void getPatientContactsIT() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/patient/contact/get/all")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(2)))
                .andExpect(jsonPath("$.data.contacts", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.contacts[0].contactType", Is.is("TELEGRAM")))
                .andExpect(jsonPath("$.data.contacts[0].value", Is.is("13235######")))
                .andExpect(jsonPath("$.data.contacts[1].contactType", Is.is("PHONE")))
                .andExpect(jsonPath("$.data.contacts[1].value", Is.is("8915#####88")))
                .andExpect(jsonPath("$.data.contacts[2].contactType", Is.is("ADDRESS")))
                .andExpect(jsonPath("$.data.contacts[2].value", Is.is("23090905#########")))
                .andExpect(jsonPath("$.data.email", Is.is("iva#@mail.com")));

    }
}