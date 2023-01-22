package com.onjee.loginApi.service;

import com.onjee.loginApi.domain.member.MemberEntity;
import com.onjee.loginApi.dto.JoinUserRequestDto;
import com.onjee.loginApi.exception.JoinWithOccupiedEmailException;
import com.onjee.loginApi.exception.JoinWithOccupiedUsernameException;
import com.onjee.loginApi.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final LoginRepository loginRepository;

    @Override
    public MemberEntity join(JoinUserRequestDto joinRequestData) throws JoinWithOccupiedUsernameException, JoinWithOccupiedEmailException {
        //check available
        if (loginRepository.existsByEmail(joinRequestData.getEmail()) == true){
            log.trace("join with occupied email");
            throw new JoinWithOccupiedEmailException();
        }
        if (loginRepository.existsByUsername(joinRequestData.getUsername()) == true){
            log.trace("join with occupied username");
            throw new JoinWithOccupiedUsernameException();
        }
        //join logic
        MemberEntity memberEntity =
                MemberEntity.builder()
                        .username(joinRequestData.getUsername())
                        .pw(joinRequestData.getPw())
                        .email(joinRequestData.getEmail())
                        .auth("ROLE_USER")
                        .build();
        MemberEntity savedMember = loginRepository.save(memberEntity);

        return savedMember;
    }
}
