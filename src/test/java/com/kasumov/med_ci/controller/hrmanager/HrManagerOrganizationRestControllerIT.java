package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.structure.medicalOrganization.MedicalOrganizationDto;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.enums.RolesEnum;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HrManagerOrganizationRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_organization_rest_controller/getOrganizationWithDepartments.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_organization_rest_controller/getOrganizationWithDepartments_clear.sql")
    public void getOrganizationWithDepartments() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hrmanager@gmail.com", "1", mockMvc);

        mockMvc.perform(get("/api/manager/organization/get/with/departments")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
//               .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(1)))
                .andExpect(jsonPath("$.data.code", Is.is("1123")))
                .andExpect(jsonPath("$.data.name", Is.is("Городская поликлиника")))
                .andExpect(jsonPath("$.data.legalAddress", Is.is("Chelyabinsk")))
                .andExpect(jsonPath("$.data.ogrn", Is.is("202-12")))
                .andExpect(jsonPath("$.data.startDate", Is.is("09.09.2010")))
                .andExpect(jsonPath("$.data.endDate", Is.is("09.09.2099")))
                .andExpect(jsonPath("$.data.fullEmploymentStatusRange", Is.is(223.75)))
                .andExpect(jsonPath("$.data.director.id", Is.is(59)))
                .andExpect(jsonPath("$.data.director.email", Is.is("dirdoctor59@gmail.com")))
                .andExpect(jsonPath("$.data.director.firstName", Is.is("dirdoctor59")))
                .andExpect(jsonPath("$.data.director.lastName", Is.is("dirdoctor59")))
                .andExpect(jsonPath("$.data.director.patronymic", Is.is("dirdoctor59")))
                .andExpect(jsonPath("$.data.director.birthday", Is.is("25.06.1990")))
                .andExpect(jsonPath("$.data.director.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.ioDirector", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.departments.length()", Is.is(4)))

                .andExpect(jsonPath("$.data.departments.[0].id", Is.is(1001)))
                .andExpect(jsonPath("$.data.departments.[0].name", Is.is("Первое детское отделение")))
                .andExpect(jsonPath("$.data.departments.[0].ageType", Is.is("CHILD")))
                .andExpect(jsonPath("$.data.departments.[0].chiefDoctor.id", Is.is(50)))
                .andExpect(jsonPath("$.data.departments.[0].chiefDoctor.email", Is.is("doctorcox50@gmail.com")))
                .andExpect(jsonPath("$.data.departments.[0].chiefDoctor.firstName", Is.is("Percival")))
                .andExpect(jsonPath("$.data.departments.[0].chiefDoctor.lastName", Is.is("Cox")))
                .andExpect(jsonPath("$.data.departments.[0].chiefDoctor.patronymic", Is.is("Ulysses")))
                .andExpect(jsonPath("$.data.departments.[0].chiefDoctor.birthday", Is.is("25.06.1990")))
                .andExpect(jsonPath("$.data.departments.[0].chiefDoctor.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.departments.[0].ioChiefDoctor", Matchers.nullValue()))

                .andExpect(jsonPath("$.data.departments.[1].id", Is.is(1002)))
                .andExpect(jsonPath("$.data.departments.[1].name", Is.is("Второе детское отделение")))
                .andExpect(jsonPath("$.data.departments.[1].ageType", Is.is("CHILD")))
                .andExpect(jsonPath("$.data.departments.[1].chiefDoctor", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.departments.[1].ioChiefDoctor.id", Is.is(51)))
                .andExpect(jsonPath("$.data.departments.[1].ioChiefDoctor.email", Is.is("iochiefdoctor51@gmail.com")))
                .andExpect(jsonPath("$.data.departments.[1].ioChiefDoctor.firstName", Is.is("iochiefdoctor51")))
                .andExpect(jsonPath("$.data.departments.[1].ioChiefDoctor.lastName", Is.is("iochiefdoctor51")))
                .andExpect(jsonPath("$.data.departments.[1].ioChiefDoctor.patronymic", Is.is("iochiefdoctor51")))
                .andExpect(jsonPath("$.data.departments.[1].ioChiefDoctor.birthday", Is.is("25.06.1990")))
                .andExpect(jsonPath("$.data.departments.[1].ioChiefDoctor.gender", Is.is("MALE")))

                .andExpect(jsonPath("$.data.departments.[2].id", Is.is(1003)))
                .andExpect(jsonPath("$.data.departments.[2].name", Is.is("Первое взрослое отделение")))
                .andExpect(jsonPath("$.data.departments.[2].ageType", Is.is("ADULT")))
                .andExpect(jsonPath("$.data.departments.[2].chiefDoctor.id", Is.is(52)))
                .andExpect(jsonPath("$.data.departments.[2].chiefDoctor.email", Is.is("doctorhouse52@gmail.com")))
                .andExpect(jsonPath("$.data.departments.[2].chiefDoctor.firstName", Is.is("Gregory")))
                .andExpect(jsonPath("$.data.departments.[2].chiefDoctor.lastName", Is.is("House")))
                .andExpect(jsonPath("$.data.departments.[2].chiefDoctor.patronymic", Is.is("chiefdoctor52")))
                .andExpect(jsonPath("$.data.departments.[2].chiefDoctor.birthday", Is.is("25.06.1990")))
                .andExpect(jsonPath("$.data.departments.[2].chiefDoctor.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.departments.[2].ioChiefDoctor", Matchers.nullValue()))

                .andExpect(jsonPath("$.data.departments.[3].id", Is.is(1004)))
                .andExpect(jsonPath("$.data.departments.[3].name", Is.is("Второе взрослое отделение")))
                .andExpect(jsonPath("$.data.departments.[3].ageType", Is.is("ADULT")))
                .andExpect(jsonPath("$.data.departments.[3].chiefDoctor", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.departments.[3].ioChiefDoctor.id", Is.is(53)))
                .andExpect(jsonPath("$.data.departments.[3].ioChiefDoctor.email", Is.is("iochiefdoctor53@gmail.com")))
                .andExpect(jsonPath("$.data.departments.[3].ioChiefDoctor.firstName", Is.is("iochiefdoctor53")))
                .andExpect(jsonPath("$.data.departments.[3].ioChiefDoctor.lastName", Is.is("iochiefdoctor53")))
                .andExpect(jsonPath("$.data.departments.[3].ioChiefDoctor.patronymic", Is.is("iochiefdoctor53")))
                .andExpect(jsonPath("$.data.departments.[3].ioChiefDoctor.birthday", Is.is("25.06.1990")))
                .andExpect(jsonPath("$.data.departments.[3].ioChiefDoctor.gender", Is.is("MALE")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_organization_rest_controller/getOrganizationWithDepartmentsChiefAndIoChiefAreNull.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_organization_rest_controller/getOrganizationWithDepartmentsChiefAndIoChiefAreNull_clear.sql")
    public void getOrganizationWithDepartmentsChiefAndIoChiefAreNull() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hrmanager@gmail.com", "1", mockMvc);

        mockMvc.perform(get("/api/manager/organization/get/with/departments")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(1)))
                .andExpect(jsonPath("$.data.code", Is.is("1123")))
                .andExpect(jsonPath("$.data.name", Is.is("Инвитро")))
                .andExpect(jsonPath("$.data.legalAddress", Is.is("Chelyabinsk")))
                .andExpect(jsonPath("$.data.ogrn", Is.is("202-12")))
                .andExpect(jsonPath("$.data.startDate", Is.is("09.09.2010")))
                .andExpect(jsonPath("$.data.endDate", Is.is("09.09.2099")))
                .andExpect(jsonPath("$.data.fullEmploymentStatusRange", Is.is(223.75)))
                .andExpect(jsonPath("$.data.director", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.ioDirector", Matchers.nullValue()))

                .andExpect(jsonPath("$.data.departments.length()", Is.is(4)))
                .andExpect(jsonPath("$.data.departments.[0].id", Is.is(1001)))
                .andExpect(jsonPath("$.data.departments.[0].name", Is.is("Первое детское отделение")))
                .andExpect(jsonPath("$.data.departments.[0].ageType", Is.is("CHILD")))
                .andExpect(jsonPath("$.data.departments.[0].chiefDoctor", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.departments.[0].ioChiefDoctor", Matchers.nullValue()))

                .andExpect(jsonPath("$.data.departments.[1].id", Is.is(1002)))
                .andExpect(jsonPath("$.data.departments.[1].name", Is.is("Второе детское отделение")))
                .andExpect(jsonPath("$.data.departments.[1].ageType", Is.is("CHILD")))
                .andExpect(jsonPath("$.data.departments.[1].chiefDoctor", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.departments.[1].ioChiefDoctor", Matchers.nullValue()))

                .andExpect(jsonPath("$.data.departments.[2].id", Is.is(1003)))
                .andExpect(jsonPath("$.data.departments.[2].name", Is.is("Первое взрослое отделение")))
                .andExpect(jsonPath("$.data.departments.[2].ageType", Is.is("ADULT")))
                .andExpect(jsonPath("$.data.departments.[2].chiefDoctor", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.departments.[2].ioChiefDoctor", Matchers.nullValue()))

                .andExpect(jsonPath("$.data.departments.[3].id", Is.is(1004)))
                .andExpect(jsonPath("$.data.departments.[3].name", Is.is("Второе взрослое отделение")))
                .andExpect(jsonPath("$.data.departments.[3].ageType", Is.is("ADULT")))
                .andExpect(jsonPath("$.data.departments.[3].chiefDoctor", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.departments.[3].ioChiefDoctor", Matchers.nullValue()));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_organization_rest_controller/hrAddDirectorToMedOrganization.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_organization_rest_controller/hrAddDirectorToMedOrganization_clear.sql")
    public void getMedOrganizationWithDirector_noPreviousDirector() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);
        //Организация без Директора и ИО. Назначаем доктора директором.
        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/add/director/{doctorId}", 119, 12)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                // .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(119)))
                .andExpect(jsonPath("$.data.code", Is.is("code119")))
                .andExpect(jsonPath("$.data.name", Is.is("name119")))
                .andExpect(jsonPath("$.data.legalAddress", Is.is("address119")))
                .andExpect(jsonPath("$.data.ogrn", Is.is("ogrn119")))
                .andExpect(jsonPath("$.data.startDate", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data.endDate", Is.is("01.01.2030")))
                .andExpect(jsonPath("$.data.fullEmploymentStatusRange", Is.is(13.50)))
                .andExpect(jsonPath("$.data.director.id", Is.is(12)))
                .andExpect(jsonPath("$.data.director.email", Is.is("doc12@mail.com")))
                .andExpect(jsonPath("$.data.director.firstName", Is.is("doc12")))
                .andExpect(jsonPath("$.data.director.lastName", Is.is("docov12")))
                .andExpect(jsonPath("$.data.director.patronymic", Is.is("docovich12")))
                .andExpect(jsonPath("$.data.director.birthday", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data.director.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.ioDirector", Is.is(Matchers.nullValue())));

        //Проверяем, что у глав.врача стала роль DIRECTOR
        Assertions.assertEquals("DIRECTOR", entityManager.createQuery("""
                SELECT r.name
                FROM Role r
                join User u on r.id = u.role.id
                WHERE u.id=12
                """, String.class).getSingleResult());

        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/add/director/{doctorId}", 199, 12)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Мед.организации с таким id не существует")));

        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/add/director/{doctorId}", 119, 112)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Доктора с таким id не существует")));

        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/add/director/{doctorId}", 119, 14)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Доктор является зав.отделением")));

        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/add/director/{doctorId}", 119, 12)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Доктор уже занимает эту должность")));

        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/add/director/{doctorId}", 119, 16)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("У доктора нет действующего трудового договора")));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_organization_rest_controller/hrAddDirectorToMedOrganization.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_organization_rest_controller/hrAddDirectorToMedOrganization_clear.sql")
    public void getMedOrganizationWithDirector_wasPreviousDirector() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);
        //Переназначаем организации директора (был старый директор). ИО был и остался
        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/add/director/{doctorId}", 117, 12)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                // .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(117)))
                .andExpect(jsonPath("$.data.code", Is.is("code117")))
                .andExpect(jsonPath("$.data.name", Is.is("name117")))
                .andExpect(jsonPath("$.data.legalAddress", Is.is("address117")))
                .andExpect(jsonPath("$.data.ogrn", Is.is("ogrn117")))
                .andExpect(jsonPath("$.data.startDate", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data.endDate", Is.is("01.01.2030")))
                .andExpect(jsonPath("$.data.fullEmploymentStatusRange", Is.is(13.50)))
                .andExpect(jsonPath("$.data.director.id", Is.is(12)))
                .andExpect(jsonPath("$.data.director.email", Is.is("doc12@mail.com")))
                .andExpect(jsonPath("$.data.director.firstName", Is.is("doc12")))
                .andExpect(jsonPath("$.data.director.lastName", Is.is("docov12")))
                .andExpect(jsonPath("$.data.director.patronymic", Is.is("docovich12")))
                .andExpect(jsonPath("$.data.director.birthday", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data.director.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.ioDirector.id", Is.is(17)))
                .andExpect(jsonPath("$.data.ioDirector.email", Is.is("doc17@mail.com")))
                .andExpect(jsonPath("$.data.ioDirector.firstName", Is.is("doc17")))
                .andExpect(jsonPath("$.data.ioDirector.lastName", Is.is("docov17")))
                .andExpect(jsonPath("$.data.ioDirector.patronymic", Is.is("docovich17")))
                .andExpect(jsonPath("$.data.ioDirector.birthday", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data.ioDirector.gender", Is.is("MALE")));


        //Проверяем, что у глав.врача стала роль DIRECTOR
        Assertions.assertEquals("DIRECTOR", entityManager.createQuery("""
                SELECT r.name
                FROM Role r
                join User u on r.id = u.role.id
                WHERE u.id=12
                """, String.class).getSingleResult());
        //Проверяем, что у прежнего глав.врача стала роль DOCTOR
        Assertions.assertEquals("DOCTOR", entityManager.createQuery("""
                SELECT r.name
                FROM Role r
                join User u on r.id = u.role.id
                WHERE u.id=18
                """, String.class).getSingleResult());

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_organization_rest_controller/hrAddDirectorToMedOrganization.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_organization_rest_controller/hrAddDirectorToMedOrganization_clear.sql")
    public void getMedOrganizationWithDirector_ioDirectorAsNewDirector() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);
        //У организации был и директор и ио. Новым директором ставим ИО. Вместо ИО будет null
        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/add/director/{doctorId}", 119, 17)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(119)))
                .andExpect(jsonPath("$.data.code", Is.is("code119")))
                .andExpect(jsonPath("$.data.name", Is.is("name119")))
                .andExpect(jsonPath("$.data.legalAddress", Is.is("address119")))
                .andExpect(jsonPath("$.data.ogrn", Is.is("ogrn119")))
                .andExpect(jsonPath("$.data.startDate", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data.endDate", Is.is("01.01.2030")))
                .andExpect(jsonPath("$.data.fullEmploymentStatusRange", Is.is(13.50)))
                .andExpect(jsonPath("$.data.director.id", Is.is(17)))
                .andExpect(jsonPath("$.data.director.email", Is.is("doc17@mail.com")))
                .andExpect(jsonPath("$.data.director.firstName", Is.is("doc17")))
                .andExpect(jsonPath("$.data.director.lastName", Is.is("docov17")))
                .andExpect(jsonPath("$.data.director.patronymic", Is.is("docovich17")))
                .andExpect(jsonPath("$.data.director.birthday", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data.director.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.ioDirector", Is.is(Matchers.nullValue())));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hr_manager_organization_rest_controller/removeDirector.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hr_manager_organization_rest_controller/removeDirector_clear.sql")
    public void removeDirectorT() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hrmanager@gmail.com", "1", mockMvc);
        mockMvc.perform(
                patch("/api/manager/organization/{organizationId}/remove/director", 1)
                        .header("Authorization", accessToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(1)))
                .andExpect(jsonPath("$.data.code", Is.is("1123")))
                .andExpect(jsonPath("$.data.name", Is.is("Городская поликлиника")))
                .andExpect(jsonPath("$.data.legalAddress", Is.is("Chelyabinsk")))
                .andExpect(jsonPath("$.data.ogrn", Is.is("202-12")))
                .andExpect(jsonPath("$.data.startDate", Is.is("09.09.2010")))
                .andExpect(jsonPath("$.data.endDate", Is.is("09.09.2099")))
                .andExpect(jsonPath("$.data.fullEmploymentStatusRange", Is.is(223.75)))
                .andExpect(jsonPath("$.data.director", Is.is(Matchers.nullValue())));

        Assertions.assertEquals(RolesEnum.DOCTOR.name(), entityManager.createQuery("""
                SELECT r.name
                FROM Role r
                join User u on r.id = u.role.id
                WHERE u.id=59
                """, String.class).getSingleResult());

        Assertions.assertNull(entityManager.createQuery("""
                SELECT m FROM MedicalOrganization m WHERE m.id = 1
                """, MedicalOrganization.class)
                .getSingleResult()
                .getDirector());

        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/remove/director", 2)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Медицинской организации с таким id не существует")));

        mockMvc.perform(
                        patch("/api/manager/organization/{organizationId}/remove/director", 3)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("У медицинской организации нет главного врача")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_organization_rest_controller/hrUpdateNameToMedOrganization.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hrmanager_organization_rest_controller/hrUpdateNameToMedOrganization_clear.sql")
    public void updateMedicalOrganization() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        MedicalOrganizationDto organizationDto =
                MedicalOrganizationDto.builder()
                        .id(119L)
                        .code("12345678910")
                        .name("Государственное учреждение имени очень не важного человека")
                        .legalAddress("Novosibirsk")
                        .ogrn("00-2235")
                        .startDate("01.01.1992")
                        .endDate("01.01.2030")
                        .fullEmploymentStatusRange(BigDecimal.valueOf(18.5))
                        .build();

        mockMvc.perform(
                        patch("/api/manager/organization/update")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(organizationDto))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(119)))
                .andExpect(jsonPath("$.data.code", Is.is("12345678910")))
                .andExpect(jsonPath("$.data.name", Is.is("Государственное учреждение имени очень не важного человека")))
                .andExpect(jsonPath("$.data.legalAddress", Is.is("Novosibirsk")))
                .andExpect(jsonPath("$.data.ogrn", Is.is("00-2235")))
                .andExpect(jsonPath("$.data.startDate", Is.is("01.01.1992")))
                .andExpect(jsonPath("$.data.endDate", Is.is("01.01.2030")))
                .andExpect(jsonPath("$.data.fullEmploymentStatusRange", Is.is(18.5)))
                .andExpect(jsonPath("$.data.director.id", Is.is(16)))
                .andExpect(jsonPath("$.data.ioDirector.id", Is.is(16)))
                .andExpect(jsonPath("$.data.director.id", Is.is(16)))
                .andExpect(jsonPath("$.data.director.email", Is.is("doc16@mail.com")))
                .andExpect(jsonPath("$.data.director.firstName", Is.is("doc16")))
                .andExpect(jsonPath("$.data.director.lastName", Is.is("docov16")))
                .andExpect(jsonPath("$.data.director.patronymic", Is.is("docovich16")))
                .andExpect(jsonPath("$.data.director.birthday", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data.director.gender", Is.is("MALE")));

        organizationDto =
                MedicalOrganizationDto.builder()
                        .id(222)
                        .code("12345678910")
                        .name("Государственное учреждение имени Важного человека")
                        .legalAddress("Novosibirsk")
                        .ogrn("00-2235")
                        .startDate("01.01.1992")
                        .endDate("01.01.2030")
                        .fullEmploymentStatusRange(BigDecimal.valueOf(18.5))
                        .build();

        mockMvc.perform(
                        patch("/api/manager/organization/update")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(organizationDto))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Мед.организации с таким id не существует")));
    }
}
