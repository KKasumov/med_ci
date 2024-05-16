package com.kasumov.med_ci.controller.patient;

import com.kasumov.med_ci.util.ContextIT;
import lombok.RequiredArgsConstructor;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@MockBeans({
        @MockBean(JavaMailSender.class)
})
public class PatientTalonRestControllerIT extends ContextIT {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/patient/patient_talon_rest_controller/patientAssignFreeTalon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/patient/patient_talon_rest_controller/patientAssignFreeTalon_clear.sql")
    public void patientAssignFreeTalonTest() throws Exception {

        //письмо которое будет отправляться почтой
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("evgeny_sav79@mail.ru");
        smm.setSubject("Вы записаны на прием");
        smm.setText("Название отделения: Первое взрослое отделение" +
                "Имя доктора: Alexander" +
                "Фамилия доктора: Pushkin" +
                "Отчество доктора: Sergeevich" +
                "Время визита: 8:00");
        System.out.println(smm);
        mailSender.send(smm);


        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "1", mockMvc);

        // TalonDto patient
        mockMvc.perform(
                        patch("/api/patient/talon/{talonId}/assign", 5)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.code", Is.is(200)))
                .andExpect(jsonPath("$.data.id", Is.is(5)))
                .andExpect(jsonPath("$.data.time", Is.is(dateConvertor.localDateTimeToString(
                        LocalDate.now().atTime(8, 0)))
                ))
                .andExpect(jsonPath("$.data.patient.firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.data.doctor.firstName", Is.is("Alexander")));

//         проверка на существование талона
        mockMvc.perform(
                        patch("/api/patient/talon/{talonId}/assign", 879945)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Талон с таким id не существует")));

        // проверка что доктор работает с возрастом пациента
        accessToken = tokenUtil.obtainNewAccessToken("ivan2@mail.com", "1", mockMvc);
        mockMvc.perform(
                        patch("/api/patient/talon/{talonId}/assign", 7)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Возраст пациента не соответствует отделению")));

        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "1", mockMvc);

        // проверка что талон свободен
        mockMvc.perform(
                        patch("/api/patient/talon/{talonId}/assign", 2)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Запись не возможна талон уже занят")));


        //        проверка даты
        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "1", mockMvc);
        mockMvc.perform(
                        patch("/api/patient/talon/{talonId}/assign", 3)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(452)))
                .andExpect(jsonPath("$.text", Is.is("дата записи недоступна для пациента")));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/patient/patient_talon_rest_controller/patientGetsTalonsAssigned.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/patient/patient_talon_rest_controller/patientGetsTalonsAssigned_clear.sql")
    public void patientGetsTalonsAssignedTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("ivan@mail.com", "123", mockMvc);

        mockMvc.perform(
                        patch("/api/patient/talon/get/all/assigned")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].time").value(dateConvertor.localDateTimeToString(
                                LocalDate.now().atTime(7, 0))))

                .andExpect(jsonPath("$.data[1].id").value(4))
                .andExpect(jsonPath("$.data[1].time").value(dateConvertor.localDateTimeToString(
                                LocalDate.now().atTime(8, 0))))

                .andExpect(jsonPath("$.data[2].id").value(6))
                .andExpect(jsonPath("$.data[2].time").value(dateConvertor.localDateTimeToString(
                                LocalDate.now().atTime(8, 0))));

        accessToken = tokenUtil.obtainNewAccessToken("ivan1@mail.com", "123", mockMvc);

        mockMvc.perform(
                        patch("/api/patient/talon/get/all/assigned")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(2))
                .andExpect(jsonPath("$.data[0].time").value(dateConvertor.localDateTimeToString(
                        LocalDate.now().atTime(8, 0))));

        accessToken = tokenUtil.obtainNewAccessToken("ivan2@mail.com", "123", mockMvc);

        mockMvc.perform(
                        patch("/api/patient/talon/get/all/assigned")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(7))
                .andExpect(jsonPath("$.data[0].time").value(dateConvertor.localDateTimeToString(
                        LocalDate.now().atTime(8, 0))));
    }
}

