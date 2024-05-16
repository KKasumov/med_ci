package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.util.ContextIT;
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

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({
        @MockBean(JavaMailSender.class)
})
public class RegistrarDoctorRestControllerIT extends ContextIT {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_doctor_rest_controller/distributeDoctorPatientsRangeOfDaysAndRemovesTalon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_doctor_rest_controller/distributeDoctorPatientsRangeOfDaysAndRemovesTalon_clear.sql")
    public void distributeDoctorPatientsRangeOfDaysAndRemovesTalonTest() throws Exception {

        //почтовое сообщение
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("patient200@mail.com");
        smm.setSubject("Запись к врачу");
        smm.setText("Ваш талон был перенес к другому доктору");

        doNothing().when(mailSender).send(smm);

        //проверка, с не существующим id доктора
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(
                        delete("/api/registrar/doctor/{doctorId}/assigned/patients/in/department/by/days", 99999999)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Доктор с таким id не существует")));


        //проверка, с начальной датой
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(
                        delete("/api/registrar/doctor/{doctorId}/assigned/patients/in/department/by/days", 30)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("dayStart", dateConvertor.localDateTimeToString(LocalDate.now().plusDays(5).atTime(10, 0)))
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(9)))
                .andExpect(jsonPath("$.data[0].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().plusDays(14).atTime(16, 0)))))
                .andExpect(jsonPath("$.data[0].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data[0].doctor.firstName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].doctor.lastName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].doctor.patronymic", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].patient.id", Is.is(220)))
                .andExpect(jsonPath("$.data[0].patient.firstName", Is.is("patient220")))
                .andExpect(jsonPath("$.data[0].patient.lastName", Is.is("patient220")))
                .andExpect(jsonPath("$.data[0].patient.patronymic", Is.is("patient220")))
                .andExpect(jsonPath("$.data[0].patient.birthday", Is.is("25.06.1990")));

        //проверка, что талоны перенесены
        Assertions.assertEquals(3, entityManager.createQuery("""
                SELECT COUNT(t)
                FROM Talon t
                WHERE t.doctorHistory.id BETWEEN 6 and 7
                    and t.patientHistory IS NULL
                """, Long.class).getSingleResult());


        //проверка действительно ли вызывался метод отправки письма с нужными параметрами
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient210@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient220@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_doctor_rest_controller/distributeDoctorPatientsRangeOfDaysAndRemovesTalon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_doctor_rest_controller/distributeDoctorPatientsRangeOfDaysAndRemovesTalon_clear.sql")
    public void distributeDoctorPatientsRangeOfDaysAndRemovesTalonTest1() throws Exception {

        //почтовое сообщение
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("patient160@mail.com");
        smm.setSubject("Запись к врачу");
        smm.setText("Ваш талон был перенес к другому доктору");

        doNothing().when(mailSender).send(smm);

        //проверка, с конечной датой
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(
                        delete("/api/registrar/doctor/{doctorId}/assigned/patients/in/department/by/days", 30)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("dayEnd", dateConvertor.localDateTimeToString(LocalDate.now().plusDays(6).atTime(15, 30)))
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(1)))
                .andExpect(jsonPath("$.data[0].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().atTime(10, 0)))))
                .andExpect(jsonPath("$.data[0].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data[0].doctor.firstName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].doctor.lastName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].doctor.patronymic", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].patient.id", Is.is(150)))
                .andExpect(jsonPath("$.data[0].patient.firstName", Is.is("patient150")))
                .andExpect(jsonPath("$.data[0].patient.lastName", Is.is("patient150")))
                .andExpect(jsonPath("$.data[0].patient.patronymic", Is.is("patient150")))
                .andExpect(jsonPath("$.data[0].patient.birthday", Is.is("25.06.1990")))

                .andExpect(jsonPath("$.data[1].id", Is.is(4)))
                .andExpect(jsonPath("$.data[1].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().plusDays(3).atTime(11, 30)))))
                .andExpect(jsonPath("$.data[1].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data[1].doctor.firstName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[1].doctor.lastName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[1].doctor.patronymic", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[1].patient.id", Is.is(180)))
                .andExpect(jsonPath("$.data[1].patient.firstName", Is.is("patient180")))
                .andExpect(jsonPath("$.data[1].patient.lastName", Is.is("patient180")))
                .andExpect(jsonPath("$.data[1].patient.patronymic", Is.is("patient180")))
                .andExpect(jsonPath("$.data[1].patient.birthday", Is.is("25.06.1990")));


        //проверка, что талоны перенесены
        Assertions.assertEquals(2, entityManager.createQuery("""
                SELECT COUNT(t)
                FROM Talon t
                WHERE t.doctorHistory.id BETWEEN 6 and 7
                    and t.patientHistory IS NULL
                """, Long.class).getSingleResult());


        //проверка действительно ли вызывался метод отправки письма с нужными параметрами
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient170@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient190@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient200@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_doctor_rest_controller/distributeDoctorPatientsRangeOfDaysAndRemovesTalon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_doctor_rest_controller/distributeDoctorPatientsRangeOfDaysAndRemovesTalon_clear.sql")
    public void distributeDoctorPatientsRangeOfDaysAndRemovesTalonTest2() throws Exception {

        //почтовое сообщение
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("patient170@mail.com");
        smm.setSubject("Запись к врачу");
        smm.setText("Ваш талон был перенес к другому доктору");

        doNothing().when(mailSender).send(smm);

        //проверка, с двумя датами
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(
                        delete("/api/registrar/doctor/{doctorId}/assigned/patients/in/department/by/days", 30)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("dayStart", dateConvertor.localDateTimeToString(LocalDate.now().plusDays(2).atTime(10, 0)))
                                .param("dayEnd", dateConvertor.localDateTimeToString(LocalDate.now().plusDays(4).atTime(15, 30)))
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(4)))
                .andExpect(jsonPath("$.data[0].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().plusDays(3).atTime(11, 30)))))
                .andExpect(jsonPath("$.data[0].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data[0].doctor.firstName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].doctor.lastName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].doctor.patronymic", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].patient.id", Is.is(180)))
                .andExpect(jsonPath("$.data[0].patient.firstName", Is.is("patient180")))
                .andExpect(jsonPath("$.data[0].patient.lastName", Is.is("patient180")))
                .andExpect(jsonPath("$.data[0].patient.patronymic", Is.is("patient180")))
                .andExpect(jsonPath("$.data[0].patient.birthday", Is.is("25.06.1990")));


        //проверка, что талоны перенесены
        Assertions.assertEquals(4, entityManager.createQuery("""
                SELECT COUNT(t)
                FROM Talon t
                WHERE t.doctorHistory.id BETWEEN 6 and 7
                    and t.patientHistory IS NULL
                """, Long.class).getSingleResult());


        //проверка действительно ли вызывался метод отправки письма с нужными параметрами
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient190@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_doctor_rest_controller/distributeDoctorPatientsRangeOfDaysAndRemovesTalon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            , value = "/scripts/controller/registrar/registrar_doctor_rest_controller/distributeDoctorPatientsRangeOfDaysAndRemovesTalon_clear.sql")
    public void distributeDoctorPatientsRangeOfDaysAndRemovesTalonTest3() throws Exception {

        //почтовое сообщение
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("patient160@mail.com");
        smm.setSubject("Запись к врачу");
        smm.setText("Ваш талон был перенес к другому доктору");

        doNothing().when(mailSender).send(smm);

        //проверка, без дат
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(
                        delete("/api/registrar/doctor/{doctorId}/assigned/patients/in/department/by/days", 30)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
    //                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Is.is(1)))
                .andExpect(jsonPath("$.data[0].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().atTime(10, 0)))))
                .andExpect(jsonPath("$.data[0].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data[0].doctor.firstName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].doctor.lastName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].doctor.patronymic", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[0].patient.id", Is.is(150)))
                .andExpect(jsonPath("$.data[0].patient.firstName", Is.is("patient150")))
                .andExpect(jsonPath("$.data[0].patient.lastName", Is.is("patient150")))
                .andExpect(jsonPath("$.data[0].patient.patronymic", Is.is("patient150")))
                .andExpect(jsonPath("$.data[0].patient.birthday", Is.is("25.06.1990")))

                .andExpect(jsonPath("$.data[1].id", Is.is(4)))
                .andExpect(jsonPath("$.data[1].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().plusDays(3).atTime(11, 30)))))
                .andExpect(jsonPath("$.data[1].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data[1].doctor.firstName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[1].doctor.lastName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[1].doctor.patronymic", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[1].patient.id", Is.is(180)))
                .andExpect(jsonPath("$.data[1].patient.firstName", Is.is("patient180")))
                .andExpect(jsonPath("$.data[1].patient.lastName", Is.is("patient180")))
                .andExpect(jsonPath("$.data[1].patient.patronymic", Is.is("patient180")))
                .andExpect(jsonPath("$.data[1].patient.birthday", Is.is("25.06.1990")))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[2].id", Is.is(9)))
                .andExpect(jsonPath("$.data[2].time", Is.is(
                        dateConvertor.localDateTimeToString(LocalDate.now().plusDays(14).atTime(16, 0)))))
                .andExpect(jsonPath("$.data[2].doctor.id", Is.is(30)))
                .andExpect(jsonPath("$.data[2].doctor.firstName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[2].doctor.lastName", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[2].doctor.patronymic", Is.is("doctor30")))
                .andExpect(jsonPath("$.data[2].patient.id", Is.is(220)))
                .andExpect(jsonPath("$.data[2].patient.firstName", Is.is("patient220")))
                .andExpect(jsonPath("$.data[2].patient.lastName", Is.is("patient220")))
                .andExpect(jsonPath("$.data[2].patient.patronymic", Is.is("patient220")))
                .andExpect(jsonPath("$.data[2].patient.birthday", Is.is("25.06.1990")));


        //проверка, что талоны перенесены
        Assertions.assertEquals(0, entityManager.createQuery("""
                SELECT COUNT(t)
                FROM Talon t
                WHERE t.doctorHistory.id BETWEEN 6 and 7
                    and t.patientHistory IS NULL
                """, Long.class).getSingleResult());


        //проверка, что талоны не передались врачу из другого отделения
        Assertions.assertEquals(9, entityManager.createQuery("""
                SELECT COUNT(t)
                FROM Talon t
                WHERE t.doctorHistory.id = 8
                    and t.patientHistory IS NULL
                """, Long.class).getSingleResult());


        //проверка действительно ли вызывался метод отправки письма с нужными параметрами
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient170@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient190@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient200@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient210@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("patient220@mail.com");
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
    }
}
