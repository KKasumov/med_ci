package com.kasumov.med_ci.controller;

import com.kasumov.med_ci.config.security.jwt.LoginRequest;
import com.kasumov.med_ci.repository.user.InviteRepository;
import com.kasumov.med_ci.util.ContextIT;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthRestControllerIT extends ContextIT {

    @Autowired
    private InviteRepository inviteRepository;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/auth_rest_controller/confirmEmailPassword.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/auth_rest_controller/confirmEmailPassword_clear.sql")
    public void confirmEmailPasswordTest() throws Exception {

        // валидный тест
        LoginRequest loginRequest = new LoginRequest("patient@email.com", "1");
        mockMvc.perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.startsWith("Bearer ")))
                .andExpect(jsonPath("$.type", Is.is("Bearer ")))
                .andExpect(jsonPath("$.id", Is.is(150)))
                .andExpect(jsonPath("$.email", Is.is("patient@email.com")))
                .andExpect(jsonPath("$.roles[0]", Is.is("PATIENT")));


        // неверный пароль
        loginRequest = new LoginRequest("patient@email.com", "2");
        mockMvc.perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path", Is.is("")))
                .andExpect(jsonPath("$.error", Is.is("Unauthorized")))
                .andExpect(jsonPath("$.message", Is.is("Bad credentials")))
                .andExpect(jsonPath("$.status", Is.is(401)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/auth_rest_controller/passwordSetByUser.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/auth_rest_controller/passwordSetByUser_clear.sql")
    public void passwordSetByUserTest() throws Exception {

        // валидный тест
        mockMvc.perform(patch("/api/auth/password/change")
                                .param("token", "0123456789abcdef")
                                .param("password", "password123")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(150)))
                .andExpect(jsonPath("$.data.email", Is.is("mis_mono_1@bk.ru")))
                .andExpect(jsonPath("$.data.snils", Is.is("123-456 78")))
                .andExpect(jsonPath("$.data.firstName", Is.is("Peter")))
                .andExpect(jsonPath("$.data.lastName", Is.is("Jackson")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("Petrovich")))
                .andExpect(jsonPath("$.data.birthday", Is.is("01.01.1990")))
                .andExpect(jsonPath("$.data.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.isEnabled", Is.is(true)));
        Assertions.assertFalse(inviteRepository.existsByEmail("mis_mono_1@bk.ru"));

        // проверка, когда инвайт уже был удален из базы
        mockMvc.perform(patch("/api/auth/password/change")
                                .param("token", "0123456789abcdef")
                                .param("password", "password123")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Ссылка устарела")));

        // проверка, когда инвайт просрочен
        mockMvc.perform(patch("/api/auth/password/change")
                        .param("token", "abcdef0123456789")
                        .param("password", "password123")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(452)))
                .andExpect(jsonPath("$.text", Is.is("Ссылка устарела")));

        // проверка, что пароль менее 10 символов
        mockMvc.perform(patch("/api/auth/password/change")
                        .param("token", "0123456789abcdef")
                        .param("password", "password1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Пароль не соответствует минимальным требованиям")));

        // проверка, что пароль имеет иные от латинских заглавных и строчных букв и цифр символы
        mockMvc.perform(patch("/api/auth/password/change")
                        .param("token", "0123456789abcdef")
                        .param("password", "password123@")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Пароль не соответствует минимальным требованиям")));
    }
}
