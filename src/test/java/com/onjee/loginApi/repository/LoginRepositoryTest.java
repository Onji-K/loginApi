package com.onjee.loginApi.repository;

import com.onjee.loginApi.domain.member.MemberEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = true)
class LoginRepositoryTest {

    @Autowired
    LoginRepository loginRepository;

    private static MemberEntity testMember;

    @BeforeAll
    public static void setTestMember(){
        //중복 회원 중복 아이디 체크 로직
        MemberEntity givenMember = MemberEntity.builder()
                .username("testId")
                .pw("testPw")
                .email("a@a.a")
                .build();
        testMember = givenMember;
    }

    @BeforeEach
    public void checkTransactionIsActive(){
        //Assumption
        assertTrue(TestTransaction.isActive(),"안전을 위해 트랜잭션 처리를 해주십시오");
        assertTrue(TestTransaction.isFlaggedForRollback(),"롤백 설정을 확인하세요");
    }

    @BeforeEach
    public void checkTestMemberExist(){
        assertFalse(loginRepository.existsByUsername(testMember.getUsername()),"이미 존재하는 멤버 아이디입니다. 테스트 멤버의 이메일을 바꿔주십시오");
        assertFalse(loginRepository.existsByEmail(testMember.getEmail()),"이미 존재하는 이메일입니다. 테스트 멤버의 이메일을 바꿔주십시오");
    }

    @Test
    @DisplayName("existsById 테스트")
    public void existsByUsername(){
        //given
        MemberEntity givenMember = testMember;
        loginRepository.save(givenMember);
        //when
        boolean result = loginRepository.existsByUsername(testMember.getUsername());
        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("existsByEmail 테스트")
    public void existsByEmail(){
        //given
        MemberEntity givenMember = testMember;
        loginRepository.save(givenMember);
        //when
        boolean result = loginRepository.existsByEmail(testMember.getEmail());
        //then
        assertTrue(result);
    }


    @Test
    @DisplayName("신규회원 추가 테스트")
    public void addNewMember(){
        //assumption

        //given
        MemberEntity givenMember = testMember;
        //when
        MemberEntity savedMember = loginRepository.save(givenMember);
        //then
        assertSame(givenMember, savedMember);
    }



}