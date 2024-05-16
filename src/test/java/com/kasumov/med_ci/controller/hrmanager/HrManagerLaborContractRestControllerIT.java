package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.core.Is;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({
        @MockBean(JavaMailSender.class)
})
public class HrManagerLaborContractRestControllerIT extends ContextIT {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_labor_contract_rest_controller/hrManagerTerminationLaborContract.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_labor_contract_rest_controller/hrManagerTerminationLaborContractClear.sql")
    public void terminationLaborContract() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru", "100", mockMvc);

        mockMvc.perform(
                        patch("/api/manager/contract/{contractId}/close", 160)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dateConvertor.localDateToString(LocalDate.now().plusDays(14)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(160)))
                .andExpect(jsonPath("$.data.endDate", Is.is(dateConvertor
                                .localDateToString(LocalDate.now().plusDays(14)))))
                .andExpect(jsonPath("$.data.firstName", Is.is("doc12")))
                .andExpect(jsonPath("$.data.lastName", Is.is("docov12")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("docovich12")))
                .andExpect(jsonPath("$.data.position", Is.is("Терапевт")));

        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("doc12@mail.com");
        smm.setSubject("Разрыв трудового соглашения");
        smm.setText("Уважаемый doc12 docov12 docovich12, с "
                + "Вам был расторгнут трудовой договор №160 с 01.01.2010 до "
                + dateConvertor.localDateToString(LocalDate.now().plusDays(14))
                + ". Занимаемая должность Терапевт.");
        doNothing().when(mailSender).send(smm);
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
        smm.setTo("doc12@mail.com");

        mockMvc.perform(
                        patch("/api/manager/contract/{contractId}/close", 2234)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dateConvertor.localDateToString(LocalDate.now().plusDays(14)))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text",
                        Is.is("Трудовой договор не найден по id")));

        mockMvc.perform(
                        patch("/api/manager/contract/{contractId}/close", 160)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dateConvertor.localDateToString(LocalDate.now()))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text",
                        Is.is("Между указанной датой увольнения должно быть минимум 14 дней")));

        mockMvc.perform(
                        patch("/api/manager/contract/{contractId}/close", 161)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dateConvertor.localDateToString(LocalDate.now()))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text",
                        Is.is("Вы пытаетесь закрыть уже истекший договор")));

        mockMvc.perform(
                        patch("/api/manager/contract/{contractId}/close", 162)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dateConvertor.localDateToString(LocalDate.now()))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text",
                        Is.is("Вы пытаетесь закрыть уже истекший договор")));

        mockMvc.perform(
                        patch("/api/manager/contract/{contractId}/close", 163)
                                .header("Authorization", accessToken)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dateConvertor.localDateToString(LocalDate.now().plusDays(14)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(163)))
                .andExpect(jsonPath("$.data.endDate", Is.is(dateConvertor
                        .localDateToString(LocalDate.now().plusDays(14)))))
                .andExpect(jsonPath("$.data.firstName", Is.is("doc16")))
                .andExpect(jsonPath("$.data.lastName", Is.is("docov16")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("docovich16")))
                .andExpect(jsonPath("$.data.position", Is.is("Офтальмолог")));

    }
}
