package com.kasumov.med_ci.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContextIT {

    @Autowired
    protected ITAccessTokenUtil tokenUtil;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected DateConvertor dateConvertor;

    @PersistenceContext
    protected EntityManager entityManager;

    protected String accessToken;
}