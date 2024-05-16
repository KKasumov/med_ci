package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.attestation.NewAttestationDto;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HrManagerAttestationRestControllerIT extends ContextIT {
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_attestation_rest_controller/HrManagerDeleteAttestationById.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_attestation_rest_controller/HrManagerDeleteAttestationById_clear.sql")
    public void deleteAttestationByIdTest() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        //проверка удачного удаления аттестации
        mockMvc.perform(delete("/api/manager/attestation/{attestationId}", 4000)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk());

        // проверка, что аттестация удалена
        mockMvc.perform(delete("/api/manager/attestation/{attestationId}", 4000)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Аттестации с таким id не найдена")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        value ="/scripts/controller/hrmanager/hr_manager_attestation_rest_controller/HrManagerGetDoctorByParam.sql" )
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_attestation_rest_controller/HrManagerGetDoctorByParam_clear.sql")
    public void getDoctorAllByEndAttestationTest() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        mockMvc.perform(get("/api/manager/attestation/attention/doctors/by/parameters")
                        .param("departmentId", String.valueOf(55L))
                        .param("day", String.valueOf(15))
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))

                .andExpect(jsonPath("$.data.doctorDto[0].id", Is.is(3000)))
                .andExpect(jsonPath("$.data.doctorDto[0].firstName",Is.is("Oleg1")))
                .andExpect(jsonPath("$.data.doctorDto[0].lastName",Is.is("Sidorov1")))
                .andExpect(jsonPath("$.data.doctorDto[0].patronymic",Is.is("Andreevich1")))
                .andExpect(jsonPath("$.data.doctorDto[0].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[0].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now())))) //nenf
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].number",Is.is("444-333-221")))

                .andExpect(jsonPath("$.data.doctorDto[1].id", Is.is(4000)))
                .andExpect(jsonPath("$.data.doctorDto[1].firstName",Is.is("Oleg")))
                .andExpect(jsonPath("$.data.doctorDto[1].lastName",Is.is("Sidorov")))
                .andExpect(jsonPath("$.data.doctorDto[1].patronymic",Is.is("Andreevich")))
                .andExpect(jsonPath("$.data.doctorDto[1].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[1].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().minusDays(1)))))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[0].number",Is.is("444-333-666")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[1].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[1].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().minusDays(1)))))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[1].number",Is.is("444-333-222")))



                .andExpect(jsonPath("$.data.departmentDto.id",Is.is(55)))
                .andExpect(jsonPath("$.data.departmentDto.name",Is.is("Первое детское отделение")))
                .andExpect(jsonPath("$.data.departmentDto.ageType",Is.is("CHILD")));

        mockMvc.perform(get("/api/manager/attestation/attention/doctors/by/parameters")
                        .header("Authorization", accessToken)
                        .param("departmentId", String.valueOf(65L))
                        .param("day", String.valueOf(15))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text",Is.is("Отделения с таким id не существует")));

        mockMvc.perform(get("/api/manager/attestation/attention/doctors/by/parameters")
                .header("Authorization", accessToken)
                        .param("departmentId", String.valueOf(55L))
                        .param("day", String.valueOf(5))
                .contentType(MediaType.APPLICATION_JSON)
        )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.doctorDto[0].id", Is.is(3000)))
                .andExpect(jsonPath("$.data.doctorDto[0].firstName",Is.is("Oleg1")))
                .andExpect(jsonPath("$.data.doctorDto[0].lastName",Is.is("Sidorov1")))
                .andExpect(jsonPath("$.data.doctorDto[0].patronymic",Is.is("Andreevich1")))
                .andExpect(jsonPath("$.data.doctorDto[0].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[0].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now()))))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].number",Is.is("444-333-221")))

                .andExpect(jsonPath("$.data.departmentDto.id",Is.is(55)))
                .andExpect(jsonPath("$.data.departmentDto.name",Is.is("Первое детское отделение")))
                .andExpect(jsonPath("$.data.departmentDto.ageType",Is.is("CHILD")));

        mockMvc.perform(get("/api/manager/attestation/attention/doctors/by/parameters")
                .param("departmentId", String.valueOf(55L))
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))

                .andExpect(jsonPath("$.data.doctorDto[0].id", Is.is(3000)))
                .andExpect(jsonPath("$.data.doctorDto[0].firstName",Is.is("Oleg1")))
                .andExpect(jsonPath("$.data.doctorDto[0].lastName",Is.is("Sidorov1")))
                .andExpect(jsonPath("$.data.doctorDto[0].patronymic",Is.is("Andreevich1")))
                .andExpect(jsonPath("$.data.doctorDto[0].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[0].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now()))))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].number",Is.is("444-333-221")))

                .andExpect(jsonPath("$.data.doctorDto[1].id", Is.is(3500)))
                .andExpect(jsonPath("$.data.doctorDto[1].firstName",Is.is("Oleg3")))
                .andExpect(jsonPath("$.data.doctorDto[1].lastName",Is.is("Sidorov3")))
                .andExpect(jsonPath("$.data.doctorDto[1].patronymic",Is.is("Andreevich3")))
                .andExpect(jsonPath("$.data.doctorDto[1].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[1].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().plusDays(16)))))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[0].number",Is.is("444-333-223")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[1].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[1].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().plusDays(16)))))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[1].number",Is.is("444-333-226")))

                .andExpect(jsonPath("$.data.doctorDto[2].id", Is.is(4000)))
                .andExpect(jsonPath("$.data.doctorDto[2].firstName",Is.is("Oleg")))
                .andExpect(jsonPath("$.data.doctorDto[2].lastName",Is.is("Sidorov")))
                .andExpect(jsonPath("$.data.doctorDto[2].patronymic",Is.is("Andreevich")))
                .andExpect(jsonPath("$.data.doctorDto[2].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[2].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().minusDays(1)))))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[0].number",Is.is("444-333-666")))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[1].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[1].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().minusDays(1)))))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[1].number",Is.is("444-333-222")))

                .andExpect(jsonPath("$.data.departmentDto.id",Is.is(55)))
                .andExpect(jsonPath("$.data.departmentDto.name",Is.is("Первое детское отделение")))
                .andExpect(jsonPath("$.data.departmentDto.ageType",Is.is("CHILD")));

        mockMvc.perform(get("/api/manager/attestation/attention/doctors/by/parameters")
                .param("day", String.valueOf(30))
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))

                .andExpect(jsonPath("$.data.doctorDto[0].id", Is.is(3000)))
                .andExpect(jsonPath("$.data.doctorDto[0].firstName",Is.is("Oleg1")))
                .andExpect(jsonPath("$.data.doctorDto[0].lastName",Is.is("Sidorov1")))
                .andExpect(jsonPath("$.data.doctorDto[0].patronymic",Is.is("Andreevich1")))
                .andExpect(jsonPath("$.data.doctorDto[0].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[0].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now()))))
                .andExpect(jsonPath("$.data.doctorDto[0].attestationDto[0].number",Is.is("444-333-221")))

                .andExpect(jsonPath("$.data.doctorDto[1].id", Is.is(3500)))
                .andExpect(jsonPath("$.data.doctorDto[1].firstName",Is.is("Oleg3")))
                .andExpect(jsonPath("$.data.doctorDto[1].lastName",Is.is("Sidorov3")))
                .andExpect(jsonPath("$.data.doctorDto[1].patronymic",Is.is("Andreevich3")))
                .andExpect(jsonPath("$.data.doctorDto[1].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[1].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().plusDays(16)))))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[0].number",Is.is("444-333-223")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[1].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[1].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().plusDays(16)))))
                .andExpect(jsonPath("$.data.doctorDto[1].attestationDto[1].number",Is.is("444-333-226")))

                .andExpect(jsonPath("$.data.doctorDto[2].id", Is.is(4000)))
                .andExpect(jsonPath("$.data.doctorDto[2].firstName",Is.is("Oleg")))
                .andExpect(jsonPath("$.data.doctorDto[2].lastName",Is.is("Sidorov")))
                .andExpect(jsonPath("$.data.doctorDto[2].patronymic",Is.is("Andreevich")))
                .andExpect(jsonPath("$.data.doctorDto[2].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[2].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().minusDays(1)))))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[0].number",Is.is("444-333-666")))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[1].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[1].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().minusDays(1)))))
                .andExpect(jsonPath("$.data.doctorDto[2].attestationDto[1].number",Is.is("444-333-222")))

                .andExpect(jsonPath("$.data.doctorDto[3].id", Is.is(4500)))
                .andExpect(jsonPath("$.data.doctorDto[3].firstName",Is.is("Oleg4")))
                .andExpect(jsonPath("$.data.doctorDto[3].lastName",Is.is("Sidorov4")))
                .andExpect(jsonPath("$.data.doctorDto[3].patronymic",Is.is("Andreevich4")))
                .andExpect(jsonPath("$.data.doctorDto[3].birthday",Is.is("01.02.1975")))
                .andExpect(jsonPath("$.data.doctorDto[3].gender",Is.is("MALE")))
                .andExpect(jsonPath("$.data.doctorDto[3].attestationDto[0].dateFrom",Is.is("13.07.2017")))
                .andExpect(jsonPath("$.data.doctorDto[3].attestationDto[0].dateEnd",Is.is(dateConvertor
                        .localDateToString(LocalDate.now().plusDays(29)))))
                .andExpect(jsonPath("$.data.doctorDto[3].attestationDto[0].number",Is.is("444-333-228")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_attestation_rest_controller/saveAttestationByContract.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_attestation_rest_controller/saveAttestationByContract_clear.sql")
    public void saveAttestationByContractIT() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr111@gmail.com", "1", mockMvc);
        NewAttestationDto newAttestationDtoWithoutUniversity = NewAttestationDto.builder()
                .dateFrom("02.02.1986")
                .dateTo("03.03.2022")
                .number("222")
                .universityId(43244)
                .build();

        NewAttestationDto newAttestationDto = NewAttestationDto.builder()
                .dateFrom("02.02.1986")
                .dateTo("03.03.2022")
                .number("222")
                .universityId(4100)
                .build();

        //проверка на создание аттестации с несуществующим университетом
        mockMvc.perform(post("/api/manager/attestation/add/for/contract/{contractId}", 4100)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newAttestationDtoWithoutUniversity))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Университета с таким id не найдено")));


        //проверка на существование трудового договора
        mockMvc.perform(post("/api/manager/attestation/add/for/contract/{contractId}", 423424234)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newAttestationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Договора с таким id не найдено")));

        // проверка на срок годности контракта
        mockMvc.perform(post("/api/manager/attestation/add/for/contract/{contractId}", 4101)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newAttestationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Договор уже просрочен")));

        // проверка на привязку контракта к доктору
        mockMvc.perform(post("/api/manager/attestation/add/for/contract/{contractId}", 4102)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newAttestationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Договор принадлежит не доктору")));

        // проверка на работоспособность
        mockMvc.perform(post("/api/manager/attestation/add/for/contract/{contractId}", 4100)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newAttestationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.dateFrom", Is.is("02.02.1986")))
                .andExpect(jsonPath("$.data.dateTo", Is.is("03.03.2022")))
                .andExpect(jsonPath("$.data.number", Is.is("222")))
                .andExpect(jsonPath("$.data.universityDto.id", Is.is(4100)))
                .andExpect(jsonPath("$.data.laborContractDto.id", Is.is(4100)));

        // проверка на то, что аттестация уже создана=
        mockMvc.perform(post("/api/manager/attestation/add/for/contract/{contractId}", 4100)
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(newAttestationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Аттестация уже существует в системе")));
    }
}
