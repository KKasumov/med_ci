package com.kasumov.med_ci.controller.doctor;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DoctorTalonRestControllerIT extends ContextIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/doctor/doctor_talon_rest_controller/deleteDoctorTalons.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/doctor/doctor_talon_rest_controller/deleteDoctorTalons_clear.sql")
    public void deleteDoctorTalonsIT() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("doctor20@mail.com", "1", mockMvc);

        // Удаляю свободные талоны в указанный диапазон дней и возвращаю неудаленные
        mockMvc.perform(
                        delete("/api/doctor/talon/delete/for/days")
                                .param("dateStart", dateConvertor.localDateTimeToString(
                                        LocalDateTime.now().plusDays(1)))
                                .param("dateEnd", dateConvertor.localDateTimeToString(
                                        LocalDateTime.now().plusDays(3)))
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].talons.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[0].talons[0].id", Is.is(25)))
                .andExpect(jsonPath("$.data[0].talons[0].time", Is.is(dateConvertor.localDateTimeToString
                        (LocalDate.now().plusDays(2).atTime(11, 0)))))
                .andExpect(jsonPath("$.data[0].talons[0].patient.id", Is.is(30)))
                .andExpect(jsonPath("$.data[0].talons[1].id", Is.is(26)))
                .andExpect(jsonPath("$.data[0].talons[1].time", Is.is(dateConvertor.localDateTimeToString(
                        LocalDate.now().plusDays(2).atTime(12, 0)))))
                .andExpect(jsonPath("$.data[0].talons[1].patient.id", Is.is(30)));

        // Проверяю, что не удалены свободные талоны, которые выходят за диапазон теста
        Assertions.assertEquals(3L, entityManager.createQuery("""
                SELECT COUNT (t.id)
                FROM Talon t
                WHERE t.patientHistory IS NULL
                    AND t.doctorHistory.employee.id = 20
                """, Long.class).getSingleResult());

        // Проверяю, что не удалены талоны другого доктора
        Assertions.assertEquals(3L, entityManager.createQuery("""
                SELECT COUNT (t.id)
                FROM Talon t
                WHERE t.doctorHistory.employee.id = 50
                """, Long.class).getSingleResult());

        // Удаляю талоны без указанного времени (от сегодня до doctorScope) и возвращаю неудаленные
        mockMvc.perform(
                        delete("/api/doctor/talon/delete/for/days")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].talons.length()", Is.is(1)))
                .andExpect(jsonPath("$.data[0].talons[0].id", Is.is(22)))
                .andExpect(jsonPath("$.data[0].talons[0].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().atTime(8, 0)))))
                .andExpect(jsonPath("$.data[0].talons[0].patient.id", Is.is(30)))

                .andExpect(jsonPath("$.data[1].talons.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[1].talons[0].id", Is.is(25)))
                .andExpect(jsonPath("$.data[1].talons[0].time", Is.is(dateConvertor.localDateTimeToString(
                        LocalDate.now().plusDays(2).atTime(11, 0)))))
                .andExpect(jsonPath("$.data[1].talons[0].patient.id", Is.is(30)))
                .andExpect(jsonPath("$.data[1].talons[1].id", Is.is(26)))
                .andExpect(jsonPath("$.data[1].talons[1].time", Is.is(dateConvertor.localDateTimeToString(
                        LocalDate.now().plusDays(2).atTime(12, 0)))))
                .andExpect(jsonPath("$.data[1].talons[1].patient.id", Is.is(30)))

                .andExpect(jsonPath("$.data[2].talons.length()", Is.is(1)))
                .andExpect(jsonPath("$.data[2].talons[0].id", Is.is(27)))
                .andExpect(jsonPath("$.data[2].talons[0].time", Is.is(dateConvertor.localDateTimeToString(
                        LocalDate.now().plusDays(4).atTime(9, 0)))))
                .andExpect(jsonPath("$.data[2].talons[0].patient.id", Is.is(30)));

        // Проверяю, что не удалены талоны, которые выходят за doctorScope
        Assertions.assertEquals(1L, entityManager.createQuery("""
                SELECT COUNT (t.id)
                FROM Talon t
                WHERE t.patientHistory IS NULL
                    AND t.doctorHistory.employee.id = 20
                """, Long.class).getSingleResult());

        // Проверяю, что не удалены талоны другого доктора
        Assertions.assertEquals(3L, entityManager.createQuery("""
                SELECT COUNT (t.id)
                FROM Talon t
                WHERE t.doctorHistory.employee.id = 50
                """, Long.class).getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/doctor/doctor_talon_rest_controller/doctorGetTalonsForToday.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/doctor/doctor_talon_rest_controller/doctorGetTalonsForToday_clear.sql")
    public void doctorGetTalonsForTodayTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("doctor@mail.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/doctor/talon/get/today")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.day", Is.is(dateConvertor.localDateToString(LocalDate.now()))))
                .andExpect(jsonPath("$.data.talons.length()", Is.is(3)))
                .andExpect(jsonPath("$.data.talons[0].id", Is.is(404)))
                .andExpect(jsonPath("$.data.talons[0].time", Is.is
                        (dateConvertor.localDateTimeToString(LocalDate.now().atTime(0, 30)))))
                .andExpect(jsonPath("$.data.talons[0].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data.talons[0].doctor.firstName", Is.is("Peter")))
                .andExpect(jsonPath("$.data.talons[0].doctor.lastName", Is.is("Petrov")))
                .andExpect(jsonPath("$.data.talons[0].doctor.patronymic", Is.is("Petrovich")))
                .andExpect(jsonPath("$.data.talons[0].patient", Matchers.nullValue()))
                .andExpect(jsonPath("$.data.talons[1].id", Is.is(408)))
                .andExpect(jsonPath("$.data.talons[1].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().atTime(9, 30)))))
                .andExpect(jsonPath("$.data.talons[1].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data.talons[1].doctor.firstName", Is.is("Peter")))
                .andExpect(jsonPath("$.data.talons[1].doctor.lastName", Is.is("Petrov")))
                .andExpect(jsonPath("$.data.talons[1].doctor.patronymic", Is.is("Petrovich")))
                .andExpect(jsonPath("$.data.talons[1].patient.id", Is.is(10)))
                .andExpect(jsonPath("$.data.talons[1].patient.firstName", Is.is("Gennady")))
                .andExpect(jsonPath("$.data.talons[1].patient.lastName", Is.is("Gennadyev")))
                .andExpect(jsonPath("$.data.talons[1].patient.patronymic", Is.is("Gennadyevich")))
                .andExpect(jsonPath("$.data.talons[1].patient.birthday", Is.is("07.01.1998")))
                .andExpect(jsonPath("$.data.talons[2].id", Is.is(411)))
                .andExpect(jsonPath("$.data.talons[2].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().atTime(20, 30)))))
                .andExpect(jsonPath("$.data.talons[2].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data.talons[2].doctor.firstName", Is.is("Peter")))
                .andExpect(jsonPath("$.data.talons[2].doctor.lastName", Is.is("Petrov")))
                .andExpect(jsonPath("$.data.talons[2].doctor.patronymic", Is.is("Petrovich")))
                .andExpect(jsonPath("$.data.talons[2].patient.id", Is.is(20)))
                .andExpect(jsonPath("$.data.talons[2].patient.firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.data.talons[2].patient.lastName", Is.is("Ivanov")))
                .andExpect(jsonPath("$.data.talons[2].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.talons[2].patient.birthday", Is.is("25.06.1990")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/doctor/doctor_talon_rest_controller/doctorGetTalonsByDays.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/doctor/doctor_talon_rest_controller/doctorGetTalonsByDays_clear.sql")
    public void doctorGetTalonsByDaysTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("doctor41@email.com", "1", mockMvc);
        mockMvc.perform(
                        get("/api/doctor/talon//get/for/days")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(4)))

                .andExpect(jsonPath("$.data[0].day", Is.is(dateConvertor.localDateToString(LocalDate.now()))))
                .andExpect(jsonPath("$.data[0].talons.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[0].talons[0].id", Is.is(1)))
                .andExpect(jsonPath("$.data[0].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(7, 00)))))
                .andExpect(jsonPath("$.data[0].talons[0].doctor.id", Is.is(41)))
                .andExpect(jsonPath("$.data[0].talons[0].doctor.firstName", Is.is("Oleg")))
                .andExpect(jsonPath("$.data[0].talons[0].doctor.lastName", Is.is("Olegov")))
                .andExpect(jsonPath("$.data[0].talons[0].doctor.patronymic", Is.is("Andreevich")))
                .andExpect(jsonPath("$.data[0].talons[0].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data[0].talons[0].patient.firstName", Is.is("Ivan1")))
                .andExpect(jsonPath("$.data[0].talons[0].patient.lastName", Is.is("Ivanov1")))
                .andExpect(jsonPath("$.data[0].talons[0].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data[0].talons[0].patient.birthday", Is.is("25.09.1990")))
                .andExpect(jsonPath("$.data[0].talons[1].id", Is.is(2)))
                .andExpect(jsonPath("$.data[0].talons[1].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(8, 00)))))
                .andExpect(jsonPath("$.data[0].talons[1].doctor.id", Is.is(41)))
                .andExpect(jsonPath("$.data[0].talons[1].doctor.firstName", Is.is("Oleg")))
                .andExpect(jsonPath("$.data[0].talons[1].doctor.lastName", Is.is("Olegov")))
                .andExpect(jsonPath("$.data[0].talons[1].doctor.patronymic", Is.is("Andreevich")))

                .andExpect(jsonPath("$.data[1].day", Is.is(Is.is(dateConvertor.localDateToString(LocalDate.now().plusDays(3))))))
                .andExpect(jsonPath("$.data[1].talons.length()", Is.is(1)))
                .andExpect(jsonPath("$.data[1].talons[0].id", Is.is(5)))
                .andExpect(jsonPath("$.data[1].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(3).atTime(9, 00)))))
                .andExpect(jsonPath("$.data[1].talons[0].doctor.id", Is.is(41)))
                .andExpect(jsonPath("$.data[1].talons[0].doctor.firstName", Is.is("Oleg")))
                .andExpect(jsonPath("$.data[1].talons[0].doctor.lastName", Is.is("Olegov")))
                .andExpect(jsonPath("$.data[1].talons[0].doctor.patronymic", Is.is("Andreevich")))
                .andExpect(jsonPath("$.data[1].talons[0].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data[1].talons[0].patient.firstName", Is.is("Ivan1")))
                .andExpect(jsonPath("$.data[1].talons[0].patient.lastName", Is.is("Ivanov1")))
                .andExpect(jsonPath("$.data[1].talons[0].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data[1].talons[0].patient.birthday", Is.is("25.09.1990")))

                .andExpect(jsonPath("$.data[2].day", Is.is(dateConvertor.localDateToString(LocalDate.now().plusDays(6)))))
                .andExpect(jsonPath("$.data[2].talons.length()", Is.is(1)))
                .andExpect(jsonPath("$.data[2].talons[0].id", Is.is(6)))
                .andExpect(jsonPath("$.data[2].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(6).atTime(10, 00)))))
                .andExpect(jsonPath("$.data[2].talons[0].doctor.id", Is.is(41)))
                .andExpect(jsonPath("$.data[2].talons[0].doctor.firstName", Is.is("Oleg")))
                .andExpect(jsonPath("$.data[2].talons[0].doctor.lastName", Is.is("Olegov")))
                .andExpect(jsonPath("$.data[2].talons[0].doctor.patronymic", Is.is("Andreevich")))
                .andExpect(jsonPath("$.data[2].talons[0].patient.id", Is.is(22)))
                .andExpect(jsonPath("$.data[2].talons[0].patient.firstName", Is.is("Ivan2")))
                .andExpect(jsonPath("$.data[2].talons[0].patient.lastName", Is.is("Ivanov2")))
                .andExpect(jsonPath("$.data[2].talons[0].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data[2].talons[0].patient.birthday", Is.is("25.06.1990")))

                .andExpect(jsonPath("$.data[3].day", Is.is(dateConvertor.localDateToString(LocalDate.now().plusDays(20)))))
                .andExpect(jsonPath("$.data[3].talons.length()", Is.is(1)))
                .andExpect(jsonPath("$.data[3].talons[0].id", Is.is(9)))
                .andExpect(jsonPath("$.data[3].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(20).atTime(10, 00)))))
                .andExpect(jsonPath("$.data[3].talons[0].doctor.id", Is.is(41)))
                .andExpect(jsonPath("$.data[3].talons[0].doctor.firstName", Is.is("Oleg")))
                .andExpect(jsonPath("$.data[3].talons[0].doctor.lastName", Is.is("Olegov")))
                .andExpect(jsonPath("$.data[3].talons[0].doctor.patronymic", Is.is("Andreevich")));

        //проверяю переданные даты, в диапазоне которых должны быть найдены талоны
        mockMvc.perform(
                        get("/api/doctor/talon//get/for/days")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("dateStart", dateConvertor.localDateTimeToString((LocalDate.now().plusDays(2).atTime(0,0))))
                                .param("dateEnd", dateConvertor.localDateTimeToString((LocalDate.now().plusDays(4).atTime(23,59))))
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(1)))

                .andExpect(jsonPath("$.data[0].day", Is.is(Is.is(dateConvertor.localDateToString(LocalDate.now().plusDays(3))))))
                .andExpect(jsonPath("$.data[0].talons[0].id", Is.is(5)))
                .andExpect(jsonPath("$.data[0].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(3).atTime(9, 00)))))
                .andExpect(jsonPath("$.data[0].talons[0].doctor.id", Is.is(41)))
                .andExpect(jsonPath("$.data[0].talons[0].doctor.firstName", Is.is("Oleg")))
                .andExpect(jsonPath("$.data[0].talons[0].doctor.lastName", Is.is("Olegov")))
                .andExpect(jsonPath("$.data[0].talons[0].doctor.patronymic", Is.is("Andreevich")))
                .andExpect(jsonPath("$.data[0].talons[0].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data[0].talons[0].patient.firstName", Is.is("Ivan1")))
                .andExpect(jsonPath("$.data[0].talons[0].patient.lastName", Is.is("Ivanov1")))
                .andExpect(jsonPath("$.data[0].talons[0].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data[0].talons[0].patient.birthday", Is.is("25.09.1990")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/doctor/doctor_talon_rest_controller/doctorDeleteTalonById.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/doctor/doctor_talon_rest_controller/doctorDeleteTalonById_clear.sql")
    public void doctorDeleteTalonById() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("doctor1@mail.com", "4", mockMvc);
        mockMvc.perform(
                        delete("/api/doctor/talon/{talonId}/delete", 333)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk());
        Assertions.assertEquals(0L, entityManager.createQuery("""
                SELECT COUNT(t.id) 
                FROM Talon t
                WHERE t.id = 333
                """, Long.class).getSingleResult());

        mockMvc.perform(
                        delete("/api/doctor/talon/{talonId}/delete", 111)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("На талон с таким id записан пациент")));

        mockMvc.perform(
                        delete("/api/doctor/talon/{talonId}/delete", 222)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Талон с таким id не найден")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/doctor/doctor_talon_rest_controller/doctorGetTalon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/doctor/doctor_talon_rest_controller/doctorGetTalon_clear.sql")
    public void doctorGetTalonTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("doctor1@mail.com", "4", mockMvc);
        mockMvc.perform(
                        get("/api/doctor/talon/{talonId}/get", 111)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(111)))
                .andExpect(jsonPath("$.data.time", Is.is("02.03.2023 16:49")))
                .andExpect(jsonPath("$.data.doctor.id", Is.is(40)))
                .andExpect(jsonPath("$.data.doctor.firstName", Is.is("f_doctor1")))
                .andExpect(jsonPath("$.data.doctor.lastName", Is.is("l_doctor1")))
                .andExpect(jsonPath("$.data.doctor.patronymic", Is.is("p_doctor1")))
                .andExpect(jsonPath("$.data.patient.id", Is.is(10)))
                .andExpect(jsonPath("$.data.patient.firstName", Is.is("f_patient1")))
                .andExpect(jsonPath("$.data.patient.lastName", Is.is("l_patient1")))
                .andExpect(jsonPath("$.data.patient.patronymic", Is.is("p_patient1")))
                .andExpect(jsonPath("$.data.patient.birthday", Is.is("07.01.1998")));

        mockMvc.perform(
                        get("/api/doctor/talon/{talonId}/get", 333)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(333)))
                .andExpect(jsonPath("$.data.time", Is.is("02.03.2023 16:49")))
                .andExpect(jsonPath("$.data.doctor.id", Is.is(40)))
                .andExpect(jsonPath("$.data.doctor.firstName", Is.is("f_doctor1")))
                .andExpect(jsonPath("$.data.doctor.lastName", Is.is("l_doctor1")))
                .andExpect(jsonPath("$.data.doctor.patronymic", Is.is("p_doctor1")))
                .andExpect(jsonPath("$.data.patient", Matchers.nullValue()));

        // проверка на принадлежность к доктору
        mockMvc.perform(
                        get("/api/doctor/talon/{talonId}/get", 222)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Талон с таким id не найден")));

        // проверка на существование талона
        mockMvc.perform(
                        get("/api/doctor/talon/{talonId}/get", 444)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Талон с таким id не найден")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/doctor/doctor_talon_rest_controller/createTalonsForRangeDays.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/doctor/doctor_talon_rest_controller/createTalonsForRangeDays_clear.sql")
    public void doctorCreateTalonsForRangeDays() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("doctor52@email.com", "4", mockMvc);

        // Проверка, что талоны заполнились
        mockMvc.perform(
                post("/api/doctor/talon/add/new/for/days")
                        .param("dateStart", dateConvertor.localDateToString(LocalDate.now().plusDays(1)))
                        .param("dateEnd", dateConvertor.localDateToString(LocalDate.now().plusDays(15)))
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data.length()", Is.is(11)))
                .andExpect(jsonPath("$.data.[0].talons[0].id", Is.is(Matchers.notNullValue())))
                .andExpect(jsonPath("$.data.[0].talons[0].doctor.id", Is.is(52)))
                .andExpect(jsonPath("$.data.[0].talons[0].doctor.firstName", Is.is("Irina1")))
                .andExpect(jsonPath("$.data.[0].talons[0].doctor.lastName", Is.is("Petrova1")))
                .andExpect(jsonPath("$.data.[0].talons[0].doctor.patronymic", Is.is("Olegovna")));

        // Проверка с отправкой dayStart и dayEnd равными null
        mockMvc.perform(
                        post("/api/doctor/talon/add/new/for/days")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data.length()", Is.is(21)));

    }

}
