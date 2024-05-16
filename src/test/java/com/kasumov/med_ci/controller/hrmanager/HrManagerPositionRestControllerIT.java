package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.structure.position.NewPositionDto;
import com.kasumov.med_ci.model.dto.structure.wage.NewWageDto;
import com.kasumov.med_ci.model.dto.structure.wage.WageDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HrManagerPositionRestControllerIT extends ContextIT {


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_position_rest_controller/hrmanagerCreatePositionForDepartment.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_position_rest_controller/hrmanagerCreatePositionForDepartment_clear.sql")
    public void hrmanagerCreatePositionForDepartmentTest() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hrmanager@gmail.com", "1", mockMvc);

        NewWageDto newWageDto = NewWageDto.builder()
                .dateStart("13-06-2022")
                .dateEnd(null)
                .value(BigDecimal.valueOf(51000))
                .build();

        NewPositionDto newPositionDto = NewPositionDto.builder()
                .name("Хирург")
                .daysForVocation(13)
                .cabinetId(5L)
                .equipmentsId(null)
                .wage(newWageDto)
                .build();

        mockMvc.perform(post("/api/manager/position/add/for/department/{departmentId}", 88888888)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newPositionDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Отделения не существует")));

        NewWageDto newWageDto2 = NewWageDto.builder()
                .dateStart("13.06.2022")
                .dateEnd(null)
                .value(BigDecimal.valueOf(51000))
                .build();

        NewPositionDto newPositionDto2 = NewPositionDto.builder()
                .name("Хирург")
                .daysForVocation(13)
                .cabinetId(8L)
                .equipmentsId(null)
                .wage(newWageDto2)
                .build();

        mockMvc.perform(post("/api/manager/position/add/for/department/{departmentId}", 4000)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newPositionDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Кабинета не существует")));

        NewWageDto newWageDto3 = NewWageDto.builder()
                .dateStart("13.06.2022")
                .dateEnd(null)
                .value(BigDecimal.valueOf(51000))
                .build();

        NewPositionDto newPositionDto3 = NewPositionDto.builder()
                .name("Хирург")
                .daysForVocation(13)
                .cabinetId(5L)
                .equipmentsId(Collections.singletonList(12L))
                .wage(newWageDto3)
                .build();

        mockMvc.perform(post("/api/manager/position/add/for/department/{departmentId}", 4000)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newPositionDto3))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Оборудование не существует")));

        NewWageDto newWageDto4 = NewWageDto.builder()
                .dateStart("13.06.2022")
                .dateEnd(null)
                .value(BigDecimal.valueOf(51000))
                .build();

        NewPositionDto newPositionDto4 = NewPositionDto.builder()
                .name("Хирург")
                .daysForVocation(13)
                .cabinetId(5L)
                .equipmentsId(Collections.singletonList(10L))
                .wage(newWageDto4)
                .build();

        mockMvc.perform(post("/api/manager/position/add/for/department/{departmentId}", 4000)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newPositionDto4))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Оборудование занято")));

        NewWageDto newWageDto5 = NewWageDto.builder()
                .dateStart("11.06.2023")
                .dateEnd(null)
                .value(BigDecimal.valueOf(51000))
                .build();

        NewPositionDto newPositionDto5 = NewPositionDto.builder()
                .name("Хирург")
                .daysForVocation(13)
                .cabinetId(5L)
                .equipmentsId(Collections.singletonList(11L))
                .wage(newWageDto5)
                .build();

        mockMvc.perform(post("/api/manager/position/add/for/department/{departmentId}", 4000)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newPositionDto5))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(Matchers.notNullValue())))
                .andExpect(jsonPath("$.data.name", Is.is("Хирург")))
                .andExpect(jsonPath("$.data.daysForVocation", Is.is(13)))
                .andExpect(jsonPath("$.data.cabinet.cabinetId", Is.is(5)))
                .andExpect(jsonPath("$.data.cabinet.cabinetNumber", Is.is(400)))
                .andExpect(jsonPath("$.data.cabinet.buildingId", Is.is(40)))
                .andExpect(jsonPath("$.data.wage.id", Is.is(Matchers.notNullValue())))
                .andExpect(jsonPath("$.data.wage.dateStart", Is.is("2023-06-11")))
                .andExpect(jsonPath("$.data.wage.dateEnd", Is.is("null")))
                .andExpect(jsonPath("$.data.wage.value", Is.is(51000)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_position_rest_controller/getInformationByDepartment.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_position_rest_controller/getInformationByDepartment_clear.sql")
    public void getAllWageByDepartmentT() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);
        mockMvc.perform(get("/api/manager/position/get/information/by/department/{departmentId}",40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(40)))
                .andExpect(jsonPath("$.data.name",Is.is("Первое детское отделение")))
                .andExpect(jsonPath("$.data.ageType",Is.is("CHILD")))
                .andExpect(jsonPath("$.data.positions[0].id",Is.is(4000)))
                .andExpect(jsonPath("$.data.positions[0].name",Is.is("Терапевт")))
                .andExpect(jsonPath("$.data.positions[0].daysForVocation",Is.is(42)))

                .andExpect(jsonPath("$.data.positions[0].cabinet.cabinetId",Is.is(40)))
                .andExpect(jsonPath("$.data.positions[0].cabinet.cabinetNumber",Is.is(566)))
                .andExpect(jsonPath("$.data.positions[0].cabinet.cabinetName",Is.is("RRR")))
                .andExpect(jsonPath("$.data.positions[0].cabinet.buildingId",Is.is(65)))
                .andExpect(jsonPath("$.data.positions[0].cabinet.buildingPhysicalAddress",Is.is("Центральная, д.22, кв.176")))

                .andExpect(jsonPath("$.data.positions[0].employee.employeeHistoryId[0]",Is.is(3500)))
                .andExpect(jsonPath("$.data.positions[0].employee.laborContractId[0]",Is.is(300)))
                .andExpect(jsonPath("$.data.positions[0].employee.user[0].id",Is.is(3000)))
                .andExpect(jsonPath("$.data.positions[0].employee.user[0].email",Is.is("hr@mail.ru")))
                .andExpect(jsonPath("$.data.positions[0].employee.user[0].firstName",Is.is("Ivan")))
                .andExpect(jsonPath("$.data.positions[0].employee.user[0].lastName",Is.is("Ivanov")))
                .andExpect(jsonPath("$.data.positions[0].employee.user[0].patronymic",Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.positions[0].employee.user[0].birthday",Is.is("01.01.1970")))
                .andExpect(jsonPath("$.data.positions[0].employee.user[0].gender",Is.is("MALE")))

                .andExpect(jsonPath("$.data.positions[0].wage.id",Is.is(32)))
                .andExpect(jsonPath("$.data.positions[0].wage.dateStart",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.positions[0].wage.dateEnd",Is.is("13.07.2023")))
                .andExpect(jsonPath("$.data.positions[0].wage.value",Is.is(335)))


                .andExpect(jsonPath("$.data.positions[1].id",Is.is(5000)))
                .andExpect(jsonPath("$.data.positions[1].name",Is.is("Стоматолог")))
                .andExpect(jsonPath("$.data.positions[1].daysForVocation",Is.is(42)))

                .andExpect(jsonPath("$.data.positions[1].cabinet.cabinetId",Is.is(55)))
                .andExpect(jsonPath("$.data.positions[1].cabinet.cabinetNumber",Is.is(777)))
                .andExpect(jsonPath("$.data.positions[1].cabinet.cabinetName",Is.is("456-в")))
                .andExpect(jsonPath("$.data.positions[1].cabinet.buildingId",Is.is(65)))
                .andExpect(jsonPath("$.data.positions[1].cabinet.buildingPhysicalAddress",Is.is("Центральная, д.22, кв.176")))

                .andExpect(jsonPath("$.data.positions[1].employee.employeeHistoryId[0]",Is.is(4500)))
                .andExpect(jsonPath("$.data.positions[1].employee.laborContractId[0]",Is.is(400)))
                .andExpect(jsonPath("$.data.positions[1].employee.user[0].id",Is.is(3500)))
                .andExpect(jsonPath("$.data.positions[1].employee.user[0].email",Is.is("hr@mail1.ru")))
                .andExpect(jsonPath("$.data.positions[1].employee.user[0].firstName",Is.is("Ivan1")))
                .andExpect(jsonPath("$.data.positions[1].employee.user[0].lastName",Is.is("Ivanov1")))
                .andExpect(jsonPath("$.data.positions[1].employee.user[0].patronymic",Is.is("Ivanovich1")))
                .andExpect(jsonPath("$.data.positions[1].employee.user[0].birthday",Is.is("01.01.1970")))
                .andExpect(jsonPath("$.data.positions[1].employee.user[0].gender",Is.is("MALE")))

                .andExpect(jsonPath("$.data.positions[1].wage.id",Is.is(35)))
                .andExpect(jsonPath("$.data.positions[1].wage.dateStart",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.positions[1].wage.dateEnd",Is.is("13.07.2023")))
                .andExpect(jsonPath("$.data.positions[1].wage.value",Is.is(400)))


                .andExpect(jsonPath("$.data.positions[2].id",Is.is(6000)))
                .andExpect(jsonPath("$.data.positions[2].name",Is.is("Техник")))
                .andExpect(jsonPath("$.data.positions[2].daysForVocation",Is.is(42)))

                .andExpect(jsonPath("$.data.positions[2].cabinet.cabinetId",Is.is(65)))
                .andExpect(jsonPath("$.data.positions[2].cabinet.cabinetNumber",Is.is(888)))
                .andExpect(jsonPath("$.data.positions[2].cabinet.cabinetName",Is.is("454-a")))
                .andExpect(jsonPath("$.data.positions[2].cabinet.buildingId",Is.is(65)))
                .andExpect(jsonPath("$.data.positions[2].cabinet.buildingPhysicalAddress",Is.is("Центральная, д.22, кв.176")))

                .andExpect(jsonPath("$.data.positions[2].employee.employeeHistoryId[0]",Is.is(5500)))
                .andExpect(jsonPath("$.data.positions[2].employee.laborContractId[0]",Is.is(500)))
                .andExpect(jsonPath("$.data.positions[2].employee.user[0].id",Is.is(4500)))
                .andExpect(jsonPath("$.data.positions[2].employee.user[0].email",Is.is("hr@mail2.ru")))
                .andExpect(jsonPath("$.data.positions[2].employee.user[0].firstName",Is.is("Ivan2")))
                .andExpect(jsonPath("$.data.positions[2].employee.user[0].lastName",Is.is("Ivanov2")))
                .andExpect(jsonPath("$.data.positions[2].employee.user[0].patronymic",Is.is("Ivanovich2")))
                .andExpect(jsonPath("$.data.positions[2].employee.user[0].birthday",Is.is("01.01.1970")))
                .andExpect(jsonPath("$.data.positions[2].employee.user[0].gender",Is.is("MALE")))

                .andExpect(jsonPath("$.data.positions[2].wage.id",Is.is(38)))
                .andExpect(jsonPath("$.data.positions[2].wage.dateStart",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.positions[2].wage.dateEnd",Is.is("13.07.2023")))
                .andExpect(jsonPath("$.data.positions[2].wage.value",Is.is(600)));


        mockMvc.perform(get("/api/manager/position/get/information/by/department/{departmentId}",50)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Отделения с такм id не существует")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hr_manager_position_rest_controller/updateWagePosition.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/hrmanager/hr_manager_position_rest_controller/updateWagePosition_clear.sql")
    public void editWageOfPosition() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        WageDto wageDto = WageDto.builder()
                .id(11000)
                .value(BigDecimal.valueOf(1000.00))
                .dateStart("05.01.2021")
                .dateEnd("23.01.2021")
                .build();

        //Проверка изменения цены, даты начала и конца оклада
        mockMvc.perform(patch("/api/manager/position/{positionId}", "10000")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(wageDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200));

        //Проверка существующего должности
        mockMvc.perform(patch("/api/manager/position/{positionId}", "2000")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(wageDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Должность с таким ID не найдена")));

        WageDto wageDto1 = WageDto.builder()
                .id(9000)
                .build();

        //Проверка существующего оклада
        mockMvc.perform(patch("/api/manager/position/{positionId}", "10002")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(wageDto1))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Оклад с таким ID не найден")));

        WageDto wageDto2 = WageDto.builder()
                .id(11001)
                .value(BigDecimal.valueOf(1000.00))
                .build();

        //Проверка действующего оклада, когда дата окончания у него - null
        mockMvc.perform(patch("/api/manager/position/{positionId}", "10000")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(wageDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(452)))
                .andExpect(jsonPath("$.text", Is.is("Цена оклада действует в данный момент времени")));

        //Проверка принадлежит ли оклад должности
        WageDto wageDto3 = WageDto.builder()
                .id(11005)
                .build();
        mockMvc.perform(patch("/api/manager/position/{positionId}", "10000")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(wageDto3))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Оклад не принадлежит должности")));

        //Проверка наложения даты действия оклада
        WageDto wageDto4 = WageDto.builder()
                .id(11000)
                .value(BigDecimal.valueOf(1000.00))
                .dateStart("05.01.2021")
                .dateEnd("23.02.2021")
                .build();

        mockMvc.perform(patch("/api/manager/position/{positionId}", "10000")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(wageDto4))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(452)))
                .andExpect(jsonPath("$.text", Is.is("Накладка даты действия цены")));
    }
}
