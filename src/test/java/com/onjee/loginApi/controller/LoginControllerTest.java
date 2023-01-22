package com.onjee.loginApi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onjee.loginApi.domain.member.MemberEntity;
import com.onjee.loginApi.dto.JoinUserRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private static final String BASE_URL = "/api/member";

    @Nested
    @DisplayName("join 메서드 테스트")
    class joinTest{

        @Test
        @DisplayName("신규회원 가입")
        public void joinNewMember() throws Exception {
            //given
            String username = "testUsername";
            String email = "test@a.a";
            String pw = "testPw";

            String jsonBody = mapper.writeValueAsString(
                    JoinUserRequestDto.builder()
                            .username(username)
                            .pw(pw)
                            .email(email)
                            .build()
            );

            //when

            ResultActions performResult = mockMvc.perform(post(BASE_URL)
                    .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
            );
            //then
            performResult
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.success").value("true"))
                    .andExpect(jsonPath("$.code").value("200"))
                    .andExpect(jsonPath("$.data").value(username))
            ;

        }

        @Test
        @DisplayName("아이디 or 이메일이 겹치는 회원 가입 방지")
        public void joinWithOccInfo() throws Exception{
            //condition
            String username = "testUsername";
            String email = "test@a.a";
            String pw = "testPw";

            String jsonBody = mapper.writeValueAsString(
                    JoinUserRequestDto.builder()
                            .username(username)
                            .pw(pw)
                            .email(email)
                            .build()
            );

            mockMvc.perform(post(BASE_URL)
                    .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            //given
            String occUsernameJsonBody = mapper.writeValueAsString(
                    JoinUserRequestDto.builder()
                            .username(username)
                            .pw(pw)
                            .email(email+"diff")
                            .build()
            );
            String occEmailJsonBody = mapper.writeValueAsString(
                    JoinUserRequestDto.builder()
                            .username(username)
                            .pw(pw)
                            .email(email+"diff")
                            .build()
            );

            //when
            ResultActions occUsernameJoinPerformResult = mockMvc.perform(post(BASE_URL)
                    .content(occUsernameJsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
            );
            ResultActions occEmailJoinPerformResult = mockMvc.perform(post(BASE_URL)
                    .content(occEmailJsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            //then
            occUsernameJoinPerformResult
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value("false"))
                    .andExpect(jsonPath("$.code").value("400"));

            occEmailJoinPerformResult
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value("false"))
                    .andExpect(jsonPath("$.code").value("400"));
        }

    }


}