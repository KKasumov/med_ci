package com.kasumov.med_ci.util;

import com.kasumov.med_ci.config.security.jwt.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
@RequiredArgsConstructor
public class ITAccessTokenUtil {

    private final ObjectMapper objectMapper;
    private final JacksonJsonParser jsonParser;

    public String obtainNewAccessToken(String username, String password, MockMvc mockMvc) throws Exception {
        LoginRequest loginRequest = new LoginRequest(username, password);

        MvcResult response;
        response = mockMvc
                .perform(
                        post("/api/auth/login")
                                .content(objectMapper.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        return jsonParser.parseMap(response.getResponse().getContentAsString()).get("token").toString();
    }
}
