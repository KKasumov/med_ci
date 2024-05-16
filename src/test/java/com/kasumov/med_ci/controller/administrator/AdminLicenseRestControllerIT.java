package com.kasumov.med_ci.controller.administrator;

import com.kasumov.med_ci.model.dto.structure.license.LicenseForCreateDto;
import com.kasumov.med_ci.model.dto.structure.license.LicenseForUpdateDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminLicenseRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/admin/admin_crud_oper_with_license/admin_license.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/admin/admin_crud_oper_with_license/admin_license_clear.sql")
    public void createLicense() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        LicenseForCreateDto licenseDto =
                LicenseForCreateDto
                        .builder()
                        .name("Лицензия на обучение интернов")
                        .number("37653135")
                        .startDate("01.01.2024")
                        .endDate("31.12.2028")
                        .medicalOrganizationId(119L)
                        .build();

        mockMvc.perform(
                        post("/api/admin/license/create")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(licenseDto))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", Is.is("Лицензия на обучение интернов")))
                .andExpect(jsonPath("$.data.number", Is.is("37653135")))
                .andExpect(jsonPath("$.data.startDate", Is.is("01.01.2024")))
                .andExpect(jsonPath("$.data.endDate", Is.is("31.12.2028")));

        mockMvc.perform(
                get("/api/admin/license/getAll")
                        .header("Authorization", accessToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].number", Is.is("12585457")))
                .andExpect(jsonPath("$.data[1].number", Is.is("3141541")))
                .andExpect(jsonPath("$.data[2].number", Is.is("37653135")))
                .andExpect(jsonPath("$.data[3].number", Is.is("7567446")))
                .andExpect(jsonPath("$.data[0].name", Is.is("Лицензия на пользование туалета")))
                .andExpect(jsonPath("$.data[1].name", Is.is("Лицензия на осуществление врачебной деятельности")))
                .andExpect(jsonPath("$.data[2].name", Is.is("Лицензия на обучение интернов")))
                .andExpect(jsonPath("$.data[3].name", Is.is("Лицензия на пользование WINDOW12")))
                .andExpect(jsonPath("$.data[0].startDate", Is.is("01.01.2020")))
                .andExpect(jsonPath("$.data[1].startDate", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data[2].startDate", Is.is("01.01.2024")))
                .andExpect(jsonPath("$.data[3].startDate", Is.is("01.01.2010")))
                .andExpect(jsonPath("$.data[0].endDate", Is.is("01.01.2028")))
                .andExpect(jsonPath("$.data[1].endDate", Is.is("01.01.2035")))
                .andExpect(jsonPath("$.data[2].endDate", Is.is("31.12.2028")))
                .andExpect(jsonPath("$.data[3].endDate", Is.is("01.01.2024")));

        licenseDto =
                LicenseForCreateDto
                        .builder()
                        .name("Лицензия на обучение интернов")
                        .number("37653135")
                        .startDate("01.01.2024")
                        .endDate("31.12.2028")
                        .medicalOrganizationId(145L)
                        .build();

        mockMvc.perform(
                        post("/api/admin/license/create")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(licenseDto))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("MedicalOrganization by id not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/admin/admin_crud_oper_with_license/admin_license.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/admin/admin_crud_oper_with_license/admin_license_clear.sql")
    public void updateLicense() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        LicenseForUpdateDto licenseDto =
                LicenseForUpdateDto
                        .builder()
                        .id(196)
                        .name("Лицензия на осуществление врачебной деятельности 2.0")
                        .number("37653135x2")
                        .startDate("01.01.2024")
                        .endDate("30.06.2029")
                        .build();

        mockMvc.perform(
                        patch("/api/admin/license/update/")
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(licenseDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(196)))
                .andExpect(jsonPath("$.data.name", Is.is("Лицензия на осуществление врачебной деятельности 2.0")))
                .andExpect(jsonPath("$.data.number", Is.is("37653135x2")))
                .andExpect(jsonPath("$.data.startDate", Is.is("01.01.2024")))
                .andExpect(jsonPath("$.data.endDate", Is.is("30.06.2029")));

        licenseDto =
                LicenseForUpdateDto
                        .builder()
                        .id(250)
                        .name("Лицензия на нелегальную деятельность")
                        .number("64577243")
                        .startDate("01.01.2024")
                        .endDate("30.06.2029")
                        .build();

        mockMvc.perform(
                patch("/api/admin/license/update/")
                        .header("Authorization", accessToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(licenseDto))
        )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("License by id not found")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/admin/admin_crud_oper_with_license/admin_license.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/admin/admin_crud_oper_with_license/admin_license_clear.sql")
    public void deleteLicense() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        mockMvc.perform(
                delete("/api/admin/license/delete/{id}", 196)
                        .header("Authorization", accessToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Is.is(200)));

        mockMvc.perform(
                        delete("/api/admin/license/delete/{id}", 205)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Entity by id not found")));
    }


}
