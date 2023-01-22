package com.onjee.loginApi.service;

import com.onjee.loginApi.domain.member.MemberEntity;
import com.onjee.loginApi.dto.JoinUserRequestDto;
import com.onjee.loginApi.exception.JoinWithOccupiedEmailException;
import com.onjee.loginApi.exception.JoinWithOccupiedUsernameException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = true)
@DisplayName("LoginServiceImpl 테스트")
class LoginServiceImplTest {

    @Autowired
    LoginService loginService;

    @Nested
    @DisplayName("join method test")
    class Join{

        static JoinUserRequestDto testJoinRequest;

        @BeforeAll
        public static void initTestJoinRequest(){
            testJoinRequest = JoinUserRequestDto.builder()
                    .email("test@a.a")
                    .pw("testPw")
                    .username("testUsername")
                    .build();
        }


        @Test
        @DisplayName("신규회원 가입")
        public void joinNewMember() throws JoinWithOccupiedEmailException, JoinWithOccupiedUsernameException {
            //given
            JoinUserRequestDto givenRequest = testJoinRequest;
            //when
            MemberEntity joinedMember = loginService.join(givenRequest);
            //then
            assertEquals(joinedMember.getUsername(), givenRequest.getUsername());
            assertEquals(joinedMember.getPw(), givenRequest.getPw());
            assertEquals(joinedMember.getEmail(), givenRequest.getEmail());

        }

        @Test
        @DisplayName("아이디 중복 된 회원가입")
        public void joinWithOccupiedUsername() throws JoinWithOccupiedEmailException, JoinWithOccupiedUsernameException {
            //given
            loginService.join(testJoinRequest);
            JoinUserRequestDto requestWithOccupiedUsername =
                    JoinUserRequestDto.builder()
                            .username(testJoinRequest.getUsername())
                            .email(testJoinRequest.getEmail() + "different")
                            .pw("1234")
                            .build();
            //when
            assertThrows(JoinWithOccupiedUsernameException.class, () ->
                    loginService.join(requestWithOccupiedUsername)
            );
        }

        @Test
        @DisplayName("이메일 중복 된 회원가입")
        public void joinWithOccupiedEmail() throws JoinWithOccupiedEmailException, JoinWithOccupiedUsernameException {
            //given
            loginService.join(testJoinRequest);
            JoinUserRequestDto requestWithOccupiedEmail =
                    JoinUserRequestDto.builder()
                            .username(testJoinRequest.getUsername() + "different")
                            .email(testJoinRequest.getEmail())
                            .pw("1234")
                            .build();
            //then throw
            assertThrows(JoinWithOccupiedEmailException.class, () ->
                    loginService.join(requestWithOccupiedEmail)
            );
        }


    }


}