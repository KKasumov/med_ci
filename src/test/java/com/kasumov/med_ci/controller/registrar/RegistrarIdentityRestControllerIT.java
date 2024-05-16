package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static com.kasumov.med_ci.model.enums.Gender.MALE;
import static com.kasumov.med_ci.model.enums.IdentityDocumentType.PASSPORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrarIdentityRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_identity_rest_controller/registrarAddNewIdentityDocument.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_identity_rest_controller/registrarAddNewIdentityDocument_clear.sql")
    public void registrarAddNewIdentityDocument() throws Exception {

        NewIdentityDocumentDto newIdentityDocumentDto = NewIdentityDocumentDto.builder()
                .documentType(PASSPORT)
                .serial("1111")
                .number("111111111")
                .dateStart("01.01.2001")
                .firstName("f_patient_2")
                .lastName("l_patient_2")
                .patronymic("p_patient_2")
                .birthday("10.02.1990")
                .gender(MALE)
                .build();

        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(
                        post("/api/registrar/identity/add/new/for/patient/{patientId}", 10)
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(newIdentityDocumentDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[0].id", Is.is(1111)))
                .andExpect(jsonPath("$.data[0].documentType", Is.is("PASSPORT")))
                .andExpect(jsonPath("$.data[0].serial", Is.is("##00")))
                .andExpect(jsonPath("$.data[0].number", Is.is("#######00")))
                .andExpect(jsonPath("$.data[0].dateStart", Is.is("02.02.2020")))
                .andExpect(jsonPath("$.data[0].firstName", Is.is("f_patient")))
                .andExpect(jsonPath("$.data[0].lastName", Is.is("l_patient")))
                .andExpect(jsonPath("$.data[0].patronymic", Is.is("p_patient")))
                .andExpect(jsonPath("$.data[0].birthday", Is.is("08.08.1888")))
                .andExpect(jsonPath("$.data[0].isDeprecated", Is.is(true)))
                .andExpect(jsonPath("$.data[0].gender", Is.is("MALE")))

                .andExpect(jsonPath("$.data[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data[1].documentType", Is.is("PASSPORT")))
                .andExpect(jsonPath("$.data[1].serial", Is.is("##11")))
                .andExpect(jsonPath("$.data[1].number", Is.is("#######11")))
                .andExpect(jsonPath("$.data[1].dateStart", Is.is("01.01.2001")))
                .andExpect(jsonPath("$.data[1].firstName", Is.is("f_patient_2")))
                .andExpect(jsonPath("$.data[1].lastName", Is.is("l_patient_2")))
                .andExpect(jsonPath("$.data[1].patronymic", Is.is("p_patient_2")))
                .andExpect(jsonPath("$.data[1].birthday", Is.is("10.02.1990")))
                .andExpect(jsonPath("$.data[1].isDeprecated", Is.is(false)))
                .andExpect(jsonPath("$.data[1].gender", Is.is("MALE")));

        mockMvc.perform(
                post("/api/registrar/identity/add/new/for/patient/{patientId}", 102)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newIdentityDocumentDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Пациента с таким id не существует")));

    }
}
