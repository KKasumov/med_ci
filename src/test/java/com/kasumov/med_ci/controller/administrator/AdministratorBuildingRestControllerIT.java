package com.kasumov.med_ci.controller.administrator;

import com.kasumov.med_ci.model.dto.structure.building.NewBuildingDto;
import com.kasumov.med_ci.model.dto.structure.cabinet.NewCabinetDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import com.kasumov.med_ci.model.dto.structure.building.BuildingDto;
import com.kasumov.med_ci.model.dto.structure.cabinet.CabinetDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

public class AdministratorBuildingRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/administrator/administrator_building_rest_controller/administratorDeleteCabinetById.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/administrator/administrator_building_rest_controller/administratorDeleteCabinetById_clear.sql")
    public void administratorDeleteCabinetByIdTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("Ivan@mail.com", "100", mockMvc);

//         Проверка существует ли здание
        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete/cabinets", 88888888)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[5, 6]")
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Здания не существует")));

        // проверка существует ли кабинет
        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete/cabinets", 40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[12, 6]")
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Кабинета не существует")));


        // проверка, что кабинет не содержит рабочие места
        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete/cabinets", 40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[5]")
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(455)))
                .andExpect(jsonPath("$.text", Is.is("Кабинет содержит рабочие места")));

        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete/cabinets", 40)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[6, 7]")
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(40)))
                .andExpect(jsonPath("$.data.physicalAddress", Is.is("555-fer")))
                .andExpect(jsonPath("$.data.cabinets.[0].id", Is.is(5)))
                .andExpect(jsonPath("$.data.cabinets.[0].number", Is.is(400)))
                .andExpect(jsonPath("$.data.cabinets.[0].name", Is.is("Pediatrician")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/admin/admin_cabinet_rest_controller/adminNewCabinet.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/admin/admin_cabinet_rest_controller/adminNewCabinet_clear.sql")
    public void saveNewCabinetTest() throws Exception {

        NewCabinetDto newCabinetDTOOne = NewCabinetDto.builder()
                .number(100)
                .name("UXO")
                .build();
        NewCabinetDto newCabinetDTOTwo = NewCabinetDto.builder()
                .number(200)
                .name("Glass")
                .build();
        List<NewCabinetDto> newCabinetDTOOneList = new ArrayList<>();
        newCabinetDTOOneList.add(newCabinetDTOOne);
        newCabinetDTOOneList.add(newCabinetDTOTwo);

        List<NewCabinetDto> newCabinetDTOTwoList = new ArrayList<>();
        newCabinetDTOTwoList.add(newCabinetDTOTwo);

        accessToken = tokenUtil.obtainNewAccessToken("Dmintriy@mail.com", "30", mockMvc);
        mockMvc.perform(post("/api/administrator/building/{buildingId}/add/cabinet", 40)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newCabinetDTOOneList))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(40)))
                .andExpect(jsonPath("$.data.physicalAddress", Is.is("555-fer")))

                .andExpect(jsonPath("$.data.cabinets[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.cabinets[0].number", Is.is(100)))
                .andExpect(jsonPath("$.data.cabinets[0].name", Is.is("UXO")))

                .andExpect(jsonPath("$.data.cabinets[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.cabinets[1].number", Is.is(200)))
                .andExpect(jsonPath("$.data.cabinets[1].name", Is.is("Glass")));


        mockMvc.perform(post("/api/administrator/building/{buildingId}/add/cabinet", 50)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newCabinetDTOTwoList))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(50)))
                .andExpect(jsonPath("$.data.physicalAddress", Is.is("666-der")))

                .andExpect(jsonPath("$.data.cabinets[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.cabinets[0].number", Is.is(200)))
                .andExpect(jsonPath("$.data.cabinets[0].name", Is.is("Glass")));

        mockMvc.perform(post("/api/administrator/building/{buildingId}/add/cabinet", 90)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newCabinetDTOOneList))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Здание не найдено по id")));
    }

    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/administrator/administrator_building_rest_controller/saveNewBuilding.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/administrator/administrator_building_rest_controller/saveNewBuilding_clear.sql")
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/administrator" +
            "/administrator_building_rest_controller/saveNewBuilding.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/administrator" +
            "/administrator_building_rest_controller/saveNewBuilding_clear.sql")
    public void saveNewBuilding() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("admin@gmail.com", "1", mockMvc);

        //проверка на создание здания с несуществующей мед. организацией
        NewBuildingDto newBuildingDtoWithNotExistsMedicalOrganization = NewBuildingDto.builder()
                .physicalAddress("Батутина, д.8, кв.26")
                .medicalOrganizationId(4324234)
                .build();

        mockMvc.perform(post("/api/administrator/building/add")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newBuildingDtoWithNotExistsMedicalOrganization))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Медицинской организации не существует")));


        //проверка на добавление здания с кабинетами
        List<NewCabinetDto> newCabinetDtoList = Stream.of(
                        NewCabinetDto.builder()
                                .number(308)
                                .name("Офтальмолог")
                                .build(),
                        NewCabinetDto.builder()
                                .number(502)
                                .name("Гинеколог")
                                .build()).
                toList();

        NewBuildingDto newBuildingDto = NewBuildingDto.builder()
                .physicalAddress("Батутина, д.8, кв.26")
                .medicalOrganizationId(2243)
                .cabinets(newCabinetDtoList)
                .build();

        mockMvc.perform(post("/api/administrator/building/add")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newBuildingDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.physicalAddress", Is.is("Батутина, д.8, кв.26")));

        //проверка на наличие этого здания в системе
        mockMvc.perform(post("/api/administrator/building/add")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newBuildingDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Здание с таким адресом уже существует в системе")));


        //проверка на добавление здания без кабинетов
        NewBuildingDto newBuildingDtoWithoutCabinets = NewBuildingDto.builder()
                .physicalAddress("Типанова, д.20, кв. 32")
                .medicalOrganizationId(2243)
                .build();

        mockMvc.perform(post("/api/administrator/building/add")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newBuildingDtoWithoutCabinets))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.physicalAddress", Is.is("Типанова, д.20, кв. 32")));

        //проверка на наличие этого здания в системе
        mockMvc.perform(post("/api/administrator/building/add")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newBuildingDtoWithoutCabinets))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Здание с таким адресом уже существует в системе")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
            "/scripts/controller/administrator/administrator_building_rest_controller/deleteBuildingById.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
            "/scripts/controller/administrator/administrator_building_rest_controller/deleteBuildingById_clear.sql")
    public void deleteBuildingByIdIT() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("admin11@gmail.com", "1", mockMvc);

        //проверка на существование здания
        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete", 4389274)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Здания с таким id не существует")));


        //проверка на занятость кабинетов
        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete", 111)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Кабинеты этого здания уже используются")));


        //проверка на успешное удаление здания с незанятыми кабинетами
        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete", 112)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        //проверка на успешное удаление здания без кабинетов
        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete", 113)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        //проверка, действительно ли прошло удаление зданий
        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete", 112)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Здания с таким id не существует")));

        mockMvc.perform(delete("/api/administrator/building/{buildingId}/delete", 113)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Здания с таким id не существует")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/administrator/administrator_building_rest_controller/AdministratorUpdateBuildingById.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/administrator/administrator_building_rest_controller/AdministratorUpdateBuildingById_clear.sql")

    public void updateBuilding() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("admin@mail.com", "1", mockMvc);

        List<CabinetDto> cabinetDto = new ArrayList<>();
        cabinetDto.add(0, new CabinetDto(5000L, 10, "Окулист"));
        cabinetDto.add(1, new CabinetDto(5001L, 20, "Терапевт"));
        cabinetDto.add(2, new CabinetDto(5002L, 30, "УЗИ"));
        cabinetDto.add(3, new CabinetDto(5003L, 40, "ЛОР"));
        cabinetDto.add(4, new CabinetDto(5064L, 50, "ЛОР")); // этого кабинета нет в базе

        BuildingDto buildingDto1 = BuildingDto.builder()
                .id(2000)
                .physicalAddress("Новый адрес")
                .cabinets(cabinetDto)
                .build();

        //Проверка изменения существующего здания и его кабинетов
        mockMvc.perform(patch("/api/administrator/building/{buildingId}/modify", "2000")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(buildingDto1))
                        .contentType(MediaType.APPLICATION_JSON)

                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200));

        //Проверка существования здания
        mockMvc.perform(patch("/api/administrator/building/{buildingId}/modify", "2005")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(buildingDto1))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Здание с таким ID не найдено")));

        //Проверка на корректные данные в запросе
        BuildingDto buildingDto3 = BuildingDto.builder()
                .id(2005)
                .build();
        mockMvc.perform(patch("/api/administrator/building/{buildingId}/modify", "2000")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(buildingDto3))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Некорректные данные в запросе")));

//        Проверка изменения существующего здания без кабинетов
        BuildingDto buildingDto2 = BuildingDto.builder()
                .id(2001)
                .physicalAddress("Новый адрес без кабинетов")
                .build();

        mockMvc.perform(patch("/api/administrator/building/{buildingId}/modify", "2001")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(buildingDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200));
    }
}
