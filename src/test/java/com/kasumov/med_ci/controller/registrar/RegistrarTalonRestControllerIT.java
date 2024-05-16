package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.util.ContextIT;
import com.kasumov.med_ci.util.Generator;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({
        @MockBean(Generator.class),
        @MockBean(JavaMailSender.class)
})

public class RegistrarTalonRestControllerIT extends ContextIT {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/registrar/registrar_talon_rest_controller/add_for_registrar_talon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/registrar/registrar_talon_rest_controller/add_for_registrar_talon_clear.sql")
    public void getDepartmentWithTalonsDtoTest() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("registrar@email.com", "1", mockMvc);

        mockMvc.perform(
                        get("/api/registrar/talon/get/all/today")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))

                .andExpect(jsonPath("$.data.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.data.[0].name", Is.is("Surgery")))
                .andExpect(jsonPath("$.data.[0].ageType", Is.is("ADULT")))
                .andExpect(jsonPath("$.data.[0].chiefDoctorFirstName", Is.is("Oleg3")))
                .andExpect(jsonPath("$.data.[0].chiefDoctorLastName", Is.is("Sidorov3")))

                .andExpect(jsonPath("$.data.[0].talons.[0].id", Is.is(3)))
                .andExpect(jsonPath("$.data.[0].talons.[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(8, 00)))))
                .andExpect(jsonPath("$.data.[0].talons.[0].doctor.id", Is.is(51)))
                .andExpect(jsonPath("$.data.[0].talons.[0].doctor.firstName", Is.is("Oleg3")))
                .andExpect(jsonPath("$.data.[0].talons.[0].doctor.lastName", Is.is("Sidorov3")))
                .andExpect(jsonPath("$.data.[0].talons.[0].doctor.patronymic", Is.is("Andreevich")))

                .andExpect(jsonPath("$.data.[0].talons.[1].id", Is.is(4)))
                .andExpect(jsonPath("$.data.[0].talons.[1].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(9, 00)))))
                .andExpect(jsonPath("$.data.[0].talons.[1].doctor.id", Is.is(51)))
                .andExpect(jsonPath("$.data.[0].talons.[1].doctor.firstName", Is.is("Oleg3")))
                .andExpect(jsonPath("$.data.[0].talons.[1].doctor.lastName", Is.is("Sidorov3")))
                .andExpect(jsonPath("$.data.[0].talons.[1].doctor.patronymic", Is.is("Andreevich")))

                .andExpect(jsonPath("$.data.[0].talons.[2].id", Is.is(5)))
                .andExpect(jsonPath("$.data.[0].talons.[2].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(9, 00)))))
                .andExpect(jsonPath("$.data.[0].talons.[2].doctor.id", Is.is(52)))
                .andExpect(jsonPath("$.data.[0].talons.[2].doctor.firstName", Is.is("Irina1")))
                .andExpect(jsonPath("$.data.[0].talons.[2].doctor.lastName", Is.is("Petrova1")))
                .andExpect(jsonPath("$.data.[0].talons.[2].doctor.patronymic", Is.is("Olegovna")))
                .andExpect(jsonPath("$.data.[0].talons.[2].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data.[0].talons.[2].patient.firstName", Is.is("Ivan1")))
                .andExpect(jsonPath("$.data.[0].talons.[2].patient.lastName", Is.is("Ivanov1")))
                .andExpect(jsonPath("$.data.[0].talons.[2].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.[0].talons.[2].patient.birthday", Is.is("25.09.1990")))

                .andExpect(jsonPath("$.data.[0].talons.[3].id", Is.is(6)))
                .andExpect(jsonPath("$.data.[0].talons.[3].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(10, 00)))))
                .andExpect(jsonPath("$.data.[0].talons.[3].doctor.id", Is.is(52)))
                .andExpect(jsonPath("$.data.[0].talons.[3].doctor.firstName", Is.is("Irina1")))
                .andExpect(jsonPath("$.data.[0].talons.[3].doctor.lastName", Is.is("Petrova1")))
                .andExpect(jsonPath("$.data.[0].talons.[3].doctor.patronymic", Is.is("Olegovna")))
                .andExpect(jsonPath("$.data.[0].talons.[3].patient.id", Is.is(22)))
                .andExpect(jsonPath("$.data.[0].talons.[3].patient.firstName", Is.is("Ivan2")))
                .andExpect(jsonPath("$.data.[0].talons.[3].patient.lastName", Is.is("Ivanov2")))
                .andExpect(jsonPath("$.data.[0].talons.[3].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.[0].talons.[3].patient.birthday", Is.is("25.06.1990")))

                .andExpect(jsonPath("$.data.[1].id", Is.is(2)))
                .andExpect(jsonPath("$.data.[1].name", Is.is("Therapy")))
                .andExpect(jsonPath("$.data.[1].ageType", Is.is("CHILD")))
                .andExpect(jsonPath("$.data.[1].chiefDoctorFirstName", Is.is("Oleg2")))
                .andExpect(jsonPath("$.data.[1].chiefDoctorLastName", Is.is("Sidorov2")))

                .andExpect(jsonPath("$.data.[1].talons.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.data.[1].talons.[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(7, 00)))))
                .andExpect(jsonPath("$.data.[1].talons.[0].doctor.id", Is.is(42)))
                .andExpect(jsonPath("$.data.[1].talons.[0].doctor.firstName", Is.is("Oleg2")))
                .andExpect(jsonPath("$.data.[1].talons.[0].doctor.lastName", Is.is("Sidorov2")))
                .andExpect(jsonPath("$.data.[1].talons.[0].doctor.patronymic", Is.is("Andreevich")))
                .andExpect(jsonPath("$.data.[1].talons.[0].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data.[1].talons.[0].patient.firstName", Is.is("Ivan1")))
                .andExpect(jsonPath("$.data.[1].talons.[0].patient.lastName", Is.is("Ivanov1")))
                .andExpect(jsonPath("$.data.[1].talons.[0].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.[1].talons.[0].patient.birthday", Is.is("25.09.1990")))

                .andExpect(jsonPath("$.data.[1].talons.[1].id", Is.is(2)))
                .andExpect(jsonPath("$.data.[1].talons.[1].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(8, 00)))))
                .andExpect(jsonPath("$.data.[1].talons.[1].doctor.id", Is.is(42)))
                .andExpect(jsonPath("$.data.[1].talons.[1].doctor.firstName", Is.is("Oleg2")))
                .andExpect(jsonPath("$.data.[1].talons.[1].doctor.lastName", Is.is("Sidorov2")))
                .andExpect(jsonPath("$.data.[1].talons.[1].doctor.patronymic", Is.is("Andreevich")))

                .andExpect(jsonPath("$.data.[1].talons.[2].id", Is.is(7)))
                .andExpect(jsonPath("$.data.[1].talons.[2].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(10, 00)))))
                .andExpect(jsonPath("$.data.[1].talons.[2].doctor.id", Is.is(53)))
                .andExpect(jsonPath("$.data.[1].talons.[2].doctor.firstName", Is.is("Irina2")))
                .andExpect(jsonPath("$.data.[1].talons.[2].doctor.lastName", Is.is("Petrova2")))
                .andExpect(jsonPath("$.data.[1].talons.[2].doctor.patronymic", Is.is("Olegovna")))

                .andExpect(jsonPath("$.data.[1].talons.[3].id", Is.is(8)))
                .andExpect(jsonPath("$.data.[1].talons.[3].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(11, 00)))))
                .andExpect(jsonPath("$.data.[1].talons.[3].doctor.id", Is.is(53)))
                .andExpect(jsonPath("$.data.[1].talons.[3].doctor.firstName", Is.is("Irina2")))
                .andExpect(jsonPath("$.data.[1].talons.[3].doctor.lastName", Is.is("Petrova2")))
                .andExpect(jsonPath("$.data.[1].talons.[3].doctor.patronymic", Is.is("Olegovna")))
                .andExpect(jsonPath("$.data.[1].talons.[3].patient.id", Is.is(23)))
                .andExpect(jsonPath("$.data.[1].talons.[3].patient.firstName", Is.is("Ivan3")))
                .andExpect(jsonPath("$.data.[1].talons.[3].patient.lastName", Is.is("Ivanov3")))
                .andExpect(jsonPath("$.data.[1].talons.[3].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.[1].talons.[3].patient.birthday", Is.is("25.04.1990")));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_talon_rest_controller/add_for_registrar_talon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_talon_rest_controller/add_for_registrar_talon_clear.sql")
    public void getPatientTalons() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("registrar@email.com", "1", mockMvc);

        // Проверка, что пациента не существует
        mockMvc.perform(
                        get("/api/registrar/talon/get/all/by/patient/{patientId}", 100500)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Пациента не существует")));


        mockMvc.perform(
                        get("/api/registrar/talon/get/all/by/patient/{patientId}", 21)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(2)))
                .andExpect(jsonPath("$.data.[0].id", Is.is(1)))
                .andExpect(jsonPath("$.data.[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(7, 00)))))
                .andExpect(jsonPath("$.data.[0].doctor.id", Is.is(42)))
                .andExpect(jsonPath("$.data.[0].doctor.firstName", Is.is("Oleg2")))
                .andExpect(jsonPath("$.data.[0].doctor.lastName", Is.is("Sidorov2")))
                .andExpect(jsonPath("$.data.[0].doctor.patronymic", Is.is("Andreevich")))
                .andExpect(jsonPath("$.data.[0].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data.[0].patient.firstName", Is.is("Ivan1")))
                .andExpect(jsonPath("$.data.[0].patient.lastName", Is.is("Ivanov1")))
                .andExpect(jsonPath("$.data.[0].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.[0].patient.birthday", Is.is("25.09.1990")))

                .andExpect(jsonPath("$.data.[1].id", Is.is(5)))
                .andExpect(jsonPath("$.data.[1].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(9, 00)))))
                .andExpect(jsonPath("$.data.[1].doctor.id", Is.is(52)))
                .andExpect(jsonPath("$.data.[1].doctor.firstName", Is.is("Irina1")))
                .andExpect(jsonPath("$.data.[1].doctor.lastName", Is.is("Petrova1")))
                .andExpect(jsonPath("$.data.[1].doctor.patronymic", Is.is("Olegovna")))
                .andExpect(jsonPath("$.data.[1].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data.[1].patient.firstName", Is.is("Ivan1")))
                .andExpect(jsonPath("$.data.[1].patient.lastName", Is.is("Ivanov1")))
                .andExpect(jsonPath("$.data.[1].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.[1].patient.birthday", Is.is("25.09.1990")));
    }

    @Value("${spring.mail.username}")
    private String username;

    @Value("${testSystem.server.address}")
    private String serverAddress;


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registratar_rewrite_new_talon/add_for_registrar_talon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registratar_rewrite_new_talon/clear.sql")
    public void getNewTalonIsDeleteOldTalonNull() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.ru", "1", mockMvc);
        mockMvc.perform(
                        get("/api/registrar/talon/{talonId}/assigned/patient/from/old/talon/{oldTalonId}", "300", "400")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(4)))

                .andExpect(jsonPath("$.data.id", Is.is(300)))
                .andExpect(jsonPath("$.data.time", Is.is("08.01.1999 04:05")))
                .andExpect(jsonPath("$.data.doctor.id", Is.is(20)))
                .andExpect(jsonPath("$.data.doctor.firstName", Is.is("Doctor20")))
                .andExpect(jsonPath("$.data.doctor.lastName", Is.is("Doctor20")))
                .andExpect(jsonPath("$.data.doctor.patronymic", Is.is("Doctor20")))
                .andExpect(jsonPath("$.data.patient.id", Is.is(10)))
                .andExpect(jsonPath("$.data.patient.firstName", Is.is("Patient10")))
                .andExpect(jsonPath("$.data.patient.lastName", Is.is("Patient10")))
                .andExpect(jsonPath("$.data.patient.patronymic", Is.is("Patient10")))
                .andExpect(jsonPath("$.data.patient.birthday", Is.is("20.02.2020")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registratar_rewrite_new_talon/add_for_registrar_talon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registratar_rewrite_new_talon/clear.sql")
    public void getNewTalonIsDeleteOldTalonFalse() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.ru", "1", mockMvc);
        mockMvc.perform(
                        get("/api/registrar/talon/{talonId}/assigned/patient/from/old/talon/{oldTalonId}?isDeleteOldTalon=false", "300", "400")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.length()", Is.is(4)))

                .andExpect(jsonPath("$.data.id", Is.is(300)))
                .andExpect(jsonPath("$.data.time", Is.is("08.01.1999 04:05")))
                .andExpect(jsonPath("$.data.doctor.id", Is.is(20)))
                .andExpect(jsonPath("$.data.doctor.firstName", Is.is("Doctor20")))
                .andExpect(jsonPath("$.data.doctor.lastName", Is.is("Doctor20")))
                .andExpect(jsonPath("$.data.doctor.patronymic", Is.is("Doctor20")))
                .andExpect(jsonPath("$.data.patient.id", Is.is(10)))
                .andExpect(jsonPath("$.data.patient.firstName", Is.is("Patient10")))
                .andExpect(jsonPath("$.data.patient.lastName", Is.is("Patient10")))
                .andExpect(jsonPath("$.data.patient.patronymic", Is.is("Patient10")))
                .andExpect(jsonPath("$.data.patient.birthday", Is.is("20.02.2020")))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registratar_rewrite_new_talon/add_for_registrar_talon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registratar_rewrite_new_talon/clear.sql")
    public void getNewTalon() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.ru", "1", mockMvc);

        mockMvc.perform(
                        get("/api/registrar/talon/{talonId}/assigned/patient/from/old/talon/{oldTalonId}?isDeleteOldTalon=true", "300", "400")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(jsonPath("$.data.length()", Is.is(4)))

                .andExpect(jsonPath("$.data.id", Is.is(300)))
                .andExpect(jsonPath("$.data.time", Is.is("08.01.1999 04:05")))
                .andExpect(jsonPath("$.data.doctor.id", Is.is(20)))
                .andExpect(jsonPath("$.data.doctor.firstName", Is.is("Doctor20")))
                .andExpect(jsonPath("$.data.doctor.lastName", Is.is("Doctor20")))
                .andExpect(jsonPath("$.data.doctor.patronymic", Is.is("Doctor20")))
                .andExpect(jsonPath("$.data.patient.id", Is.is(10)))
                .andExpect(jsonPath("$.data.patient.firstName", Is.is("Patient10")))
                .andExpect(jsonPath("$.data.patient.lastName", Is.is("Patient10")))
                .andExpect(jsonPath("$.data.patient.patronymic", Is.is("Patient10")))
                .andExpect(jsonPath("$.data.patient.birthday", Is.is("20.02.2020")))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/registrar/talon/{talonId}/assigned/patient/from/old/talon/{oldTalonId}?isDeleteOldTalon=true", "300", "5000")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.ALL)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().toString()))

                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Старого талона не существует")));

        mockMvc.perform(
                        get("/api/registrar/talon/{talonId}/assigned/patient/from/old/talon/{oldTalonId}?isDeleteOldTalon=true", "3000", "500")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.ALL)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().toString()))

                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Нового талона не существует")));

        mockMvc.perform(
                        get("/api/registrar/talon/{talonId}/assigned/patient/from/old/talon/{oldTalonId}?isDeleteOldTalon=true", "20", "500")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.ALL)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().toString()))

                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("принимающий талон не свободный")));

        mockMvc.perform(
                        get("/api/registrar/talon/{talonId}/assigned/patient/from/old/talon/{oldTalonId}?isDeleteOldTalon=true", "500", "20")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.ALL)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().toString()))

                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Талоны не из одного отделения")));

        //письмо которое будет отправляться почтой
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("maratgareev97@gmail.com");
        smm.setSubject("Вы перезаписаны на новый талон");
        smm.setText("Номер Вашего талона: 300");
        System.out.println(smm);
        mailSender.send(smm);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_talon_rest_controller/registrarDeleteTalons.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_talon_rest_controller/registrarDeleteTalons_clear.sql")
    public void deleteTalons() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("rg@mail.ru", "100", mockMvc);

        // Удаляем свободные талоны в указанный диапазон дней и возвращаем те, что остались
        mockMvc.perform(
                        delete("/api/registrar/talon/delete/free/for/doctor/{doctorId}/by/days", 17)
                                .param("dateStart", dateConvertor.localDateTimeToString(LocalDateTime.now().plusDays(1)))
                                .param("dateEnd", dateConvertor.localDateTimeToString(LocalDateTime.now().plusDays(3)))
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                // .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].talons.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[0].talons[0].id", Is.is(225)))
                .andExpect(jsonPath("$.data[0].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(2).atTime(11, 0)))))
                .andExpect(jsonPath("$.data[0].talons[0].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data[0].talons[1].id", Is.is(226)))
                .andExpect(jsonPath("$.data[0].talons[1].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(2).atTime(12, 0)))))
                .andExpect(jsonPath("$.data[0].talons[1].patient.id", Is.is(23)));

        // Проверяем, что доктора с таким id не существует
        mockMvc.perform(
                        delete("/api/registrar/talon/delete/free/for/doctor/{doctorId}/by/days", 1119)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Доктора с таким id не существует")));

        // Проверяем, что не удалены свободные талоны, которые вне диапазона тестовых дней
        Assertions.assertEquals(4L, entityManager.createQuery("""
                SELECT COUNT (t.id)
                FROM Talon t
                WHERE t.patientHistory IS NULL
                    AND t.doctorHistory.employee.employeeHistory.id = 130
                """, Long.class).getSingleResult());

        // Удаляем свободные талоны, если диапазон дней не задан (от сегодня до doctorScope) и возвращае
        mockMvc.perform(
                        delete("/api/registrar/talon/delete/free/for/doctor/{doctorId}/by/days", 17)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data[0].talons.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[0].talons[0].id", Is.is(222)))
                .andExpect(jsonPath("$.data[0].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(8, 0)))))
                .andExpect(jsonPath("$.data[0].talons[0].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data[0].talons[1].id", Is.is(234)))
                .andExpect(jsonPath("$.data[0].talons[1].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().atTime(11, 0)))))
                .andExpect(jsonPath("$.data[0].talons[1].patient.id", Is.is(22)))

                .andExpect(jsonPath("$.data[1].talons.length()", Is.is(2)))
                .andExpect(jsonPath("$.data[1].talons[0].id", Is.is(225)))
                .andExpect(jsonPath("$.data[1].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(2).atTime(11, 0)))))
                .andExpect(jsonPath("$.data[1].talons[0].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data[1].talons[1].id", Is.is(226)))
                .andExpect(jsonPath("$.data[1].talons[1].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(2).atTime(12, 0)))))
                .andExpect(jsonPath("$.data[1].talons[1].patient.id", Is.is(23)))

                .andExpect(jsonPath("$.data[2].talons.length()", Is.is(1)))
                .andExpect(jsonPath("$.data[2].talons[0].id", Is.is(227)))
                .andExpect(jsonPath("$.data[2].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(4).atTime(9, 0)))))
                .andExpect(jsonPath("$.data[2].talons[0].patient.id", Is.is(21)))

                .andExpect(jsonPath("$.data[3].talons.length()", Is.is(1)))
                .andExpect(jsonPath("$.data[3].talons[0].id", Is.is(236)))
                .andExpect(jsonPath("$.data[3].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(6).atTime(7, 0)))))
                .andExpect(jsonPath("$.data[3].talons[0].patient.id", Is.is(24)));

        // Проверяем, что не удалены свободняе талоны, которые выходят за doctorScope
        Assertions.assertEquals(1L, entityManager.createQuery("""
                SELECT COUNT (t.id)
                FROM Talon t
                WHERE t.patientHistory IS NULL
                    AND t.doctorHistory.employee.employeeHistory.id = 130
                """, Long.class).getSingleResult());


    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_talon_rest_controller/addTalonsForDoctor.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_talon_rest_controller/addTalonsForDoctor_clear.sql")
    public void addTalonsForDoctor() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("registrar@email.com", "1", mockMvc);

        // Проверка, что доктора не существует
        mockMvc.perform(
                        post("/api/registrar/talon/add/for/doctor/{doctorId}/by/days", 100500)
                                .param("dayStart", dateConvertor.localDateToString(LocalDate.now().plusDays(1)))
                                .param("dayEnd", dateConvertor.localDateToString(LocalDate.now().plusDays(10)))
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Доктора не существует")));

        // Проверка, что талоны заполнились
        mockMvc.perform(
                        post("/api/registrar/talon/add/for/doctor/{doctorId}/by/days", 53)
                                .param("dayStart", dateConvertor.localDateToString(LocalDate.now().plusDays(1)))
                                .param("dayEnd", dateConvertor.localDateToString(LocalDate.now().plusDays(15)))
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data.length()", Is.is(11)))
                .andExpect(jsonPath("$.data.[0].day", Is.is(dateConvertor.localDateToString(LocalDate.now().plusDays(1)))))
                .andExpect(jsonPath("$.data.[0].talons[0].id", Is.is(9000)))
                .andExpect(jsonPath("$.data.[0].talons[0].time", Is.is(dateConvertor.localDateTimeToString(LocalDate.now().plusDays(1).atTime(10, 00)))))
                .andExpect(jsonPath("$.data.[0].talons[0].doctor.id", Is.is(53)))
                .andExpect(jsonPath("$.data.[0].talons[0].doctor.firstName", Is.is("Irina2")))
                .andExpect(jsonPath("$.data.[0].talons[0].doctor.lastName", Is.is("Petrova2")))
                .andExpect(jsonPath("$.data.[0].talons[0].doctor.patronymic", Is.is("Olegovna")))
                .andExpect(jsonPath("$.data.[0].talons[0].patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data.[0].talons[0].patient.firstName", Is.is("Ivan1")))
                .andExpect(jsonPath("$.data.[0].talons[0].patient.lastName", Is.is("Ivanov1")))
                .andExpect(jsonPath("$.data.[0].talons[0].patient.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.[0].talons[0].patient.birthday", Is.is("25.09.1990")))
                .andExpect(jsonPath("$.data.[1].talons.length()", Is.is(8)));

        // Проверка с отправкой dayStart и dayEnd равными null
        mockMvc.perform(
                        post("/api/registrar/talon/add/for/doctor/{doctorId}/by/days", 52)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data.length()", Is.is(21)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_talon_rest_controller/registrarDeleteTalon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_talon_rest_controller/registrarDeleteTalon_clear.sql")
    public void deleteTalonById() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("rg@mail.ru", "100", mockMvc);

        mockMvc.perform(
                        delete("/api/registrar/talon/{talonId}/delete", 235)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        Assertions.assertEquals(0L, entityManager.createQuery("""
                SELECT COUNT(t.id) 
                FROM Talon t
                WHERE t.id = 235
                """, Long.class).getSingleResult());

        mockMvc.perform(
                        delete("/api/registrar/talon/{talonId}/delete", 234)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("На талон с таким id записан пациент")));

        mockMvc.perform(
                        delete("/api/registrar/talon/{talonId}/delete", 348)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Талон с таким id не найден")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_talon_rest_controller/addForPatientTalon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_talon_rest_controller/addForPatientTalon_clear.sql")
    public void registersPatientByFreeTalonTest() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.ru", "1", mockMvc);

        mockMvc.perform(patch("/api/registrar/talon/{talonId}/assigned/by/patient/{patientId}", "1", "1000")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.id", Is.is(1)))
                .andExpect(jsonPath("$.data.doctor.id", Is.is(2000)))
                .andExpect(jsonPath("$.data.doctor.firstName", Is.is("Doctor")))
                .andExpect(jsonPath("$.data.doctor.lastName", Is.is("Doctor")))
                .andExpect(jsonPath("$.data.doctor.patronymic", Is.is("Doctor")))
                .andExpect(jsonPath("$.data.patient.id", Is.is(1000)))
                .andExpect(jsonPath("$.data.patient.firstName", Is.is("PatientID1000")))
                .andExpect(jsonPath("$.data.patient.lastName", Is.is("PatientID1000")))
                .andExpect(jsonPath("$.data.patient.patronymic", Is.is("PatientID1000")))
                .andExpect(jsonPath("$.data.patient.birthday", Is.is("20.02.2020")))
                .andExpect(status().isOk());

        mockMvc.perform(
                        patch("/api/registrar/talon/{talonId}/assigned/by/patient/{patientId}", "300", "1000")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.ALL)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Талон с таким ID не существует")));

        mockMvc.perform(
                        patch("/api/registrar/talon/{talonId}/assigned/by/patient/{patientId}", "1", "2000")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.ALL)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Пациент с таким ID не существует")));

        mockMvc.perform(
                        patch("/api/registrar/talon/{talonId}/assigned/by/patient/{patientId}", "2", "1000")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.ALL)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Талон используется другим пациентом")));

        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("patient1001@mail.ru");
        smm.setSubject("Вы записаны на свободный талон");
        smm.setText("Номер Вашего талона: 1");
        mailSender.send(smm);

        doNothing().when(mailSender).send(smm);

        Mockito.verify(mailSender, Mockito.times(1)).send(smm);

    }
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_talon_rest_controller/registrar_get_talons_by_parameters.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_talon_rest_controller/registrar_get_talons_by_parameters_clear.sql")
    public void registrarGetTalonsByParametersT() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        mockMvc.perform(
                get("/api/registrar/talon/get/by/parameters")
                        .param("departmentId", "")
                        .param("doctorId", "18")
                        .param("dateStart", "26.05.2023 03:00")
                        .param("dateEnd", "29.05.2023 06:00")
                        .param("ageType", "CHILD")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "")
                                .param("doctorId", "18")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .param("ageType", "CHILD")
                                .param("isFree", "false")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "")
                                .param("doctorId", "18")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .param("isFree", "false")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "")
                                .param("doctorId", "18")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .param("ageType", "")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .param("ageType", "ADULT")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "60")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .param("ageType", "CHILD")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "60")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .param("ageType", "ADULT")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "60")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .param("ageType", "ADULT")
                                .param("isFree", "false")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "150")
                                .param("doctorId", String.valueOf(17))
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .param("ageType", "ADULT")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Департамент по id не найден")));

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "60")
                                .param("doctorId", "3100")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "29.05.2023 06:00")
                                .param("ageType", "ADULT")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Доктора с таким id не существует")));

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "")
                                .param("doctorId","")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "06.06.2023 06:00")
                                .param("ageType", "ADULT")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Диапазон дней больше 7-ми")));


        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "")
                                .param("doctorId","18")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "10.08.2023 06:00")
                                .param("ageType", "ADULT")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Диапазон дней макс 28 дней")));

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "")
                                .param("doctorId","")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "10.06.2023 06:00")
                                .param("ageType", "")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Диапазон дней больше 3-х, тип мед. учреждения не указан")));

        mockMvc.perform(
                        get("/api/registrar/talon/get/by/parameters")
                                .param("departmentId", "")
                                .param("doctorId","")
                                .param("dateStart", "26.05.2023 03:00")
                                .param("dateEnd", "10.06.2023 06:00")
                                .param("ageType", "ADULT")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Диапазон дней больше 7-ми")));


    }

}