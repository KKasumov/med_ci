package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientAppealRestControllerIT extends ContextIT {
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/patient/patient_appeal_rest_controller/getCompleteInformationOnAppeal.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/patient/patient_appeal_rest_controller/getCompleteInformationOnAppeal_clear.sql")
    public void getCompleteInformationOnAppeal() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("pat21@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/patient/appeal/{appealId}/get", 300)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                // .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(300)))
                .andExpect(jsonPath("$.data.closedDate", Is.is("07.04.2023 00:00")))
                .andExpect(jsonPath("$.data.insuranceType", Is.is("DMS")))

                .andExpect(jsonPath("$.data.disease.id", Is.is(500)))
                .andExpect(jsonPath("$.data.disease.identifier", Is.is("identifier0")))
                .andExpect(jsonPath("$.data.disease.name", Is.is("name0")))
                .andExpect(jsonPath("$.data.disease.isDisabled", Is.is(false)))
                .andExpect(jsonPath("$.data.disease.ageType", Is.is("ADULT")))

                .andExpect(jsonPath("$.data.disease.department.id", Is.is(62)))
                .andExpect(jsonPath("$.data.disease.department.name", Is.is("department1")))
                .andExpect(jsonPath("$.data.disease.department.ageType", nullValue()))

                .andExpect(jsonPath("$.data.visits[0].id", Is.is(400)))
                .andExpect(jsonPath("$.data.visits[0].dayOfVisit", Is.is("10.02.2023 00:00")))
                .andExpect(jsonPath("$.data.visits[0].doctor.id", Is.is(17)))
                .andExpect(jsonPath("$.data.visits[0].doctor.firstName", Is.is("doc17")))
                .andExpect(jsonPath("$.data.visits[0].doctor.lastName", Is.is("docov17")))
                .andExpect(jsonPath("$.data.visits[0].doctor.patronymic", Is.is("docovich17 ")))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[0].id", Is.is(1)))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[0].identifier", Is.is("01")))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[0].name", Is.is("service1")))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[1].id", Is.is(2)))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[1].identifier", Is.is("02")))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[1].name", Is.is("service2")))
                .andExpect(jsonPath("$.data.visits[0].money", Is.is(250.00)))

                .andExpect(jsonPath("$.data.visits[1].id", Is.is(401)))
                .andExpect(jsonPath("$.data.visits[1].dayOfVisit", Is.is("10.03.2023 00:00")))
                .andExpect(jsonPath("$.data.visits[1].doctor.id", Is.is(17)))
                .andExpect(jsonPath("$.data.visits[1].doctor.firstName", Is.is("doc17")))
                .andExpect(jsonPath("$.data.visits[1].doctor.lastName", Is.is("docov17")))
                .andExpect(jsonPath("$.data.visits[1].doctor.patronymic", Is.is("docovich17 ")))
                .andExpect(jsonPath("$.data.visits[1].medicalServices[0].id", Is.is(2)))
                .andExpect(jsonPath("$.data.visits[1].medicalServices[0].identifier", Is.is("02")))
                .andExpect(jsonPath("$.data.visits[1].medicalServices[0].name", Is.is("service2")))
                .andExpect(jsonPath("$.data.visits[1].money", Is.is(100.00)))

                .andExpect(jsonPath("$.data.visits[2].id", Is.is(402)))
                .andExpect(jsonPath("$.data.visits[2].dayOfVisit", Is.is("04.04.2023 00:00")))
                .andExpect(jsonPath("$.data.visits[2].doctor.id", Is.is(17)))
                .andExpect(jsonPath("$.data.visits[2].doctor.firstName", Is.is("doc17")))
                .andExpect(jsonPath("$.data.visits[2].doctor.lastName", Is.is("docov17")))
                .andExpect(jsonPath("$.data.visits[2].doctor.patronymic", Is.is("docovich17 ")))
                .andExpect(jsonPath("$.data.visits[2].medicalServices[0].id", Is.is(3)))
                .andExpect(jsonPath("$.data.visits[2].medicalServices[0].identifier", Is.is("03")))
                .andExpect(jsonPath("$.data.visits[2].medicalServices[0].name", Is.is("service3")))
                .andExpect(jsonPath("$.data.visits[2].money", Is.is(175.00)))
                .andExpect(jsonPath("$.data.money", Is.is(525.00)));

        // проверяем, что обращение существует
        mockMvc.perform(
                        get("/api/patient/appeal/{appealId}/get", 3000)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Обращение не существует")));

        // проверяем, что обращение принадлежит пациенту
        mockMvc.perform(
                        get("/api/patient/appeal/{appealId}/get", 303)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Обращение не принадлежит пациенту")));

        accessToken = tokenUtil.obtainNewAccessToken("pat22@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/patient/appeal/{appealId}/get", 305)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(305)))
                .andExpect(jsonPath("$.data.closedDate", Is.is("09.04.2023 00:00")))
                .andExpect(jsonPath("$.data.insuranceType", Is.is("OMS")))

                .andExpect(jsonPath("$.data.disease.id", Is.is(504)))
                .andExpect(jsonPath("$.data.disease.identifier", Is.is("identifier4")))
                .andExpect(jsonPath("$.data.disease.name", Is.is("caries")))
                .andExpect(jsonPath("$.data.disease.isDisabled", Is.is(false)))
                .andExpect(jsonPath("$.data.disease.ageType", Is.is("ADULT")))

                .andExpect(jsonPath("$.data.disease.department.id", Is.is(62)))
                .andExpect(jsonPath("$.data.disease.department.name", Is.is("department1")))
                .andExpect(jsonPath("$.data.disease.department.ageType", nullValue()))

                .andExpect(jsonPath("$.data.visits[0].dayOfVisit", Is.is("20.03.2023 00:00")))
                .andExpect(jsonPath("$.data.visits[0].doctor.id", Is.is(17)))
                .andExpect(jsonPath("$.data.visits[0].doctor.firstName", Is.is("doc17")))
                .andExpect(jsonPath("$.data.visits[0].doctor.lastName", Is.is("docov17")))
                .andExpect(jsonPath("$.data.visits[0].doctor.patronymic", Is.is("docovich17 ")))

                .andExpect(jsonPath("$.data.visits[0].medicalServices[0].id", Is.is(4)))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[0].identifier", Is.is("04")))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[0].name", Is.is("cleaningToothCavity")))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[1].id", Is.is(5)))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[1].identifier", Is.is("05")))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[1].name", Is.is("nerveRemoval")))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[2].id", Is.is(6)))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[2].identifier", Is.is("06")))
                .andExpect(jsonPath("$.data.visits[0].medicalServices[2].name", Is.is("temporaryFilling")))
                .andExpect(jsonPath("$.data.visits[0].money", Is.is(984.8)))

                .andExpect(jsonPath("$.data.visits[1].id", Is.is(406)))
                .andExpect(jsonPath("$.data.visits[1].dayOfVisit", Is.is("28.03.2023 00:00")))
                .andExpect(jsonPath("$.data.visits[1].doctor.id", Is.is(17)))
                .andExpect(jsonPath("$.data.visits[1].doctor.firstName", Is.is("doc17")))
                .andExpect(jsonPath("$.data.visits[1].doctor.lastName", Is.is("docov17")))
                .andExpect(jsonPath("$.data.visits[1].doctor.patronymic", Is.is("docovich17 ")))

                .andExpect(jsonPath("$.data.visits[1].medicalServices[0].id", Is.is(4)))
                .andExpect(jsonPath("$.data.visits[1].medicalServices[0].identifier", Is.is("04")))
                .andExpect(jsonPath("$.data.visits[1].medicalServices[0].name", Is.is("cleaningToothCavity")))
                .andExpect(jsonPath("$.data.visits[1].medicalServices[1].id", Is.is(8)))
                .andExpect(jsonPath("$.data.visits[1].medicalServices[1].identifier", Is.is("08")))
                .andExpect(jsonPath("$.data.visits[1].medicalServices[1].name", Is.is("permanentFilling")))
                .andExpect(jsonPath("$.data.visits[1].money", Is.is(1169.45)))

                .andExpect(jsonPath("$.data.visits[2].id", Is.is(407)))
                .andExpect(jsonPath("$.data.visits[2].dayOfVisit", Is.is("03.04.2023 00:00")))
                .andExpect(jsonPath("$.data.visits[2].doctor.id", Is.is(17)))
                .andExpect(jsonPath("$.data.visits[2].doctor.firstName", Is.is("doc17")))
                .andExpect(jsonPath("$.data.visits[2].doctor.lastName", Is.is("docov17")))
                .andExpect(jsonPath("$.data.visits[2].doctor.patronymic", Is.is("docovich17 ")))

                .andExpect(jsonPath("$.data.visits[2].medicalServices[0].id", Is.is(7)))
                .andExpect(jsonPath("$.data.visits[2].medicalServices[0].identifier", Is.is("07")))
                .andExpect(jsonPath("$.data.visits[2].medicalServices[0].name", Is.is("temporaryFillingRemoval")))
                .andExpect(jsonPath("$.data.visits[2].medicalServices[1].id", Is.is(8)))
                .andExpect(jsonPath("$.data.visits[2].medicalServices[1].identifier", Is.is("08")))
                .andExpect(jsonPath("$.data.visits[2].medicalServices[1].name", Is.is("permanentFilling")))
                .andExpect(jsonPath("$.data.visits[2].money", Is.is(1011.37)))

                .andExpect(jsonPath("$.data.money", Is.is(3165.62)));


    }
}
