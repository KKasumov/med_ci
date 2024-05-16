package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static com.kasumov.med_ci.model.enums.IdentityDocumentType.PASSPORT;
import static com.kasumov.med_ci.model.enums.Gender.MALE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientIdentityRestControllerIT extends ContextIT {
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/patient/patient_identity_rest_controller/saveNewIdentity.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/patient/patient_identity_rest_controller/saveNewIdentity_clear.sql")
    public void saveNewIdentity() throws Exception {
        NewIdentityDocumentDto newIdentityDocumentDto = NewIdentityDocumentDto.builder()
                .documentType(PASSPORT)
                .serial("0002")
                .number("111111111")
                .dateStart("01.01.2014")
                .firstName("Ivan")
                .lastName("Ivanov")
                .patronymic("Ivanovich")
                .birthday("13.02.1991")
                .gender(MALE)
                .build();
        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "100", mockMvc);
        mockMvc.perform(
                        post("/api/patient/identity/add/new")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(newIdentityDocumentDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[0].id", Is.is(50001)))
                .andExpect(jsonPath("$.data[0].documentType", Is.is("PASSPORT")))
                .andExpect(jsonPath("$.data[0].serial", Is.is("##00")))
                .andExpect(jsonPath("$.data[0].number", Is.is("#######10")))
                .andExpect(jsonPath("$.data[0].dateStart", Is.is("01.01.2012")))
                .andExpect(jsonPath("$.data[0].firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.data[0].lastName", Is.is("Ivanov")))
                .andExpect(jsonPath("$.data[0].patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data[0].birthday", Is.is("13.02.1991")))
                .andExpect(jsonPath("$.data[0].isDeprecated", Is.is(true)))
                .andExpect(jsonPath("$.data[0].gender", Is.is("MALE")))

                .andExpect(jsonPath("$.data[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[1].documentType", Is.is("PASSPORT")))
                .andExpect(jsonPath("$.data[1].serial", Is.is("##02")))
                .andExpect(jsonPath("$.data[1].number", Is.is("#######11")))
                .andExpect(jsonPath("$.data[1].dateStart", Is.is("01.01.2014")))
                .andExpect(jsonPath("$.data[1].firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.data[1].lastName", Is.is("Ivanov")))
                .andExpect(jsonPath("$.data[1].patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data[1].birthday", Is.is("13.02.1991")))
                .andExpect(jsonPath("$.data[1].isDeprecated", Is.is(false)))
                .andExpect(jsonPath("$.data[1].gender", Is.is("MALE")));


    }

}
