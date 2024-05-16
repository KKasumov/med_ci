package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientDepartmentRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/patient/patient_department_rest_controller/getAllDepartmentsWithDoctors.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/patient/patient_department_rest_controller/getAllDepartmentsWithDoctors_clear.sql")
    public void getAllDepartmentsWithDoctors() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "1", mockMvc);

        mockMvc.perform(
                        get("/api/patient/department/get/all/with/doctors")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", Is.is(12)))
                .andExpect(jsonPath("$.[0].name", Is.is("Первое детское отделение")))
                .andExpect(jsonPath("$.[0].ageType", Is.is("CHILD")))

                .andExpect(jsonPath("$.[0].doctors.length()", Is.is(2)))
                .andExpect(jsonPath("$.[0].doctors[0].id", Is.is(30)))
                .andExpect(jsonPath("$.[0].doctors[0].lastName", Is.is("Pushkin")))
                .andExpect(jsonPath("$.[0].doctors[0].firstName", Is.is("Alexander")))
                .andExpect(jsonPath("$.[0].doctors[0].patronymic", Is.is("Sergeevich")))
                .andExpect(jsonPath("$.[0].doctors[0].gender", Is.is("MALE")))

                .andExpect(jsonPath("$.[0].doctors[1].id", Is.is(40)))
                .andExpect(jsonPath("$.[0].doctors[1].lastName", Is.is("Bunin")))
                .andExpect(jsonPath("$.[0].doctors[1].firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.[0].doctors[1].patronymic", Is.is("Alekseevich")))
                .andExpect(jsonPath("$.[0].doctors[1].gender", Is.is("MALE")))

                .andExpect(jsonPath("$.[1].name", Is.is("Второе детское отделение")))
                .andExpect(jsonPath("$.[1].ageType", Is.is("CHILD")))

                .andExpect(jsonPath("$.[1].doctors.length()", Is.is(1)))
                .andExpect(jsonPath("$.[1].doctors[0].id", Is.is(70)))
                .andExpect(jsonPath("$.[1].doctors[0].lastName", Is.is("Tsvetaeva")))
                .andExpect(jsonPath("$.[1].doctors[0].firstName", Is.is("Marina")))
                .andExpect(jsonPath("$.[1].doctors[0].patronymic", Is.is("Ivanovna")))
                .andExpect(jsonPath("$.[1].doctors[0].gender", Is.is("FEMALE")));
    }
}