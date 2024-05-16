package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.dto.structure.department.NewDepartmentDto;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class HrManagerDepartmentRestControllerIT extends ContextIT {
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_department_rest_controller/HrManagerDeleteDepartmentById.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_department_rest_controller/HrManagerDeleteDepartmentById_clear.sql")
    public void deleteDepartmentByIdTest() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);


        // проверка, что не удаляется из-за отсутствия отделения с таким id

        mockMvc.perform(delete("/api/manager/department/{departmentId}/delete", 999999999)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Отделения не существует")));


        // проверка, что не удаляется из-за наличия сотрудников

        mockMvc.perform(delete("/api/manager/department/{departmentId}/delete", 1111)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("У отделения есть сотрудники")));


        // проверка, что не удаляется из-за наличия заболеваний

        mockMvc.perform(delete("/api/manager/department/{departmentId}/delete", 2222)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("У отделения есть заболевания")));


        // проверка, на удаление отделения

        mockMvc.perform(delete("/api/manager/department/{departmentId}/delete", 3333)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200));


        // проверка, что отделение удалилось

        Assertions.assertEquals(0, entityManager.createQuery("""
                SELECT COUNT(d)
                FROM Department d
                WHERE d.id = 3333
                """, Long.class).getSingleResult());


        // проверка, что мед услуги связанные с отделением удалились

        Assertions.assertEquals(0, entityManager.createQuery("""
                SELECT COUNT(ms)
                FROM MedicalService ms
                WHERE ms.department.id = 3333
                """, Long.class).getSingleResult());


        // проверка, что ОМС сервисы связанные с мед услугами удалились

        Assertions.assertEquals(0, entityManager.createQuery("""
                SELECT COUNT(opoms)
                FROM OmsPriceOfMedicalService opoms
                WHERE opoms.medicalService.id = 1
                """, Long.class).getSingleResult());


        // проверка, что платные сервисы связанные с мед услугами удалились

        Assertions.assertEquals(0, entityManager.createQuery("""
                SELECT COUNT(ppoms)
                FROM PayPriceOfMedicalService ppoms
                WHERE ppoms.medicalService.id = 1
                """, Long.class).getSingleResult());


        // проверка, что все связанные с отделением должности удалились

        Assertions.assertEquals(0, entityManager.createQuery("""
                SELECT COUNT(p)
                FROM Position p
                WHERE p.department.id = 3333
                """, Long.class).getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerUpdateDepartment.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerUpdateDepartment_clear.sql")
    public void hrmanagerUpdateDepartment() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        // проверка обновления несуществующего отделения

        DepartmentDto departmentDto = DepartmentDto.builder()
                .id(10000)
                .name("OTHER")
                .ageType(AgeType.NO)
                .build();

        mockMvc.perform(patch("/api/manager/department/update")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(departmentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Отделение с таким ID не найдено")));

        // проверка, что не обновляется из-за наличия сотрудников

        DepartmentDto departmentDto1 = DepartmentDto.builder()
                .id(1111)
                .name("OTHER")
                .ageType(AgeType.NO)
                .build();

        mockMvc.perform(patch("/api/manager/department/update")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(departmentDto1))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Изменение типа отделения запрещено, т.к. с ним связан хоть один доктор")));

        // проверка, что не удаляется из-за наличия заболевания

        DepartmentDto departmentDto2 = DepartmentDto.builder()
                .id(2222)
                .name("OTHER")
                .ageType(AgeType.NO)
                .build();

        mockMvc.perform(patch("/api/manager/department/update")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(departmentDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Изменение типа отделения запрещено, т.к. с ним связано хоть одно заболевание")));


        // проверка, что не удаляется из-за наличия услуги

        DepartmentDto departmentDto3 = DepartmentDto.builder()
                .id(3333)
                .name("OTHER")
                .ageType(AgeType.NO)
                .build();

        mockMvc.perform(patch("/api/manager/department/update")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(departmentDto3))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                //.andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Изменение типа отделения запрещено, т.к. с ним связана хоть одна медицинская услуга")));


        // проверка обновления отделения

        DepartmentDto departmentDto4 = DepartmentDto.builder()
                .id(4444)
                .name("name4")
                .ageType(AgeType.ADULT)
                .build();

        mockMvc.perform(patch("/api/manager/department/update")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(departmentDto4))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(4444)))
                .andExpect(jsonPath("$.data.name", Is.is("name4")))
                .andExpect(jsonPath("$.data.ageType", Is.is(AgeType.ADULT.name())))
                .andExpect(jsonPath("$.data.chiefDoctor", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.ioChiefDoctor", Matchers.nullValue()))
                .andExpect(jsonPath("$.code", Is.is(200)));

        Assertions.assertEquals("name4", entityManager.createQuery("""
                SELECT d.name
                FROM Department d
                WHERE d.id = 4444
                """).getSingleResult().toString());

        Assertions.assertEquals(AgeType.ADULT.name(), entityManager.createQuery("""
                SELECT d.ageType
                FROM Department d
                WHERE d.id = 4444
                """).getSingleResult().toString());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerAddChiefOfDepartment.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerAddChiefOfDepartment_clear.sql")
    public void hrmanagerAddChiefOfDepartment() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("gena@mail.com", "100", mockMvc);

        //проверка, что срабатывает исключение Отделения с таким id не существует
        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/chief/{doctorId}", 88888888, 20)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Отделения с таким id не существует")));

        //проверка, что срабатывает исключение Доктора с таким id не существует
        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/chief/{doctorId}", 12, 201)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Доктора с таким id не существует")));

        //проверка, что срабатывает исключение У доктора с таким id  роль DIRECTOR

        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/chief/{doctorId}", 12, 30)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(456)))
                .andExpect(jsonPath("$.text", Is.is("У доктора с таким id  роль DIRECTOR")));

        //проверка, что срабатывает исключение Доктор работает в другом отделении

        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/chief/{doctorId}", 13, 40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(456)))
                .andExpect(jsonPath("$.text", Is.is("Доктор работает в другом отделении")));

        //проверка, что срабатывает исключение Доктор уже назначен заведующим отделением

        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/chief/{doctorId}", 13, 50)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(455)))
                .andExpect(jsonPath("$.text", Is.is("Доктор уже назначен заведующим отделением")));

        //проверка, что срабатывает исключение У доктора нет действующего трудового договора

        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/chief/{doctorId}", 12, 40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("У доктора нет действующего трудового договора")));

        //проверка что контроллер работает

        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/chief/{doctorId}", 13, 60)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Взрослое отделение"))
                .andExpect(jsonPath("$.data.ageType").value("ADULT"))
                .andExpect(jsonPath("$.data.chiefDoctor.id").value(60))
                .andExpect(jsonPath("$.data.ioChiefDoctor").value(Matchers.nullValue()));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerCreateNewDepartment.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerCreateNewDepartment_clear.sql")
    public void hrmanagerCreateNewDepartment() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hrmanager@gmail.com", "1", mockMvc);

        NewDepartmentDto newDepartmentDto = NewDepartmentDto.builder()
                .name("Other Department")
                .ageType(AgeType.CHILD)
                .build();
        //проверка, что срабатывает исключение Организации с таким id не существует
        mockMvc.perform(post("/api/manager/department/add/for/organization/{organizationId}", 88888888)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newDepartmentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Организации с таким ID не существует")));

        // проверка, создание нового департамента
        NewDepartmentDto newDepartmentDto2 = NewDepartmentDto.builder()
                .name("New Department")
                .ageType(AgeType.CHILD)
                .build();

        mockMvc.perform(post("/api/manager/department/add/for/organization/{organizationId}", 1)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newDepartmentDto2))
                        .contentType(MediaType.APPLICATION_JSON)
        )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(Matchers.notNullValue())))
                .andExpect(jsonPath("$.data.name", Is.is("New Department")))
                .andExpect(jsonPath("$.data.ageType", Is.is(AgeType.CHILD.name())))
                .andExpect(jsonPath("$.data.chiefDoctor", Is.is(Matchers.nullValue())))
                .andExpect(jsonPath("$.data.ioChiefDoctor", Is.is(Matchers.nullValue())));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerAddIOChiefOfDepartment.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerAddIOChiefOfDepartment_clear.sql")
    public void hrmanagerAddIOChiefOfDepartment() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("employee@mail.com", "100", mockMvc);

        // проверка, что отделение не существует
        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/io/chief/{doctorId}", 88888888, 20)
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Отделения с таким id не существует")));

        // проверка, что доктора не существует
        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/io/chief/{doctorId}", 12, 300)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Доктора с таким id не существует")));

        // проверка, что доктор уже назначен ио заведующим отделением
        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/io/chief/{doctorId}", 13, 60)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(455)))
                .andExpect(jsonPath("$.text", Is.is("Доктор уже назначен ио заведующего отделением")));

        // проверка, что у доктора роль DIRECTOR
        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/io/chief/{doctorId}", 13, 30)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("У доктора с таким id  роль DIRECTOR")));

        // проверка, что доктор работает в другом отделении
        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/io/chief/{doctorId}", 13, 40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(455)))
                .andExpect(jsonPath("$.text", Is.is("Доктор работает в другом отделении")));

        // проверка, что у доктора нет действующего трудового договора
        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/io/chief/{doctorId}", 12, 40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(455)))
                .andExpect(jsonPath("$.text", Is.is("У доктора нет действующего трудового договора")));

        // контроллер работает
        mockMvc.perform(patch("/api/manager/department/{departmentId}/add/io/chief/{doctorId}", 14, 70)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Первое взрослое отделение"))
                .andExpect(jsonPath("$.data.ageType").value("ADULT"))
                .andExpect(jsonPath("$.data.chiefDoctor").value(Matchers.nullValue()))
                .andExpect(jsonPath("$.data.ioChiefDoctor.id").value(70));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerRemovesIOChiefOfDepartment.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hr_manager_department_rest_controller/hrmanagerRemovesIOChiefOfDepartment_clear.sql")
    public void hrmanagerRemovesChiefOfDepartmentT() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("employee@mail.com", "100", mockMvc);

        mockMvc.perform(patch("/api/manager/department/{departmentId}/delete/chief", 1000)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Отделения с таким id не существует")));

        mockMvc.perform(patch("/api/manager/department/{departmentId}/delete/chief", 12)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Заведущего в отделении не существует")));

                // контроллер работает
        mockMvc.perform(patch("/api/manager/department/{departmentId}/delete/chief", 13)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.name").value("Взрослое отделение"))
                .andExpect(jsonPath("$.data.ageType").value("ADULT"))
                .andExpect(jsonPath("$.data.chiefDoctor").value(Matchers.nullValue()))
                .andExpect(jsonPath("$.data.ioChiefDoctor.id").value(60))
                .andExpect(jsonPath("$.data.ioChiefDoctor.email").value(Matchers.nullValue()))
                .andExpect(jsonPath("$.data.ioChiefDoctor.firstName").value("Maxim"))
                .andExpect(jsonPath("$.data.ioChiefDoctor.lastName").value("Kopanev"))
                .andExpect(jsonPath("$.data.ioChiefDoctor.patronymic").value("Sergeevich"))
                .andExpect(jsonPath("$.data.ioChiefDoctor.birthday").value("1985-09-23"))
                .andExpect(jsonPath("$.data.ioChiefDoctor.gender").value("MALE"));
    }
}
